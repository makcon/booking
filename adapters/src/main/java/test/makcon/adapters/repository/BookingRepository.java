package test.makcon.adapters.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.makcon.adapters.repository.doc.BookingDoc;
import test.makcon.domain.model.BookingStatus;

import java.util.List;

public interface BookingRepository extends MongoRepository<BookingDoc, String> {

    List<BookingDoc> findByPropertyIdAndStatus(String propertyId, BookingStatus status);
}
