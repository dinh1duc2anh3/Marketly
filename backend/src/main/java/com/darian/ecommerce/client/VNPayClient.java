package com.darian.ecommerce.client;

import com.darian.ecommerce.converter.VNPayConverter;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;

public class VNPayClient {
    private VNPayConverter paymentConverter;
    private  VNPayConverter refundConverter;

    public VNPayResponse sendPaymentRequest(VNPayRequest request){

    }

    public VNPayResponse sendRefundRequest(VNPayRequest request){

    }

    public PaymentResult processPaymentResponse(VNPayResponse response){

    }

    public RefundResult processRefundResponse(VNPayResponse response){

    }

}
