package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.ConstructionOrder;
import com.swp_group4.back_end.requests.StaffAssignedRequest;
import com.swp_group4.back_end.responses.ConstructOrderDetailForCustomerResponse;
import com.swp_group4.back_end.responses.ConstructOrderDetailForManagerResponse;
import com.swp_group4.back_end.responses.ConstructOrderResponse;
import com.swp_group4.back_end.responses.ConstructQuotationResponse;
import com.swp_group4.back_end.responses.ProgressReviewResponse;
import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class ConstructionOrderMapperImpl implements ConstructionOrderMapper {

    @Override
    public ConstructOrderResponse constructOrderResponse(ConstructionOrder constructionOrder, ConstructOrderResponse response) {
        if ( constructionOrder == null ) {
            return response;
        }

        response.setConstructionOrderId( constructionOrder.getConstructionOrderId() );
        response.setCustomerId( constructionOrder.getCustomerId() );
        response.setCustomerRequest( constructionOrder.getCustomerRequest() );
        response.setTotal( constructionOrder.getTotal() );
        response.setStatus( constructionOrder.getStatus() );

        return response;
    }

    @Override
    public ConstructionOrder toConstructionOrder(StaffAssignedRequest request, ConstructionOrder order) {
        if ( request == null ) {
            return order;
        }

        if ( request.getConstructionOrderId() != null ) {
            order.setConstructionOrderId( request.getConstructionOrderId() );
        }
        if ( request.getConsultantId() != null ) {
            order.setConsultantId( request.getConsultantId() );
        }
        if ( request.getDesignerLeaderId() != null ) {
            order.setDesignerLeaderId( request.getDesignerLeaderId() );
        }
        if ( request.getConstructorLeaderId() != null ) {
            order.setConstructorLeaderId( request.getConstructorLeaderId() );
        }
        if ( request.getStatus() != null ) {
            order.setStatus( request.getStatus() );
        }

        return order;
    }

    @Override
    public ConstructOrderDetailForManagerResponse toDetailForManager(ConstructionOrder order, ConstructOrderDetailForManagerResponse detail) {
        if ( order == null ) {
            return detail;
        }

        if ( order.getConstructionOrderId() != null ) {
            detail.setOrderId( order.getConstructionOrderId() );
        }
        detail.setTotalPrice( order.getTotal() );
        if ( order.getConstructionEndDate() != null ) {
            detail.setEndDate( order.getConstructionEndDate() );
        }
        if ( order.getStartDate() != null ) {
            detail.setStartDate( Date.from( order.getStartDate().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( order.getConsultantId() != null ) {
            detail.setConsultantId( order.getConsultantId() );
        }
        if ( order.getDesignerLeaderId() != null ) {
            detail.setDesignerLeaderId( order.getDesignerLeaderId() );
        }
        if ( order.getConstructorLeaderId() != null ) {
            detail.setConstructorLeaderId( order.getConstructorLeaderId() );
        }
        if ( order.getStatus() != null ) {
            detail.setStatus( order.getStatus() );
        }

        return detail;
    }

    @Override
    public ConstructQuotationResponse toConstructQuotationResponse(ConstructionOrder order, ConstructQuotationResponse response) {
        if ( order == null ) {
            return response;
        }

        response.setTotalPrice( order.getTotal() );
        response.setConstructionOrderStatus( order.getStatus() );
        response.setQuotationId( order.getQuotationId() );
        if ( order.getStartDate() != null ) {
            response.setStartDate( Date.from( order.getStartDate().toInstant( ZoneOffset.UTC ) ) );
        }
        else {
            response.setStartDate( null );
        }
        response.setCustomerRequest( order.getCustomerRequest() );

        return response;
    }

    @Override
    public ConstructOrderDetailForCustomerResponse toDetailForCustomerResponse(ConstructionOrder order, ConstructOrderDetailForCustomerResponse response) {
        if ( order == null ) {
            return response;
        }

        response.setEndDate( order.getConstructionEndDate() );
        response.setConstructionOrderId( order.getConstructionOrderId() );
        response.setQuotationId( order.getQuotationId() );
        response.setDesignId( order.getDesignId() );
        response.setStartDate( order.getStartDate() );
        response.setStatus( order.getStatus() );

        return response;
    }

    @Override
    public ProgressReviewResponse toProgressReviewResponse(ConstructionOrder order, ProgressReviewResponse response) {
        if ( order == null ) {
            return response;
        }

        response.setStartDate( order.getConstructionStartDate() );
        response.setEndDate( order.getConstructionEndDate() );
        response.setConstructionOrderId( order.getConstructionOrderId() );
        response.setStatus( order.getStatus() );

        return response;
    }
}
