package test.makcon.domain.command.booking.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.makcon.domain.command.booking.UpdateBookingCommand;
import test.makcon.domain.exception.ModelNotExistsException;
import test.makcon.domain.exception.OverlapException;
import test.makcon.domain.model.Booking;
import test.makcon.domain.mother.BookingMother;
import test.makcon.domain.mother.UpdateBookingCommandMother;
import test.makcon.domain.port.BookingRepositoryPort;
import test.makcon.domain.validator.PropertyAvailabilityValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateBookingCommandHandlerShould {

    private final Booking storedBooking = BookingMother.random();
    @InjectMocks
    private UpdateBookingCommandHandler handler;
    @Mock
    private PropertyAvailabilityValidator availabilityValidator;
    @Mock
    private BookingRepositoryPort repository;
    @Captor
    private ArgumentCaptor<Booking> bookingCaptor;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository, availabilityValidator);
    }

    @Test
    void call_repo_to_update_booking_when_exists_and_period_is_valid() {
        // given
        UpdateBookingCommand givenCommand = UpdateBookingCommandMother.random();
        when(repository.findByIdRequired(any())).thenReturn(storedBooking);
        doNothing().when(availabilityValidator).validate(any(), any());

        // when
        handler.handle(givenCommand);

        // then
        verify(repository).findByIdRequired(givenCommand.getBookingId());
        verify(availabilityValidator).validate(givenCommand.getPeriod(), storedBooking.getPropertyId());
        verify(repository).update(bookingCaptor.capture());
        assertEquals(storedBooking.getId(), bookingCaptor.getValue().getId());
        assertEquals(storedBooking.getPropertyId(), bookingCaptor.getValue().getPropertyId());
        assertEquals(storedBooking.getStatus(), bookingCaptor.getValue().getStatus());
        assertEquals(givenCommand.getVersion(), bookingCaptor.getValue().getVersion());
        assertEquals(givenCommand.getPeriod(), bookingCaptor.getValue().getPeriod());
        assertEquals(givenCommand.getGuests(), bookingCaptor.getValue().getGuests());
    }

    @Test
    void not_call_repo_to_update_booking_when_not_exists() {
        // given
        UpdateBookingCommand givenCommand = UpdateBookingCommandMother.random();
        ModelNotExistsException expectedException = mock(ModelNotExistsException.class);
        doThrow(expectedException).when(repository).findByIdRequired(any());

        // when
        assertThrows(ModelNotExistsException.class, () -> handler.handle(givenCommand));

        // then
        verify(repository).findByIdRequired(givenCommand.getBookingId());
        verify(availabilityValidator, never()).validate(any(), any());
        verify(repository, never()).update(bookingCaptor.capture());
    }

    @Test
    void not_call_period_validator_when_period_is_the_same() {
        // given
        UpdateBookingCommand givenCommand = UpdateBookingCommandMother.randomBuilder()
                .period(storedBooking.getPeriod())
                .build();
        when(repository.findByIdRequired(any())).thenReturn(storedBooking);

        // when
        handler.handle(givenCommand);

        // then
        verify(repository).findByIdRequired(givenCommand.getBookingId());
        verify(availabilityValidator, never()).validate(any(), any());
        verify(repository).update(bookingCaptor.capture());
    }

    @Test
    void not_call_repo_to_update_booking_when_exists_but_period_is_invalid() {
        // given
        UpdateBookingCommand givenCommand = UpdateBookingCommandMother.random();
        OverlapException expectedException = mock(OverlapException.class);
        when(repository.findByIdRequired(any())).thenReturn(storedBooking);
        doThrow(expectedException).when(availabilityValidator).validate(any(), any());

        // when
        assertThrows(OverlapException.class, () -> handler.handle(givenCommand));

        // then
        verify(repository).findByIdRequired(givenCommand.getBookingId());
        verify(availabilityValidator).validate(givenCommand.getPeriod(), storedBooking.getPropertyId());
        verify(repository, never()).update(bookingCaptor.capture());
    }
}