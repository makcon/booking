package test.makcon.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import test.makcon.dto.DateRangeV1;

@Data
@Builder
@Jacksonized
public class CreateBlockRequestParamsV1 {
    private final String propertyId;
    private final DateRangeV1 period;
    private final String reason;
}
