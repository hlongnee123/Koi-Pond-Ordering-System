package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.Customer;
import com.swp_group4.back_end.requests.ServiceRequest;
import com.swp_group4.back_end.requests.UpdateInfoRequest;
import com.swp_group4.back_end.responses.AllCustomerInfoResponse;
import com.swp_group4.back_end.responses.ConstructOrderDetailForManagerResponse;
import com.swp_group4.back_end.responses.CustomerResponse;
import com.swp_group4.back_end.responses.MaintenanceOrderDetailForManagerResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer serviceRequestToCustomer(ServiceRequest request, Customer customer) {
        if ( request == null ) {
            return customer;
        }

        customer.setFirstName( request.getFirstName() );
        customer.setLastName( request.getLastName() );
        customer.setPhone( request.getPhone() );
        customer.setAddress( request.getAddress() );

        return customer;
    }

    @Override
    public Customer updateInfoToCustomer(UpdateInfoRequest request, Customer customer) {
        if ( request == null ) {
            return customer;
        }

        customer.setFirstName( request.getFirstName() );
        customer.setLastName( request.getLastName() );
        customer.setPhone( request.getPhone() );
        customer.setAddress( request.getAddress() );

        return customer;
    }

    @Override
    public CustomerResponse customerToResponse(Customer customer, CustomerResponse customerResponse) {
        if ( customer == null ) {
            return customerResponse;
        }

        customerResponse.setFirstName( customer.getFirstName() );
        customerResponse.setLastName( customer.getLastName() );
        customerResponse.setPhone( customer.getPhone() );
        customerResponse.setAddress( customer.getAddress() );

        return customerResponse;
    }

    @Override
    public ConstructOrderDetailForManagerResponse toDetailForManager(Customer customer, ConstructOrderDetailForManagerResponse detail) {
        if ( customer == null ) {
            return detail;
        }

        if ( customer.getPhone() != null ) {
            detail.setPhone( customer.getPhone() );
        }
        if ( customer.getAddress() != null ) {
            detail.setAddress( customer.getAddress() );
        }

        return detail;
    }

    @Override
    public MaintenanceOrderDetailForManagerResponse toMaintenanceDetailForManager(Customer customer, MaintenanceOrderDetailForManagerResponse detail) {
        if ( customer == null ) {
            return detail;
        }

        if ( customer.getPhone() != null ) {
            detail.setPhone( customer.getPhone() );
        }
        if ( customer.getAddress() != null ) {
            detail.setAddress( customer.getAddress() );
        }

        return detail;
    }

    @Override
    public AllCustomerInfoResponse toAllCustomerInfoResponse(Customer customer, AllCustomerInfoResponse customerResponse) {
        if ( customer == null ) {
            return customerResponse;
        }

        customerResponse.setFirstName( customer.getFirstName() );
        customerResponse.setLastName( customer.getLastName() );
        customerResponse.setPhone( customer.getPhone() );
        customerResponse.setPoint( customer.getPoint() );
        customerResponse.setAddress( customer.getAddress() );
        customerResponse.setAvatarURL( customer.getAvatarURL() );

        return customerResponse;
    }
}
