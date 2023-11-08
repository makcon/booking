package test.makcon.domain.port;

import test.makcon.domain.exception.ModelNotExistsException;
import test.makcon.domain.model.Block;

import java.util.List;
import java.util.Optional;

public interface BlockRepositoryPort {

    Block create(Block booking);

    Block update(Block booking);

    List<Block> findPending(String propertyId);

    Optional<Block> findById(String id);

    default Block findByIdRequired(String id) {
        return findById(id)
                .orElseThrow(() -> new ModelNotExistsException("Block"));
    }
}
