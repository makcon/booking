package test.makcon.adapters.mapper;

import org.springframework.stereotype.Component;
import test.makcon.adapters.repository.doc.BookingDoc;
import test.makcon.domain.model.Booking;

@Component
public class BookingDocMapper {

    public BookingDoc toDoc(Booking model) {
        return BookingDoc.builder()
                .id(model.getId())
                .version(model.getVersion())
                .propertyId(model.getPropertyId())
                .period(model.getPeriod())
                .status(model.getStatus())
                .guests(model.getGuests())
                .build();
    }

    public Booking toModel(BookingDoc doc) {
        return Booking.builder()
                .id(doc.getId())
                .version(doc.getVersion())
                .propertyId(doc.getPropertyId())
                .period(doc.getPeriod())
                .status(doc.getStatus())
                .guests(doc.getGuests())
                .build();
    }
}
