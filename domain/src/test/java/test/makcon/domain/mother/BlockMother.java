package test.makcon.domain.mother;


import test.makcon.domain.model.Block;
import test.makcon.domain.model.BookingStatus;

import java.util.Random;
import java.util.UUID;

public class BlockMother {

    public static Block random() {
        return Block.builder()
                .id(UUID.randomUUID().toString())
                .version(new Random().nextLong())
                .propertyId(UUID.randomUUID().toString())
                .period(PeriodMother.random())
                .status(BookingStatus.values()[new Random().nextInt(BookingStatus.values().length)])
                .reason(UUID.randomUUID().toString())
                .build();
    }
}
