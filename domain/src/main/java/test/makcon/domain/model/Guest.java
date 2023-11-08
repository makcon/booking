package test.makcon.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Guest {
    private final String firstName;
    private final String lastName;
}
