package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.MaintenanceOrder;
import com.swp_group4.back_end.requests.MaintenanceStaffAssignedRequest;
import com.swp_group4.back_end.responses.MaintenanceOrderDetailForManagerResponse;
import com.swp_group4.back_end.responses.MaintenanceOrderResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class MaintenanceOrderMapperImpl implements MaintenanceOrderMapper {

    @Override
    public MaintenanceOrderResponse maintenanceOrderResponse(MaintenanceOrder maintenanceOrder, MaintenanceOrderResponse response) {
        if ( maintenanceOrder == null ) {
            return response;
        }

        response.setMaintenanceOrderId( maintenanceOrder.getMaintenanceOrderId() );
        response.setStatus( maintenanceOrder.getStatus() );

        return response;
    }

    @Override
    public MaintenanceOrder toMaintenanceOrder(MaintenanceStaffAssignedRequest request, MaintenanceOrder order) {
        if ( request == null ) {
            return order;
        }

        if ( request.getMaintenanceOrderId() != null ) {
            order.setMaintenanceOrderId( request.getMaintenanceOrderId() );
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
    public MaintenanceOrderDetailForManagerResponse toMaintenanceOrderDetailForManager(MaintenanceOrder order, MaintenanceOrderDetailForManagerResponse detail) {
        if ( order == null ) {
            return detail;
        }

        if ( order.getMaintenanceOrderId() != null ) {
            detail.setOrderId( order.getMaintenanceOrderId() );
        }
        if ( order.getTotal() != null ) {
            detail.setTotalPrice( order.getTotal() );
        }
        if ( order.getStartDate() != null ) {
            detail.setStartDate( order.getStartDate() );
        }
        if ( order.getConstructorLeaderId() != null ) {
            detail.setConstructorLeaderId( order.getConstructorLeaderId() );
        }
        if ( order.getStatus() != null ) {
            detail.setStatus( order.getStatus() );
        }

        return detail;
    }
}
