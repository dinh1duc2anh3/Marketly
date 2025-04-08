package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.DeliveryInfoDTO;
import com.darian.ecommerce.dto.InvoiceDTO;
import com.darian.ecommerce.dto.OrderDTO;
import com.darian.ecommerce.dto.RushOrderDTO;
import com.darian.ecommerce.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO result = orderService.placeOrder(orderDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/place-rush")
    public ResponseEntity<RushOrderDTO> placeRushOrder(@RequestBody RushOrderDTO rushOrderDTO) {
        RushOrderDTO result = orderService.placeRushOrder(rushOrderDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable String orderId) {
        OrderDTO result = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{orderId}/delivery-info")
    public ResponseEntity<OrderDTO> setDeliveryInfo(@PathVariable String orderId,
                                                    @RequestBody DeliveryInfoDTO deliveryInfoDTO) {
        OrderDTO result = orderService.setDeliveryInfo(orderId, deliveryInfoDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{orderId}/invoice")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable String orderId) {
        InvoiceDTO result = orderService.getInvoice(orderId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<String> initiatePayment(@PathVariable String orderId,
                                                  @RequestParam String paymentMethod) {
        // Placeholder for payment initiation
        return ResponseEntity.ok("Payment initiated for order: " + orderId);
    }

}
