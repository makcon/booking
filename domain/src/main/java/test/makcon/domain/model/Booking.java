package test.makcon.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Booking {
    private final String id;
    private final Long version;
    private final String propertyId;
    private final DateRange period;
    private final BookingStatus status;
    private final List<Guest> guests;
}
