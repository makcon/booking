package test.makcon.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import test.makcon.domain.command.booking.CreateBookingCommand;
import test.makcon.domain.command.booking.RebookBookingCommand;
import test.makcon.domain.command.booking.UpdateBookingCommand;
import test.makcon.domain.model.Booking;
import test.makcon.dto.BookingV1;
import test.makcon.dto.request.CreateBookingRequestParamsV1;
import test.makcon.dto.request.RebookBookingRequestParamsV1;
import test.makcon.dto.request.UpdateBookingRequestParamsV1;

@Component
@RequiredArgsConstructor
public class BookingDtoMapper {

    private final PeriodMapper periodMapper;
    private final GuestMapper guestMapper;

    public CreateBookingCommand toCommand(CreateBookingRequestParamsV1 params) {
        return CreateBookingCommand.builder()
                .propertyId(params.getPropertyId())
                .period(periodMapper.toModel(params.getPeriod()))
                .guests(guestMapper.toModel(params.getGuests()))
                .build();
    }

    public UpdateBookingCommand toCommand(String bookingId, UpdateBookingRequestParamsV1 params) {
        return UpdateBookingCommand.builder()
                .bookingId(bookingId)
                .version(params.getVersion())
                .period(periodMapper.toModel(params.getPeriod()))
                .guests(guestMapper.toModel(params.getGuests()))
                .build();
    }

    public RebookBookingCommand toCommand(String bookingId, RebookBookingRequestParamsV1 params) {
        return RebookBookingCommand.builder()
                .bookingId(bookingId)
                .period(periodMapper.toModel(params.getPeriod()))
                .build();
    }

    public BookingV1 toDto(Booking model) {
        return BookingV1.builder()
                .id(model.getId())
                .version(model.getVersion())
                .propertyId(model.getPropertyId())
                .period(periodMapper.toDto(model.getPeriod()))
                .status(model.getStatus().name())
                .guests(guestMapper.toDto(model.getGuests()))
                .build();
    }
}
