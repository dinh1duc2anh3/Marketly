package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.VNPayConfig;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.enums.RefundStatus;
import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Component
public class VNPayConverter {
    // Cohesion: Functional Cohesion
    // → Class này tập trung 100% vào việc tạo VNPayRequest từ dữ liệu domain, không làm nhiệm vụ nào khác.

    // SRP: Không vi phạm
    // → Lớp chỉ đảm nhiệm việc xây dựng request (builder cho transaction).

    // Build payment request
//    protected VNPayRequest buildPaymentRequest(Long orderId, Float amount, String content) {
//        return VNPayRequest.builder()
//                .orderId(orderId)
//                .amount(amount)
//                .content(content)
//                .transactionType(TransactionType.PAYMENT)
//                .bankCode("VNB") // Default to domestic bank
//                .language("vn")     // Default to Vietnamese
//                .build();
//    }

    private final VNPayConfig vnPayConfig;

    public VNPayConverter(VNPayConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }



    public Map<String, String> buildPaymentParams(Long orderId, Float amount, String content, HttpServletRequest request) {
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnPayConfig.vnp_TmnCode);
        vnpParams.put("vnp_Amount", String.valueOf((long) (amount * 100)));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_BankCode", "NCB");
        vnpParams.put("vnp_TxnRef", VNPayConfig.getRandomNumber(8));
        vnpParams.put("vnp_OrderInfo", content);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnPayConfig.vnp_ReturnUrl);
        vnpParams.put("vnp_IpAddr", VNPayConfig.getIpAddress(request));
        vnpParams.put("vnp_IpnUrl", vnPayConfig.vnp_IpnUrl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnpParams.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnpParams.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        return vnpParams;
    }

    protected VNPayRequest buildPaymentRequest(Long orderId, Float amount, String content) {
        return VNPayRequest.builder()
                .orderId(orderId)
                .amount(amount)
                .content(content)
                .transactionType(TransactionType.PAYMENT)
                .bankCode("NCB")
                .language("vn")
                .build();
    }

    protected VNPayRequest buildRefundRequest(Long orderId) {
        return VNPayRequest.builder()
                .orderId(orderId)
                .transactionType(TransactionType.REFUND)
                .build();
    }

    // Build payment request with custom bank code
    protected VNPayRequest buildPaymentRequest(Long orderId, Float amount, String content, String bankCode, String language) {
        return VNPayRequest.builder()
                .orderId(orderId)
                .amount(amount)
                .content(content)
                .transactionType(TransactionType.PAYMENT)
                .bankCode(bankCode)
                .language(language)
                .build();
    }
}
