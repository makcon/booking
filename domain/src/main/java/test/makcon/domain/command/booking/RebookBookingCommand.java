package test.makcon.domain.command.booking;

import lombok.Builder;
import lombok.Data;
import test.makcon.domain.model.DateRange;

@Data
@Builder
public class RebookBookingCommand {
    private final String bookingId;
    private final DateRange period;
}
