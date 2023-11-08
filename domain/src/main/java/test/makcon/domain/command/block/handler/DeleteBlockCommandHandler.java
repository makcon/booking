package test.makcon.domain.command.block.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.model.Block;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BlockRepositoryPort;

@Component
@RequiredArgsConstructor
public class DeleteBlockCommandHandler {

    private final BlockRepositoryPort repository;

    public Block handle(String blockId) {
        Block storedBlock = repository.findByIdRequired(blockId);

        Block block = Block.builder()
                .id(storedBlock.getId())
                .propertyId(storedBlock.getPropertyId())
                .status(BookingStatus.DELETED)
                .version(storedBlock.getVersion())
                .period(storedBlock.getPeriod())
                .reason(storedBlock.getReason())
                .build();

        return repository.update(block);
    }
}
