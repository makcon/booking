package test.makcon.domain.mother;

import test.makcon.domain.command.booking.UpdateBookingCommand;
import test.makcon.domain.command.booking.UpdateBookingCommand.UpdateBookingCommandBuilder;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UpdateBookingCommandMother {

    public static UpdateBookingCommandBuilder randomBuilder() {
        return UpdateBookingCommand.builder()
                .bookingId(UUID.randomUUID().toString())
                .version(new Random().nextLong())
                .period(PeriodMother.random())
                .guests(List.of(GuestMother.random()));
    }

    public static UpdateBookingCommand random() {
        return randomBuilder().build();
    }
}
