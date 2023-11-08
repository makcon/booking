package test.makcon.domain.command.block.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.command.block.CreateBlockCommand;
import test.makcon.domain.model.Block;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BlockRepositoryPort;
import test.makcon.domain.validator.PropertyAvailabilityValidator;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateBlockCommandHandler {

    private final PropertyAvailabilityValidator availabilityValidator;
    private final BlockRepositoryPort repository;

    public Block handle(CreateBlockCommand command) {
        availabilityValidator.validate(command.getPeriod(), command.getPropertyId());

        Block block = Block.builder()
                .id(UUID.randomUUID().toString())
                .propertyId(command.getPropertyId())
                .period(command.getPeriod())
                .status(BookingStatus.PENDING)
                .reason(command.getReason())
                .build();

        return repository.create(block);
    }
}
