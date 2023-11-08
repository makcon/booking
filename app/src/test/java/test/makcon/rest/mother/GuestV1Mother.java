package test.makcon.rest.mother;

import test.makcon.dto.GuestV1;

import java.util.UUID;

public class GuestV1Mother {

    public static GuestV1 random() {
        return GuestV1.builder()
                .firstName(UUID.randomUUID().toString())
                .lastName(UUID.randomUUID().toString())
                .build();
    }
}
