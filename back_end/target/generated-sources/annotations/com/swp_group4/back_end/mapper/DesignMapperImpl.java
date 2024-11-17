package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.Design;
import com.swp_group4.back_end.responses.ConstructDesignResponse;
import com.swp_group4.back_end.responses.ViewRejectedDesignResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class DesignMapperImpl implements DesignMapper {

    @Override
    public ConstructDesignResponse toDesignResponse(Design design, ConstructDesignResponse constructDesignResponse) {
        if ( design == null ) {
            return constructDesignResponse;
        }

        constructDesignResponse.setDesignId( design.getDesignId() );
        constructDesignResponse.setUrl2dDesign( design.getUrl2dDesign() );
        constructDesignResponse.setUrl3dDesign( design.getUrl3dDesign() );
        constructDesignResponse.setUrlFrontDesign( design.getUrlFrontDesign() );
        constructDesignResponse.setDesignStatus( design.getDesignStatus() );

        return constructDesignResponse;
    }

    @Override
    public ViewRejectedDesignResponse toViewRejectedDesignResponse(Design design, ViewRejectedDesignResponse viewRejectedDesignResponse) {
        if ( design == null ) {
            return viewRejectedDesignResponse;
        }

        viewRejectedDesignResponse.setUrl2dDesign( design.getUrl2dDesign() );
        viewRejectedDesignResponse.setUrl3dDesign( design.getUrl3dDesign() );
        viewRejectedDesignResponse.setUrlFrontDesign( design.getUrlFrontDesign() );

        return viewRejectedDesignResponse;
    }
}
