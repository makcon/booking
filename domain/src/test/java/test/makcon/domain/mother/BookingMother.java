package test.makcon.domain.mother;


import test.makcon.domain.model.Booking;
import test.makcon.domain.model.BookingStatus;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BookingMother {

    public static Booking random() {
        return Booking.builder()
                .id(UUID.randomUUID().toString())
                .version(new Random().nextLong())
                .propertyId(UUID.randomUUID().toString())
                .period(PeriodMother.random())
                .status(BookingStatus.values()[new Random().nextInt(BookingStatus.values().length)])
                .guests(List.of(GuestMother.random()))
                .build();
    }
}
