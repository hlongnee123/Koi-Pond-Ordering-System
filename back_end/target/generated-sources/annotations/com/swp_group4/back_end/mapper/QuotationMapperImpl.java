package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.Quotation;
import com.swp_group4.back_end.requests.ExportQuotationRequest;
import com.swp_group4.back_end.responses.ConstructQuotationResponse;
import com.swp_group4.back_end.responses.GeneratePDFResponse;
import com.swp_group4.back_end.responses.ViewRejectedQuotationResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class QuotationMapperImpl implements QuotationMapper {

    @Override
    public Quotation toQuotation(ExportQuotationRequest request, Quotation quotation) {
        if ( request == null ) {
            return quotation;
        }

        quotation.setExpectedStartDate( request.getStartDate() );
        quotation.setExpectedEndDate( request.getEndDate() );
        quotation.setLength( request.getLength() );
        quotation.setWidth( request.getWidth() );
        quotation.setHeight( request.getHeight() );
        quotation.setPercentageStage1( request.getPercentageStage1() );
        quotation.setPercentageStage2( request.getPercentageStage2() );
        quotation.setPercentageStage3( request.getPercentageStage3() );
        quotation.setPromotionId( request.getPromotionId() );
        quotation.setPackageId( request.getPackageId() );

        return quotation;
    }

    @Override
    public ConstructQuotationResponse toQuotationResponse(Quotation quotation, ConstructQuotationResponse constructQuotationResponse) {
        if ( quotation == null ) {
            return constructQuotationResponse;
        }

        constructQuotationResponse.setStartDate( quotation.getExpectedStartDate() );
        constructQuotationResponse.setEndDate( quotation.getExpectedEndDate() );
        constructQuotationResponse.setQuotationId( quotation.getQuotationId() );
        constructQuotationResponse.setVolume( quotation.getVolume() );
        constructQuotationResponse.setPercentageStage1( quotation.getPercentageStage1() );
        constructQuotationResponse.setPercentageStage2( quotation.getPercentageStage2() );
        constructQuotationResponse.setPercentageStage3( quotation.getPercentageStage3() );
        constructQuotationResponse.setQuotationStatus( quotation.getQuotationStatus() );

        return constructQuotationResponse;
    }

    @Override
    public ViewRejectedQuotationResponse toViewRejectedQuotationResponse(Quotation quotation, ViewRejectedQuotationResponse viewRejectedQuotationResponse) {
        if ( quotation == null ) {
            return viewRejectedQuotationResponse;
        }

        viewRejectedQuotationResponse.setStartDate( quotation.getExpectedStartDate() );
        viewRejectedQuotationResponse.setEndDate( quotation.getExpectedEndDate() );
        viewRejectedQuotationResponse.setPercentageStage1( quotation.getPercentageStage1() );
        viewRejectedQuotationResponse.setPercentageStage2( quotation.getPercentageStage2() );
        viewRejectedQuotationResponse.setPercentageStage3( quotation.getPercentageStage3() );
        viewRejectedQuotationResponse.setLength( quotation.getLength() );
        viewRejectedQuotationResponse.setHeight( quotation.getHeight() );
        viewRejectedQuotationResponse.setWidth( quotation.getWidth() );
        viewRejectedQuotationResponse.setPackageId( quotation.getPackageId() );

        return viewRejectedQuotationResponse;
    }

    @Override
    public GeneratePDFResponse toGeneratePdfResponse(Quotation quotation, GeneratePDFResponse generatePDFResponse) {
        if ( quotation == null ) {
            return generatePDFResponse;
        }

        generatePDFResponse.setConstructionStartDate( quotation.getExpectedStartDate() );
        generatePDFResponse.setConstructionEndDate( quotation.getExpectedEndDate() );
        generatePDFResponse.setVolume( quotation.getVolume() );
        generatePDFResponse.setPercentageStage1( quotation.getPercentageStage1() );
        generatePDFResponse.setPercentageStage2( quotation.getPercentageStage2() );
        generatePDFResponse.setPercentageStage3( quotation.getPercentageStage3() );
        generatePDFResponse.setPostedDate( quotation.getPostedDate() );

        return generatePDFResponse;
    }
}
