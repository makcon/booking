package test.makcon.domain.command.booking.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.command.booking.UpdateBookingCommand;
import test.makcon.domain.model.Booking;
import test.makcon.domain.port.BookingRepositoryPort;
import test.makcon.domain.validator.PropertyAvailabilityValidator;

@Component
@RequiredArgsConstructor
public class UpdateBookingCommandHandler {

    private final PropertyAvailabilityValidator availabilityValidator;
    private final BookingRepositoryPort repository;

    public Booking handle(UpdateBookingCommand command) {
        Booking storedBooking = repository.findByIdRequired(command.getBookingId());
        // TODO prevent updating it in illegal statuses like CANCELED, ACTIVE

        if (!command.getPeriod().equals(storedBooking.getPeriod())) {
            availabilityValidator.validate(command.getPeriod(), storedBooking.getPropertyId());
        }

        Booking booking = Booking.builder()
                .id(storedBooking.getId())
                .propertyId(storedBooking.getPropertyId())
                .status(storedBooking.getStatus())
                .version(command.getVersion())
                .period(command.getPeriod())
                .guests(command.getGuests())
                .build();

        return repository.update(booking);
    }
}
