package com.darian.ecommerce.controller;

import com.darian.ecommerce.config.VNPayConfig;
import com.darian.ecommerce.config.exception.order.OrderNotFoundException;
import com.darian.ecommerce.config.exception.payment.InvalidPaymentMethodException;
import com.darian.ecommerce.dto.PaymentResDTO;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.enums.PaymentMethod;
import com.darian.ecommerce.service.PaymentService;
import com.darian.ecommerce.subsystem.vnpay.VNPayResponseHandler;
import com.darian.ecommerce.utils.ApiEndpoints;
import com.darian.ecommerce.utils.LoggerMessages;
import com.darian.ecommerce.utils.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(ApiEndpoints.PAYMENT)
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:8080",
    "http://127.0.0.1:3000",
    "http://127.0.0.1:8080"
}, allowCredentials = "true")
public class PaymentController {

    // Cohesion: Functional Cohesion
    // → Class chỉ phục vụ xử lý HTTP request liên quan đến thanh toán (pay/refund), không chứa logic nghiệp vụ.

    // SRP: Không vi phạm
    // → Lớp này chỉ đóng vai trò cầu nối giữa HTTP request và service xử lý logic thanh toán.

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;
    private final VNPayResponseHandler vnPayResponseHandler;

    // Constructor injection for PaymentServiceImpl
    public PaymentController(PaymentService paymentService, VNPayResponseHandler vnPayResponseHandler) {
        this.paymentService = paymentService;
        this.vnPayResponseHandler = vnPayResponseHandler;
    }

//    // Process payment for an order
//    @PostMapping(ApiEndpoints.PAYMENT_BY_ORDER)
//    public ResponseEntity<PaymentResult> payOrder(@PathVariable Long orderId,
//                                                  @RequestParam String paymentMethod) throws InvalidPaymentMethodException, OrderNotFoundException {
//        // Validate payment method
//        try {
//            PaymentMethod.valueOf(paymentMethod.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new InvalidPaymentMethodException(String.format(ErrorMessages.PAYMENT_INVALID_METHOD, paymentMethod));
//        }
//        log.info(LoggerMessages.PAYMENT_INIT, orderId, paymentMethod);
//        PaymentResult result = paymentService.payOrder(orderId, paymentMethod);
//        log.info(LoggerMessages.PAYMENT_COMPLETED, orderId, result.getPaymentStatus());
//        return ResponseEntity.ok(result);
//    }

    // Process refund for an order
    @PostMapping(ApiEndpoints.PAYMENT_REFUND)
    public ResponseEntity<RefundResult> processRefund(@PathVariable Long orderId) {
        log.info(LoggerMessages.PAYMENT_INIT, orderId, "refund");
        RefundResult result = paymentService.processRefund(orderId);
        log.info(LoggerMessages.PAYMENT_COMPLETED, orderId, result.getRefundStatus());
        return ResponseEntity.ok(result);
    }

    @GetMapping("create-payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String orderInfo = "Thanh toan don hang:" + vnp_TxnRef;
        
        // Convert amount to VND cents (amount * 100)
        long amount = 1000000 * 100;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        log.info("========== VNPAY PAYMENT INFO ==========");
        log.info("Hash Data: " + hashData.toString());
        log.info("Query URL: " + queryUrl);
        log.info("Payment URL: " + paymentUrl);
        log.info("=======================================");

        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("OK");
        paymentResDTO.setMessage("Successfully");
        paymentResDTO.setURL(paymentUrl);

        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
    }

//    @GetMapping("/vnpay-return")
//    public ResponseEntity<PaymentResult> vnPayReturn(HttpServletRequest request) {
//        Map<String, String> responseData = new HashMap<>();
//        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//            responseData.put(entry.getKey(), entry.getValue()[0]);
//        }
//
//        PaymentResult result = vnPayResponseHandler.processVNPayReturn(responseData);
//        return ResponseEntity.ok(result);
//    }
//
//    @PostMapping("/refund")
//    public ResponseEntity<PaymentResult> refundPayment(@RequestBody VNPayRequest request) {
//        PaymentResult result = vnPayResponseHandler.processRefund(request.getOrderId());
//        return ResponseEntity.ok(result);
//    }
}
