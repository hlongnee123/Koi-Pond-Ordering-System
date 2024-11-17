package com.swp_group4.back_end.entities;

import com.swp_group4.back_end.enums.PaymentMethods;
import com.swp_group4.back_end.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String paymentId;
    String paymentTitle;
    String orderId;
    String customerId;
    LocalDateTime paidDate;
    LocalDateTime dueDate;
    PaymentMethods paymentMethods;
    Long total;
    @Enumerated(EnumType.STRING)
    PaymentStatus status;
}
