package test.makcon.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import test.makcon.dto.DateRangeV1;

@Data
@Builder
@Jacksonized
public class RebookBookingRequestParamsV1 {
    private final DateRangeV1 period;
}
