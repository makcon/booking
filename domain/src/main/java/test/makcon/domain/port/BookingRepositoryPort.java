package test.makcon.domain.port;

import test.makcon.domain.exception.ModelNotExistsException;
import test.makcon.domain.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepositoryPort {

    Booking create(Booking booking);

    Booking update(Booking booking);

    List<Booking> findPending(String propertyId);

    Optional<Booking> findById(String id);

    default Booking findByIdRequired(String id) {
        return findById(id)
                .orElseThrow(() -> new ModelNotExistsException("Booking"));
    }
}
