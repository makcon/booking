package test.makcon.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import test.makcon.domain.command.block.handler.CreateBlockCommandHandler;
import test.makcon.domain.command.block.handler.DeleteBlockCommandHandler;
import test.makcon.domain.command.block.handler.UpdateBlockCommandHandler;
import test.makcon.domain.model.Block;
import test.makcon.dto.BlockV1;
import test.makcon.dto.request.CreateBlockRequestParamsV1;
import test.makcon.dto.request.UpdateBlockRequestParamsV1;
import test.makcon.mapper.BlockDtoMapper;

@RestController
@RequestMapping("/v1/blocks")
@RequiredArgsConstructor
public class BlockControllerV1 {

    private final BlockDtoMapper mapper;
    private final CreateBlockCommandHandler createHandler;
    private final UpdateBlockCommandHandler updateHandler;
    private final DeleteBlockCommandHandler deleteHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockV1 create(@RequestBody CreateBlockRequestParamsV1 params) {
        Block block = createHandler.handle(mapper.toCommand(params));
        return mapper.toDto(block);
    }

    @PutMapping("/{id}")
    public BlockV1 update(@PathVariable String id, @RequestBody UpdateBlockRequestParamsV1 params) {
        Block block = updateHandler.handle(mapper.toCommand(id, params));
        return mapper.toDto(block);
    }

    @DeleteMapping("/{id}")
    public BlockV1 delete(@PathVariable String id) {
        Block block = deleteHandler.handle(id);
        return mapper.toDto(block);
    }
}
