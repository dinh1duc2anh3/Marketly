package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfo {
    // Primary key
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the recipient
    private String recipientName;

    // Phone number of the recipient
    private String phoneNumber;

    // Email of the recipient
    private String email;

    // Province or city for delivery
    private String provinceCity;

    // Detailed address for delivery
    private String address;

    // Additional shipping instructions
    private String shippingInstructions;

}
