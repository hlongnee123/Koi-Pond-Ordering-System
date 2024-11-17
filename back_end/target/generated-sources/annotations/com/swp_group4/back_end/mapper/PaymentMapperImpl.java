package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.PaymentOrder;
import com.swp_group4.back_end.responses.PaymentInfoResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentInfoResponse toPaymentInfoResponse(PaymentOrder paymentOrder, PaymentInfoResponse paymentInfoResponse) {
        if ( paymentOrder == null ) {
            return paymentInfoResponse;
        }

        paymentInfoResponse.setPaymentId( paymentOrder.getPaymentId() );
        paymentInfoResponse.setPaymentTitle( paymentOrder.getPaymentTitle() );
        paymentInfoResponse.setPaidDate( paymentOrder.getPaidDate() );
        paymentInfoResponse.setDueDate( paymentOrder.getDueDate() );

        return paymentInfoResponse;
    }
}
