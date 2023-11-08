package test.makcon.domain.mother;

import test.makcon.domain.command.booking.CreateBookingCommand;

import java.util.List;
import java.util.UUID;

public class CreateBookingCommandMother {

    public static CreateBookingCommand random() {
        return CreateBookingCommand.builder()
                .propertyId(UUID.randomUUID().toString())
                .period(PeriodMother.random())
                .guests(List.of(GuestMother.random()))
                .build();
    }
}
