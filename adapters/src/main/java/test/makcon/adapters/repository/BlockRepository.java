package test.makcon.adapters.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.makcon.adapters.repository.doc.BlockDoc;
import test.makcon.domain.model.BookingStatus;

import java.util.List;

public interface BlockRepository extends MongoRepository<BlockDoc, String> {

    List<BlockDoc> findByPropertyIdAndStatus(String propertyId, BookingStatus status);
}
