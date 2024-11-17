package com.swp_group4.back_end.responses;

import com.swp_group4.back_end.enums.ConstructionOrderStatus;
import com.swp_group4.back_end.enums.DesignStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ConstructDesignResponse {

    String designId;
    String constructionOrderId;
    String customerName;
    String designName;
    String customerRequest;
    String url2dDesign;
    String url3dDesign;
    String urlFrontDesign;
    String urlBackDesign;
    DesignStatus designStatus;
    ConstructionOrderStatus constructionOrderStatus;

}
