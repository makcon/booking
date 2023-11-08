package test.makcon.domain.command.booking.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BookingRepositoryPort;

@Component
@RequiredArgsConstructor
public class CancelBookingCommandHandler {

    private final BookingRepositoryPort repository;

    public Booking handle(String bookingId) {
        Booking storedBooking = repository.findByIdRequired(bookingId);

        Booking booking = Booking.builder()
                .id(storedBooking.getId())
                .propertyId(storedBooking.getPropertyId())
                .status(BookingStatus.CANCELED)
                .version(storedBooking.getVersion())
                .period(storedBooking.getPeriod())
                .guests(storedBooking.getGuests())
                .build();

        return repository.update(booking);
    }
}
