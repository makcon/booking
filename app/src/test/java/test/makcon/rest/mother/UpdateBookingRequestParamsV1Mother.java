package test.makcon.rest.mother;

import test.makcon.dto.request.UpdateBookingRequestParamsV1;

import java.util.List;

public class UpdateBookingRequestParamsV1Mother {

    public static UpdateBookingRequestParamsV1 random(Long version) {
        return UpdateBookingRequestParamsV1.builder()
                .version(version)
                .period(PeriodV1Mother.random())
                .guests(List.of(GuestV1Mother.random()))
                .build();
    }
}
