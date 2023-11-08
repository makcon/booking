package test.makcon.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DateRange {
    private LocalDate start;
    private LocalDate end;
}
