package test.makcon.domain.command.booking.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.command.booking.RebookBookingCommand;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BookingRepositoryPort;
import test.makcon.domain.validator.PropertyAvailabilityValidator;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RebookBookingCommandHandler {

    private final PropertyAvailabilityValidator availabilityValidator;
    private final BookingRepositoryPort repository;

    public Booking handle(RebookBookingCommand command) {
        Booking storedBooking = repository.findByIdRequired(command.getBookingId());
        availabilityValidator.validate(command.getPeriod(), storedBooking.getPropertyId());

        Booking booking = Booking.builder()
                .id(UUID.randomUUID().toString())
                .propertyId(storedBooking.getPropertyId())
                .status(BookingStatus.PENDING)
                .period(command.getPeriod())
                .guests(storedBooking.getGuests())
                .build();

        return repository.create(booking);
    }
}
