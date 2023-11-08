package test.makcon.rest.mother;

import test.makcon.dto.request.CreateBookingRequestParamsV1;

import java.util.List;
import java.util.UUID;

public class CreateBookingRequestParamsV1Mother {

    public static CreateBookingRequestParamsV1 random() {
        return CreateBookingRequestParamsV1.builder()
                .propertyId(UUID.randomUUID().toString())
                .period(PeriodV1Mother.random())
                .guests(List.of(GuestV1Mother.random()))
                .build();
    }
}
