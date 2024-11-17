package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.Staff;
import com.swp_group4.back_end.responses.StaffResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class StaffMapperImpl implements StaffMapper {

    @Override
    public StaffResponse toStaffResponse(Staff staff, StaffResponse staffResponse) {
        if ( staff == null ) {
            return staffResponse;
        }

        staffResponse.setStaffId( staff.getStaffId() );
        staffResponse.setStaffName( staff.getStaffName() );

        return staffResponse;
    }
}
