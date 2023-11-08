package test.makcon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestV1 {
    private final String firstName;
    private final String lastName;
}
