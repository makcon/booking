package test.makcon.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Block {
    private final String id;
    private final Long version;
    private final String propertyId;
    private final DateRange period;
    private final BookingStatus status;
    private final String reason;
}
