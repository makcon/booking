package test.makcon.domain.command.booking;

import lombok.Builder;
import lombok.Data;
import test.makcon.domain.model.DateRange;
import test.makcon.domain.model.Guest;

import java.util.List;

@Data
@Builder
public class UpdateBookingCommand {
    private final String bookingId;
    private final Long version;
    private final DateRange period;
    private final List<Guest> guests;
}
