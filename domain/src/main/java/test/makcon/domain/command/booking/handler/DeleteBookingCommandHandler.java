package test.makcon.domain.command.booking.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BookingRepositoryPort;

@Component
@RequiredArgsConstructor
public class DeleteBookingCommandHandler {

    private final BookingRepositoryPort repository;

    public Booking handle(String bookingId) {
        Booking storedBooking = repository.findByIdRequired(bookingId);

        /* There are different options to delete
            - Change status to deleted (let's use it here)
            - Add boolean flag deleted
            - Physically delete from DB but we need to have events or audit items
         */
        Booking booking = Booking.builder()
                .id(storedBooking.getId())
                .propertyId(storedBooking.getPropertyId())
                .status(BookingStatus.DELETED)
                .version(storedBooking.getVersion())
                .period(storedBooking.getPeriod())
                .guests(storedBooking.getGuests())
                .build();

        return repository.update(booking);
    }
}
