package test.makcon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookingV1 {
    private final String id;
    private final Long version;
    private final String propertyId;
    private final DateRangeV1 period;
    private final String status;
    private final List<GuestV1> guests;
}
