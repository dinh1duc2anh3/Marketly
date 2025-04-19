package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.shippingfee.ShippingFeeCalculatorFactory;
import com.darian.ecommerce.dto.*;
import com.darian.ecommerce.entity.DeliveryInfo;
import com.darian.ecommerce.entity.Order;
import com.darian.ecommerce.entity.OrderItem;
import com.darian.ecommerce.repository.OrderRepository;
import com.darian.ecommerce.service.AuditLogService;
import com.darian.ecommerce.service.CartService;
import com.darian.ecommerce.service.OrderService;
import com.darian.ecommerce.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ShippingFeeCalculatorFactory calculatorFactory;
    private final CartService cartService;
    private final AuditLogService auditLogService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            PaymentService paymentService,
                            ShippingFeeCalculatorFactory calculatorFactory,
                            CartService cartService,
                            AuditLogService auditLogService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.calculatorFactory = calculatorFactory;
        this.cartService = cartService;
        this.auditLogService = auditLogService;
    }


    @Override
    public OrderDTO createOrder(CartDTO cartDTO) {
        if (!checkAvailability(cartDTO)) {
            throw new IllegalStateException("Cart items not available");
        }
        Order order = new Order();
        order.setOrderId("ORD-" + System.currentTimeMillis());
        // need more checking + innovate cart service
        order.setCustomerId(cartDTO.getUserId());
        //check more
        order.setItems(cartDTO.getItems());
        order.setStatus("PENDING");
        order.setCreatedDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        auditLogService.logOrderAction(order.getCustomerId(), order.getOrderId(), "CUSTOMER");
        return mapToOrderDTO(savedOrder);
    }


    @Override
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        Order order = mapToOrderEntity(orderDTO);
        order.setShippingFee(calculatorFactory.getCalculator(orderDTO).calculateShippingFee(orderDTO));
        order.setStatus("PLACED");
        Order savedOrder = orderRepository.save(order);
        auditLogService.logOrderAction(order.getCustomerId(), order.getOrderId(), "CUSTOMER");
        return mapToOrderDTO(savedOrder);
    }

    @Override
    public RushOrderDTO placeRushOrder(RushOrderDTO rushOrderDTO) {
        //cần xem lại logic của checkRushProductEligibility vì nó check từng item trong order chứ ko phải check userId
        if (!checkRushProductEligibility(rushOrderDTO.getCustomerId()) ||
                !checkRushDeliveryAddress(rushOrderDTO.getDeliveryInfo().getAddress())) {
            throw new IllegalStateException("Rush order not eligible");
        }
        //need check
        Order order = mapToOrderEntity(rushOrderDTO);
        order.setShippingFee(calculatorFactory.getCalculator(rushOrderDTO).calculateShippingFee(rushOrderDTO));
        order.setStatus("RUSH_PLACED");
        Order savedOrder = orderRepository.save(order);
        auditLogService.logOrderAction(order.getCustomerId(), order.getOrderId(), "CUSTOMER");
        return mapToRushOrderDTO(savedOrder, rushOrderDTO.getRushDeliveryTime());
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus("CANCELLED");
        orderRepository.save(order);

        //need check
        auditLogService.logOrderAction(order.getCustomerId(), orderId, "CUSTOMER");
    }

    @Override
    public OrderDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return mapToOrderDTO(order);
    }

    @Override
    public Boolean checkAvailability(CartDTO cartDTO) {
        return cartService.checkAvailability(cartDTO);
    }

    @Override
    public Boolean validateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO) {\
        //need more conditions here
        return deliveryInfoDTO.getRecipientName() != null && !deliveryInfoDTO.getRecipientName().isBlank() &&
                deliveryInfoDTO.getAddress() != null && !deliveryInfoDTO.getAddress().isBlank();
    }

    @Override
    public OrderDTO setDeliveryInfo(Long orderId, DeliveryInfoDTO deliveryInfoDTO) {
        if (!validateDeliveryInfo(deliveryInfoDTO)) {
            throw new IllegalArgumentException("Invalid delivery info");
        }
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setDeliveryInfo(mapToDeliveryInfoEntity(deliveryInfoDTO));
        Order savedOrder = orderRepository.save(order);
        return mapToOrderDTO(savedOrder);
    }

    @Override
    public Boolean isRushOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> "RUSH_PLACED".equals(order.getStatus()))
                .orElse(false);
    }

    @Override
    public void setPending(Long orderId) {
        orderRepository.updateOrderStatus(orderId, "PENDING");
    }

    @Override
    public Boolean checkRushDeliveryAddress(String address) {
        //need more check
        return address != null && address.contains("Hanoi"); //example
    }

    @Override
    public Boolean checkRushProductEligibility(Integer userId) {
        return cartService.checkRushEligibility(userId); // Delegate to CartService
    }

    @Override
    public InvoiceDTO getInvoice(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setOrderId(orderId);
        invoice.setShippingFee(order.getShippingFee());
        invoice.setTotal(order.getTotal());
        return invoice;
    }

    @Override
    public void updatePaymentStatus(Long orderId, String status) {
        orderRepository.updateOrderStatus(orderId, status);
    }

    @Override
    public List<OrderDTO> getOrderHistory(Integer customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    // Mapping methods
    private OrderDTO mapToOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomerId());
        dto.setItems(order.getItems().stream().map(this::mapToOrderItemDTO).collect(Collectors.toList()));
        dto.setStatus(order.getStatus());
        dto.setDeliveryInfo(mapToDeliveryInfoDTO(order.getDeliveryInfo()));
        dto.setSubtotal((Integer) order.getSubtotal());
        dto.setShippingFee((Integer) order.getShippingFee());
        dto.setTotal((Integer) order.getTotal());
        dto.setCreatedDate(java.util.Date.from(order.getCreatedDate().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        return dto;
    }

    private RushOrderDTO mapToRushOrderDTO(Order order, java.util.Date rushDeliveryTime) {
        RushOrderDTO dto = new RushOrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomerId());
        dto.setItems(order.getItems().stream().map(this::mapToOrderItemDTO).collect(Collectors.toList()));
        dto.setStatus(order.getStatus());
        dto.setDeliveryInfo(mapToDeliveryInfoDTO(order.getDeliveryInfo()));
        dto.setSubtotal((Integer) order.getSubtotal());
        dto.setShippingFee((Integer) order.getShippingFee());
        dto.setTotal((Integer) order.getTotal());
        dto.setCreatedDate(java.util.Date.from(order.getCreatedDate().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        dto.setRushDeliveryTime(rushDeliveryTime);
        return dto;
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setLineTotal(item.getLineTotal());
        return dto;
    }

    private DeliveryInfoDTO mapToDeliveryInfoDTO(DeliveryInfo info) {
        DeliveryInfoDTO dto = new DeliveryInfoDTO();
        dto.setRecipientName(info.getRecipientName());
        dto.setPhoneNumber(info.getPhoneNumber());
        dto.setEmail(info.getEmail());
        dto.setProvinceCity(info.getProvinceCity());
        dto.setAddress(info.getAddress());
        dto.setShippingInstructions(info.getShippingInstructions());
        return dto;
    }

    private Order mapToOrderEntity(BaseOrderDTO dto) {
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
        order.setCustomerId(dto.getCustomerId());
        order.setItems(dto.getItems().stream().map(item -> {
            OrderItem entity = new OrderItem();
            entity.setProductId(item.getProductId());
            entity.setQuantity(item.getQuantity());
            entity.setUnitPrice(item.getUnitPrice());
            entity.setOrder(order);
            return entity;
        }).collect(Collectors.toList()));
        order.setStatus(dto.getStatus());
        order.setDeliveryInfo(mapToDeliveryInfoEntity(dto.getDeliveryInfo()));
        order.setSubtotal(dto.getSubtotal());
        order.setShippingFee(dto.getShippingFee());
        order.setTotal(dto.getTotal());
        order.setCreatedDate(dto.getCreatedDate() != null ?
                LocalDateTime.ofInstant(dto.getCreatedDate().toInstant(), java.time.ZoneId.systemDefault()) : LocalDateTime.now());
        return order;
    }

    private DeliveryInfo mapToDeliveryInfoEntity(DeliveryInfoDTO dto) {
        DeliveryInfo info = new DeliveryInfo();
        info.setRecipientName(dto.getRecipientName());
        info.setPhoneNumber(dto.getPhoneNumber());
        info.setEmail(dto.getEmail());
        info.setProvinceCity(dto.getProvinceCity());
        info.setAddress(dto.getAddress());
        info.setShippingInstructions(dto.getShippingInstructions());
        return info;
    }
}
