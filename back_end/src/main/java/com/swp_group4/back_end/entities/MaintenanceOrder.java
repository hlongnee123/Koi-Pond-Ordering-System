package com.swp_group4.back_end.entities;

import com.swp_group4.back_end.enums.MaintenanceOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MaintenanceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String maintenanceOrderId;
    String customerId;
    Double total;
    Date startDate;
    String constructorLeaderId;
    @Enumerated(EnumType.STRING)
    MaintenanceOrderStatus status;
}
