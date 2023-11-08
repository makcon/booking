package test.makcon.domain.command.block.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.command.block.UpdateBlockCommand;
import test.makcon.domain.model.Block;
import test.makcon.domain.port.BlockRepositoryPort;
import test.makcon.domain.validator.PropertyAvailabilityValidator;

@Component
@RequiredArgsConstructor
public class UpdateBlockCommandHandler {

    private final PropertyAvailabilityValidator availabilityValidator;
    private final BlockRepositoryPort repository;

    public Block handle(UpdateBlockCommand command) {
        Block storedBlock = repository.findByIdRequired(command.getBlockId());
        // TODO prevent updating it in illegal statuses like CANCELED, ACTIVE

        if (!command.getPeriod().equals(storedBlock.getPeriod())) {
            availabilityValidator.validate(command.getPeriod(), storedBlock.getPropertyId());
        }

        Block block = Block.builder()
                .id(storedBlock.getId())
                .propertyId(storedBlock.getPropertyId())
                .status(storedBlock.getStatus())
                .version(command.getVersion())
                .period(command.getPeriod())
                .reason(command.getReason())
                .build();

        return repository.update(block);
    }
}
