package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.mapper.ordermapper.DeliveryInfoMapper;
import com.darian.ecommerce.businesslogic.mapper.ordermapper.OrderMapper;
import com.darian.ecommerce.businesslogic.shippingfee.ShippingFeeCalculatorFactory;
import com.darian.ecommerce.dto.*;
import com.darian.ecommerce.entity.*;
import com.darian.ecommerce.enums.ActionType;
import com.darian.ecommerce.enums.OrderStatus;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.repository.OrderRepository;
import com.darian.ecommerce.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ProductService productService;
    private final ShippingFeeCalculatorFactory calculatorFactory;
    private final CartService cartService;
    private final UserService userService;
    private final AuditLogService auditLogService;
    private final OrderMapper orderMapper;
    private final DeliveryInfoMapper deliveryInfoMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            PaymentService paymentService,
                            ProductService productService,
                            ShippingFeeCalculatorFactory calculatorFactory,
                            CartService cartService,
                            UserService userService,
                            AuditLogService auditLogService, OrderMapper orderMapper, DeliveryInfoMapper deliveryInfoMapper) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.productService = productService;
        this.calculatorFactory = calculatorFactory;
        this.cartService = cartService;
        this.userService = userService;
        this.auditLogService = auditLogService;
        this.orderMapper = orderMapper;
        this.deliveryInfoMapper = deliveryInfoMapper;
    }


    @Override
    public OrderDTO createOrder(CartDTO cartDTO) {
        if (!checkAvailability(cartDTO)) {
            throw new IllegalStateException("Cart items not available");
        }
        Order order = new Order();
//        order.setOrderId("ORD-" + System.currentTimeMillis());
        // need more checking + innovate cart service
        User user = userService.getUserById(cartDTO.getUserId())
        order.setUser(user);
        //check more
//        order.setItems(cartDTO.getItems());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        auditLogService.logOrderAction(order.getUser().getId(), order.getOrderId(), UserRole.CUSTOMER, ActionType.ORDER_ACTION);
        return orderMapper.toOrderDTO(savedOrder);
    }


    @Override
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO,false);
        order.setShippingFee(calculatorFactory.getCalculator(orderDTO).calculateShippingFee(orderDTO));
        order.setOrderStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        auditLogService.logOrderAction(order.getUser().getId(), order.getOrderId(), UserRole.CUSTOMER, ActionType.PLACE_ORDER);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Override
    public RushOrderDTO placeRushOrder(RushOrderDTO rushOrderDTO) {
        //cần xem lại logic của checkRushProductEligibility vì nó check từng item trong order chứ ko phải check userId
        if (!checkRushProductEligibility(rushOrderDTO.getCustomerId()) ||
                !checkRushDeliveryAddress(rushOrderDTO.getDeliveryInfo().getAddress())) {
            throw new IllegalStateException("Rush order not eligible");
        }
        //need check
        Order order = orderMapper.toEntity(rushOrderDTO, true);
        order.setShippingFee(calculatorFactory.getCalculator(rushOrderDTO).calculateShippingFee(rushOrderDTO));
        order.setOrderStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        auditLogService.logOrderAction(order.getUser().getId(), order.getOrderId(), UserRole.CUSTOMER, ActionType.PLACE_ORDER);
        return mapToRushOrderDTO(savedOrder, rushOrderDTO.getRushDeliveryTime());
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        //need check
        auditLogService.logOrderAction(order.getUser().getId(), orderId, UserRole.CUSTOMER, ActionType.CANCEL_ORDER);
    }

    @Override
    public OrderDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return orderMapper.toOrderDTO(order);
    }

//    @Override
//    public Boolean checkAvailability(CartDTO cartDTO) {
//        return cartService.checkAvailability(cartDTO);
//    }

    @Override
    public Boolean validateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO) {
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
        order.setDeliveryInfo(deliveryInfoMapper.toEntity(deliveryInfoDTO));
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Override
    public Boolean isRushOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> "RUSH_PLACED".equals(order.getOrderStatus()))
                .orElse(false);
    }

    @Override
    public void setPending(Long orderId) {
        orderRepository.updateOrderStatus(orderId, OrderStatus.PENDING);
    }

    @Override
    public Boolean checkRushDeliveryAddress(String address) {
        //need more check
        return address != null && address.contains("Hanoi"); //example
    }


    //need more check
    @Override
    public Boolean checkCancellationValidity(Long orderId) {
        return true;
    }


//    @Override
//    public Boolean checkRushProductEligibility(Long productId) {
//        return cartService.checkRushEligibility(userId); // Delegate to CartService
//    }

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
    public Optional<Order> findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public void updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) {
        orderRepository.updatePaymentStatus(orderId, paymentStatus);
    }

    @Override
    public List<OrderDTO> getOrderHistory(Integer customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }


}
