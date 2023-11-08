package test.makcon.mapper;

import org.springframework.stereotype.Component;
import test.makcon.domain.model.Guest;
import test.makcon.dto.GuestV1;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GuestMapper {

    public List<GuestV1> toDto(List<Guest> dtos) {
        return dtos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public GuestV1 toDto(Guest model) {
        return GuestV1.builder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .build();
    }

    public List<Guest> toModel(List<GuestV1> dtos) {
        return dtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Guest toModel(GuestV1 dto) {
        return Guest.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
