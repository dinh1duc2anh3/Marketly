import static org.junit.jupiter.api.Assertions.*;

import com.darian.ecommerce.entity.Order;
import com.darian.ecommerce.enums.OrderStatus;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.repository.OrderRepository;
import com.darian.ecommerce.service.OrderService;
import com.darian.ecommerce.service.PaymentService;
import com.darian.ecommerce.service.impl.OrderServiceImpl;
import com.darian.ecommerce.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

public class PaymentServiceTest {
    private PaymentServiceImpl paymentService;
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        // Mock OrderRepository

        orderService = Mockito.mock(OrderServiceImpl.class);
        paymentService = new PaymentServiceImpl(null, null, orderService, null);

    }

    @Test
    public void testValidatePaymentWhenOrderIsConfirmedAndUnpaid() {
        // Arrange: Tạo một Order với trạng thái CONFIRMED và UNPAID
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.UNPAID);

        // Mock repository trả về order này khi tìm theo orderIdx
        Mockito.when(orderService.findOrderById(1L)).thenReturn(Optional.of(order));

        // Act: Gọi hàm validatePayment
        Boolean result = paymentService.validatePayment(1L);

        // Assert: Kiểm tra kết quả trả về là true
        assertTrue(result);
    }

    @Test
    public void testValidatePaymentWhenOrderIsPending() {
        // Arrange: Tạo một Order với trạng thái PENDING và UNPAID
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.UNPAID);

        // Mock repository trả về order này khi tìm theo orderId
        Mockito.when(orderService.findOrderById(2L)).thenReturn(Optional.of(order));

        // Act: Gọi hàm validatePayment
        Boolean result = paymentService.validatePayment(2L);

        // Assert: Kiểm tra kết quả trả về là false
        assertFalse(result);
    }

    @Test
    public void testValidatePaymentWhenOrderIsPaid() {
        // Arrange: Tạo một Order với trạng thái CONFIRMED và PAID
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PAID);

        // Mock repository trả về order này khi tìm theo orderId
        Mockito.when(orderService.findOrderById(3L)).thenReturn(Optional.of(order));

        // Act: Gọi hàm validatePayment
        Boolean result = paymentService.validatePayment(3L);

        // Assert: Kiểm tra kết quả trả về là false
        assertFalse(result);
    }

    @Test
    public void testValidatePaymentWhenOrderIsCancelled() {
        // Arrange: Tạo một Order với trạng thái CANCELLED và UNPAID
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setPaymentStatus(PaymentStatus.UNPAID);

        // Mock repository trả về order này khi tìm theo orderId
        Mockito.when(orderService.findOrderById(4L)).thenReturn(Optional.of(order));

        // Act: Gọi hàm validatePayment
        Boolean result = paymentService.validatePayment(4L);

        // Assert: Kiểm tra kết quả trả về là false
        assertFalse(result);
    }

    @Test
    public void testValidatePaymentWhenOrderNotFound() {
        // Arrange: Mock repository trả về Optional.empty()
        Mockito.when(orderService.findOrderById(5L)).thenReturn(Optional.empty());

        // Act: Gọi hàm validatePayment
        Boolean result = paymentService.validatePayment(5L);

        // Assert: Kiểm tra kết quả trả về là false vì không tìm thấy đơn hàng
        assertFalse(result);
    }
}
