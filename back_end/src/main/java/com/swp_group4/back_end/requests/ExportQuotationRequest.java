package com.swp_group4.back_end.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ExportQuotationRequest {

    String packageId;
    double length;
    double height;
    double width;
    double percentageStage1;
    double percentageStage2;
    double percentageStage3;
    String customerRequest;
    Date startDate;
    Date endDate;
    String promotionId;

}
