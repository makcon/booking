package test.makcon.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DateRangeV1 {
    private final LocalDate start;
    private final LocalDate end;
}
