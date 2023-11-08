package test.makcon.domain.command.block;

import lombok.Builder;
import lombok.Data;
import test.makcon.domain.model.DateRange;

@Data
@Builder
public class CreateBlockCommand {
    private final String propertyId;
    private final DateRange period;
    private final String reason;
}
