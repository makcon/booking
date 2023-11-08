package test.makcon.domain.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.exception.OverlapException;
import test.makcon.domain.model.Block;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.DateRange;
import test.makcon.domain.port.BlockRepositoryPort;
import test.makcon.domain.port.BookingRepositoryPort;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropertyAvailabilityValidator {

    private final BookingRepositoryPort bookingRepository;
    private final BlockRepositoryPort blockRepository;
    private final OverlapValidator overlapValidator;

    public void validate(DateRange currentPeriod, String propertyId) {
        List<DateRange> periods = bookingRepository.findPending(propertyId)
                .stream()
                .map(Booking::getPeriod)
                .collect(Collectors.toList());
        List<DateRange> blockPeriods = blockRepository.findPending(propertyId)
                .stream()
                .map(Block::getPeriod)
                .toList();
        periods.addAll(blockPeriods);

        if (overlapValidator.isAvailable(currentPeriod, periods)) return;

        throw new OverlapException();
    }
}
