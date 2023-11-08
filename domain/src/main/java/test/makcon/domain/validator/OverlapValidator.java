package test.makcon.domain.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.model.DateRange;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OverlapValidator {

    public boolean isAvailable(DateRange currentRange, List<DateRange> bookedRanges) {
        for (DateRange bookedRange : bookedRanges) {
            if (currentRange.getEnd().isAfter(bookedRange.getStart()) &&
                    currentRange.getStart().isBefore(bookedRange.getEnd())) {
                return false;
            }
        }

        return true;
    }
}
