package test.makcon.domain.command.block;

import lombok.Builder;
import lombok.Data;
import test.makcon.domain.model.DateRange;

@Data
@Builder
public class UpdateBlockCommand {
    private final String blockId;
    private final Long version;
    private final DateRange period;
    private final String reason;
}
