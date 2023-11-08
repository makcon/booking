package test.makcon.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import test.makcon.adapters.repository.BookingRepository;
import test.makcon.dto.BookingV1;
import test.makcon.dto.ErrorV1;
import test.makcon.dto.constant.ErrorCode;
import test.makcon.dto.request.CreateBookingRequestParamsV1;
import test.makcon.dto.request.UpdateBookingRequestParamsV1;
import test.makcon.rest.mother.CreateBookingRequestParamsV1Mother;
import test.makcon.rest.mother.UpdateBookingRequestParamsV1Mother;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookingControllerV1Should {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
    }

    @Test
    void successfully_create_booking() throws Exception {
        // given
        CreateBookingRequestParamsV1 givenParams = CreateBookingRequestParamsV1Mother.random();

        // when
        ResultActions actions = mvc.perform(buildCreateRequest(givenParams));

        // then
        String content = actions
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BookingV1 booking = objectMapper.readValue(content, BookingV1.class);
        assertNotNull(booking.getId());
    }

    @Test
    void return_error_trying_to_create_when_period_is_overlapped() throws Exception {
        // given
        var givenParams = CreateBookingRequestParamsV1Mother.random();
        mvc.perform(buildCreateRequest(givenParams));

        // when
        ResultActions actions = mvc.perform(buildCreateRequest(givenParams));

        // then
        String content = actions
                .andExpect(status().isConflict())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorV1 error = objectMapper.readValue(content, ErrorV1.class);
        assertEquals(ErrorCode.PERIOD_OVERLAP, error.getCode());
    }

    @Test
    void successfully_update_booking() throws Exception {
        // given
        BookingV1 storedBooking = createBooking();
        UpdateBookingRequestParamsV1 givenParams = UpdateBookingRequestParamsV1Mother.random(storedBooking.getVersion());

        // when
        ResultActions actions = mvc.perform(buildUpdateRequest(storedBooking.getId(), givenParams));

        // then
        String content = actions
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BookingV1 booking = objectMapper.readValue(content, BookingV1.class);
        assertNotEquals(storedBooking, booking);
    }

    @Test
    void return_error_trying_to_update_when_model_not_exists() throws Exception {
        // given
        UpdateBookingRequestParamsV1 givenParams = UpdateBookingRequestParamsV1Mother.random(0L);

        // when
        ResultActions actions = mvc.perform(buildUpdateRequest(UUID.randomUUID().toString(), givenParams));

        // then
        String content = actions
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorV1 error = objectMapper.readValue(content, ErrorV1.class);
        assertEquals(ErrorCode.MODEL_NOT_EXISTS, error.getCode());
    }

    @Test
    void return_error_trying_to_update_when_version_is_not_correct() throws Exception {
        // given
        BookingV1 storedBooking = createBooking();
        UpdateBookingRequestParamsV1 givenParams = UpdateBookingRequestParamsV1Mother.random(1L);

        // when
        ResultActions actions = mvc.perform(buildUpdateRequest(storedBooking.getId(), givenParams));

        // then
        String content = actions
                .andExpect(status().isConflict())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorV1 error = objectMapper.readValue(content, ErrorV1.class);
        assertEquals(ErrorCode.VERSION_OUTDATED, error.getCode());
    }

    // TODO test other methods

    private MockHttpServletRequestBuilder buildCreateRequest(CreateBookingRequestParamsV1 params) throws Exception {
        return post("/v1/booking")
                .content(objectMapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder buildUpdateRequest(
            String bookingId,
            UpdateBookingRequestParamsV1 params
    ) throws Exception {
        return put("/v1/booking/" + bookingId)
                .content(objectMapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON);
    }

    private BookingV1 createBooking() throws Exception {
        var content = mvc
                .perform(buildCreateRequest(CreateBookingRequestParamsV1Mother.random()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, BookingV1.class);
    }
}