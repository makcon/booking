package test.makcon.domain.command.booking.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.makcon.domain.command.booking.CreateBookingCommand;
import test.makcon.domain.exception.OverlapException;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.mother.CreateBookingCommandMother;
import test.makcon.domain.port.BookingRepositoryPort;
import test.makcon.domain.validator.PropertyAvailabilityValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CreateBookingCommandHandlerShould {

    @InjectMocks
    private CreateBookingCommandHandler handler;

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
    void call_repo_to_create_booking_when_period_is_valid() {
        // given
        CreateBookingCommand givenCommand = CreateBookingCommandMother.random();
        doNothing().when(availabilityValidator).validate(any(), any());

        // when
        handler.handle(givenCommand);

        // then
        verify(availabilityValidator).validate(givenCommand.getPeriod(), givenCommand.getPropertyId());
        verify(repository).create(bookingCaptor.capture());
        assertNotNull(bookingCaptor.getValue().getId());
        assertEquals(givenCommand.getPropertyId(), bookingCaptor.getValue().getPropertyId());
        assertEquals(givenCommand.getPeriod(), bookingCaptor.getValue().getPeriod());
        assertEquals(BookingStatus.PENDING, bookingCaptor.getValue().getStatus());
        assertEquals(givenCommand.getGuests(), bookingCaptor.getValue().getGuests());
    }

    @Test
    void not_call_repo_to_create_booking_when_period_is_invalid() {
        // given
        CreateBookingCommand givenCommand = CreateBookingCommandMother.random();
        OverlapException expectedException = mock(OverlapException.class);
        doThrow(expectedException).when(availabilityValidator).validate(any(), any());

        // when
        assertThrows(OverlapException.class, () -> handler.handle(givenCommand));

        // then
        verify(availabilityValidator).validate(givenCommand.getPeriod(), givenCommand.getPropertyId());
        verify(repository, never()).create(bookingCaptor.capture());
    }
}