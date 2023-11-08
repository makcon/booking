package test.makcon.domain.mother;

import test.makcon.domain.model.Guest;

import java.util.UUID;

public class GuestMother {

    public static Guest random() {
        return Guest.builder()
                .firstName(UUID.randomUUID().toString())
                .lastName(UUID.randomUUID().toString())
                .build();
    }
}
