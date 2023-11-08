package test.makcon.domain.command.booking.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.command.booking.CreateBookingCommand;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BookingRepositoryPort;
import test.makcon.domain.validator.PropertyAvailabilityValidator;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateBookingCommandHandler {

    private final PropertyAvailabilityValidator availabilityValidator;
    private final BookingRepositoryPort repository;

    public Booking handle(CreateBookingCommand command) {
        availabilityValidator.validate(command.getPeriod(), command.getPropertyId());

        Booking booking = Booking.builder()
                .id(UUID.randomUUID().toString())
                .propertyId(command.getPropertyId())
                .period(command.getPeriod())
                .status(BookingStatus.PENDING)
                .guests(command.getGuests())
                .build();

        return repository.create(booking);
    }
}
