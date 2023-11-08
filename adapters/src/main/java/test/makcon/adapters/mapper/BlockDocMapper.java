package test.makcon.adapters.mapper;

import org.springframework.stereotype.Component;
import test.makcon.adapters.repository.doc.BlockDoc;
import test.makcon.domain.model.Block;

@Component
public class BlockDocMapper {

    public BlockDoc toDoc(Block model) {
        return BlockDoc.builder()
                .id(model.getId())
                .version(model.getVersion())
                .propertyId(model.getPropertyId())
                .period(model.getPeriod())
                .status(model.getStatus())
                .reason(model.getReason())
                .build();
    }

    public Block toModel(BlockDoc doc) {
        return Block.builder()
                .id(doc.getId())
                .version(doc.getVersion())
                .propertyId(doc.getPropertyId())
                .period(doc.getPeriod())
                .status(doc.getStatus())
                .reason(doc.getReason())
                .build();
    }
}
