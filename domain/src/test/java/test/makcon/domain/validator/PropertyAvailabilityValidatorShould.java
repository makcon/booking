package test.makcon.domain.validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.makcon.domain.exception.OverlapException;
import test.makcon.domain.model.Block;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.DateRange;
import test.makcon.domain.mother.BlockMother;
import test.makcon.domain.mother.BookingMother;
import test.makcon.domain.mother.PeriodMother;
import test.makcon.domain.port.BlockRepositoryPort;
import test.makcon.domain.port.BookingRepositoryPort;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyAvailabilityValidatorShould {

    @InjectMocks
    private PropertyAvailabilityValidator validator;

    @Mock
    private BookingRepositoryPort bookingRepository;
    @Mock
    private BlockRepositoryPort blockRepository;
    @Mock
    private OverlapValidator overlapValidator;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                bookingRepository,
                blockRepository,
                overlapValidator
        );
    }

    @Test
    void be_available_when_validator_returns_true_for_booking() {
        // given
        DateRange givenPeriod = PeriodMother.random();
        String givenPropertyId = UUID.randomUUID().toString();
        Booking storedBooking = BookingMother.random();
        when(bookingRepository.findPending(any())).thenReturn(List.of(storedBooking));
        when(blockRepository.findPending(any())).thenReturn(List.of());
        when(overlapValidator.isAvailable(any(), any())).thenReturn(true);

        // when
        assertDoesNotThrow(() -> validator.validate(givenPeriod, givenPropertyId));

        // then
        verify(bookingRepository).findPending(givenPropertyId);
        verify(blockRepository).findPending(givenPropertyId);
        verify(overlapValidator).isAvailable(givenPeriod, List.of(storedBooking.getPeriod()));
    }

    @Test
    void be_available_when_validator_returns_true_for_blocks() {
        // given
        DateRange givenPeriod = PeriodMother.random();
        String givenPropertyId = UUID.randomUUID().toString();
        Block storedBlock = BlockMother.random();
        when(bookingRepository.findPending(any())).thenReturn(List.of());
        when(blockRepository.findPending(any())).thenReturn(List.of(storedBlock));
        when(overlapValidator.isAvailable(any(), any())).thenReturn(true);

        // when
        assertDoesNotThrow(() -> validator.validate(givenPeriod, givenPropertyId));

        // then
        verify(bookingRepository).findPending(givenPropertyId);
        verify(blockRepository).findPending(givenPropertyId);
        verify(overlapValidator).isAvailable(givenPeriod, List.of(storedBlock.getPeriod()));
    }

    @Test
    void be_available_when_validator_returns_true_for_booking_and_blocks() {
        // given
        DateRange givenPeriod = PeriodMother.random();
        String givenPropertyId = UUID.randomUUID().toString();
        Booking storedBooking = BookingMother.random();
        Block storedBlock = BlockMother.random();
        when(bookingRepository.findPending(any())).thenReturn(List.of(storedBooking));
        when(blockRepository.findPending(any())).thenReturn(List.of(storedBlock));
        when(overlapValidator.isAvailable(any(), any())).thenReturn(true);

        // when
        assertDoesNotThrow(() -> validator.validate(givenPeriod, givenPropertyId));

        // then
        verify(bookingRepository).findPending(givenPropertyId);
        verify(blockRepository).findPending(givenPropertyId);
        verify(overlapValidator).isAvailable(givenPeriod, List.of(storedBooking.getPeriod(), storedBlock.getPeriod()));
    }

    @Test
    void not_be_available_when_validator_returns_false() {
        // given
        DateRange givenPeriod = PeriodMother.random();
        String givenPropertyId = UUID.randomUUID().toString();
        Booking storedBooking = BookingMother.random();
        when(bookingRepository.findPending(any())).thenReturn(List.of(storedBooking));
        when(blockRepository.findPending(any())).thenReturn(List.of());
        when(overlapValidator.isAvailable(any(), any())).thenReturn(false);

        // when
        assertThrows(OverlapException.class, () -> validator.validate(givenPeriod, givenPropertyId));

        // then
        verify(bookingRepository).findPending(givenPropertyId);
        verify(blockRepository).findPending(givenPropertyId);
        verify(overlapValidator).isAvailable(givenPeriod, List.of(storedBooking.getPeriod()));
    }
}