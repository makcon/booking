package test.makcon.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import test.makcon.dto.DateRangeV1;
import test.makcon.dto.GuestV1;

import java.util.List;

@Data
@Builder
@Jacksonized
public class UpdateBookingRequestParamsV1 {
    @Builder.Default
    private final Long version = 0L;
    private final DateRangeV1 period;
    private final List<GuestV1> guests;
}
