package test.makcon.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.command.block.CreateBlockCommand;
import test.makcon.domain.command.block.UpdateBlockCommand;
import test.makcon.domain.model.Block;
import test.makcon.dto.BlockV1;
import test.makcon.dto.request.CreateBlockRequestParamsV1;
import test.makcon.dto.request.UpdateBlockRequestParamsV1;

@Component
@RequiredArgsConstructor
public class BlockDtoMapper {

    private final PeriodMapper periodMapper;

    public CreateBlockCommand toCommand(CreateBlockRequestParamsV1 params) {
        return CreateBlockCommand.builder()
                .propertyId(params.getPropertyId())
                .period(periodMapper.toModel(params.getPeriod()))
                .reason(params.getReason())
                .build();
    }

    public UpdateBlockCommand toCommand(String blockId, UpdateBlockRequestParamsV1 params) {
        return UpdateBlockCommand.builder()
                .blockId(blockId)
                .version(params.getVersion())
                .period(periodMapper.toModel(params.getPeriod()))
                .reason(params.getReason())
                .build();
    }

    public BlockV1 toDto(Block model) {
        return BlockV1.builder()
                .id(model.getId())
                .version(model.getVersion())
                .propertyId(model.getPropertyId())
                .period(periodMapper.toDto(model.getPeriod()))
                .status(model.getStatus().name())
                .reason(model.getReason())
                .build();
    }
}
