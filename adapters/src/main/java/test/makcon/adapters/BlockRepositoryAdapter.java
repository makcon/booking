package test.makcon.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import test.makcon.adapters.mapper.BlockDocMapper;
import test.makcon.adapters.repository.BlockRepository;
import test.makcon.adapters.repository.doc.BlockDoc;
import test.makcon.domain.exception.VersionOutdatedException;
import test.makcon.domain.model.Block;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BlockRepositoryPort;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BlockRepositoryAdapter implements BlockRepositoryPort {

    private final BlockRepository repository;
    private final BlockDocMapper mapper;

    @Override
    public Block create(Block booking) {
        BlockDoc doc = repository.insert(mapper.toDoc(booking));
        return mapper.toModel(doc);
    }

    @Override
    public Block update(Block booking) {
        try {
            BlockDoc doc = repository.save(mapper.toDoc(booking));
            return mapper.toModel(doc);
        } catch (OptimisticLockingFailureException e) {
            throw new VersionOutdatedException("Block", findByIdRequired(booking.getId()).getVersion());
        }
    }

    @Override
    public List<Block> findPending(String propertyId) {
        return repository.findByPropertyIdAndStatus(propertyId, BookingStatus.PENDING)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<Block> findById(String id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }
}
