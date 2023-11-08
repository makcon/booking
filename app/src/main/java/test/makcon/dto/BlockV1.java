package test.makcon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockV1 {
    private final String id;
    private final Long version;
    private final String propertyId;
    private final DateRangeV1 period;
    private final String status;
    private final String reason;
}
