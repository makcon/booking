package test.makcon.adapters.repository.doc;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.model.DateRange;

@Data
@Builder
@Document
@CompoundIndex(def = "{'propertyId': 1, 'status': 1}")
public class BlockDoc {
    private final String id;
    @Version
    private final Long version;
    private final String propertyId;
    private final DateRange period;
    private final BookingStatus status;
    private final String reason;
}
