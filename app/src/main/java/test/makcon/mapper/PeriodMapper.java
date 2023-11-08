package test.makcon.mapper;

import org.springframework.stereotype.Component;
import test.makcon.domain.model.DateRange;
import test.makcon.dto.DateRangeV1;

@Component
public class PeriodMapper {

    public DateRange toModel(DateRangeV1 dto) {
        return DateRange.builder()
                .start(dto.getStart())
                .end(dto.getEnd())
                .build();
    }

    public DateRangeV1 toDto(DateRange model) {
        return DateRangeV1.builder()
                .start(model.getStart())
                .end(model.getEnd())
                .build();
    }
}
