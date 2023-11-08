package test.makcon.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import test.makcon.adapters.mapper.BookingDocMapper;
import test.makcon.adapters.repository.BookingRepository;
import test.makcon.adapters.repository.doc.BookingDoc;
import test.makcon.domain.exception.VersionOutdatedException;
import test.makcon.domain.model.Booking;
import test.makcon.domain.model.BookingStatus;
import test.makcon.domain.port.BookingRepositoryPort;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryAdapter implements BookingRepositoryPort {

    private final BookingRepository repository;
    private final BookingDocMapper mapper;

    @Override
    public Booking create(Booking booking) {
        BookingDoc doc = repository.insert(mapper.toDoc(booking));
        return mapper.toModel(doc);
    }

    @Override
    public Booking update(Booking booking) {
        try {
            BookingDoc doc = repository.save(mapper.toDoc(booking));
            return mapper.toModel(doc);
        } catch (OptimisticLockingFailureException e) {
            throw new VersionOutdatedException("Booking", findByIdRequired(booking.getId()).getVersion());
        }
    }

    @Override
    public List<Booking> findPending(String propertyId) {
        return repository.findByPropertyIdAndStatus(propertyId, BookingStatus.PENDING)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<Booking> findById(String id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }
}
