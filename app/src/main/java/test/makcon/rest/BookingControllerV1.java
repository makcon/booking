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
import test.makcon.domain.command.booking.handler.CancelBookingCommandHandler;
import test.makcon.domain.command.booking.handler.CreateBookingCommandHandler;
import test.makcon.domain.command.booking.handler.DeleteBookingCommandHandler;
import test.makcon.domain.command.booking.handler.RebookBookingCommandHandler;
import test.makcon.domain.command.booking.handler.UpdateBookingCommandHandler;
import test.makcon.domain.model.Booking;
import test.makcon.dto.BookingV1;
import test.makcon.dto.request.CreateBookingRequestParamsV1;
import test.makcon.dto.request.RebookBookingRequestParamsV1;
import test.makcon.dto.request.UpdateBookingRequestParamsV1;
import test.makcon.mapper.BookingDtoMapper;

@RestController
@RequestMapping("/v1/booking")
@RequiredArgsConstructor
public class BookingControllerV1 {

    private final BookingDtoMapper mapper;
    private final CreateBookingCommandHandler createHandler;
    private final UpdateBookingCommandHandler updateHandler;
    private final DeleteBookingCommandHandler deleteHandler;
    private final CancelBookingCommandHandler cancelHandler;
    private final RebookBookingCommandHandler rebookHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingV1 create(@RequestBody CreateBookingRequestParamsV1 params) {
        Booking booking = createHandler.handle(mapper.toCommand(params));
        return mapper.toDto(booking);
    }

    @PutMapping("/{id}")
    public BookingV1 update(@PathVariable String id, @RequestBody UpdateBookingRequestParamsV1 params) {
        Booking booking = updateHandler.handle(mapper.toCommand(id, params));
        return mapper.toDto(booking);
    }

    @DeleteMapping("/{id}")
    public BookingV1 delete(@PathVariable String id) {
        Booking booking = deleteHandler.handle(id);
        return mapper.toDto(booking);
    }

    @PutMapping("/{id}/canceling")
    public BookingV1 cancel(@PathVariable String id) {
        Booking booking = cancelHandler.handle(id);
        return mapper.toDto(booking);
    }

    @PostMapping("/{id}/rebooking")
    public BookingV1 rebook(@PathVariable String id, @RequestBody RebookBookingRequestParamsV1 params) {
        Booking booking = rebookHandler.handle(mapper.toCommand(id, params));
        return mapper.toDto(booking);
    }
}
