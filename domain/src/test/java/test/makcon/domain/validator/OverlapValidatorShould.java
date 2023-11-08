package test.makcon.domain.validator;

import org.junit.jupiter.api.Test;
import test.makcon.domain.mother.PeriodMother;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OverlapValidatorShould {

    private final OverlapValidator validator = new OverlapValidator();

    @Test
    void pass_if_current_range_ends_the_day_before_when_booked_starts() {
        // given
        var givenCurrentRange = PeriodMother.ofMonthDays(4, 5);
        var givenBookedRange1 = PeriodMother.ofMonthDays(6, 7);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1));

        // then
        assertTrue(available);
    }

    @Test
    void pass_if_current_range_ends_the_same_day_when_booked_starts() {
        // given
        var givenCurrentRange = PeriodMother.ofMonthDays(4, 5);
        var givenBookedRange1 = PeriodMother.ofMonthDays(5, 6);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1));

        // then
        assertTrue(available);
    }

    @Test
    void pass_if_current_range_starts_the_next_day_when_booked_ends() {
        // given
        var givenBookedRange1 = PeriodMother.ofMonthDays(5, 6);
        var givenCurrentRange = PeriodMother.ofMonthDays(7, 8);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1));

        // then
        assertTrue(available);
    }

    @Test
    void pass_if_current_range_starts_the_same_day_when_booked_ends() {
        // given
        var givenBookedRange1 = PeriodMother.ofMonthDays(5, 6);
        var givenCurrentRange = PeriodMother.ofMonthDays(6, 7);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1));

        // then
        assertTrue(available);
    }

    @Test
    void pass_if_current_range_between_booked() {
        // given
        var givenBookedRange1 = PeriodMother.ofMonthDays(5, 6);
        var givenCurrentRange = PeriodMother.ofMonthDays(7, 8);
        var givenBookedRange2 = PeriodMother.ofMonthDays(8, 9);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1, givenBookedRange2));

        // then
        assertTrue(available);
    }

    @Test
    void not_pass_if_current_range_exactly_the_same_as_booked() {
        // given
        var givenBookedRange1 = PeriodMother.ofMonthDays(5, 6);
        var givenCurrentRange = PeriodMother.ofMonthDays(5, 6);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1));

        // then
        assertFalse(available);
    }

    @Test
    void not_pass_if_current_range_starts_when_booked_not_finished() {
        // given
        var givenBookedRange1 = PeriodMother.ofMonthDays(5, 8);
        var givenCurrentRange = PeriodMother.ofMonthDays(7, 8);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1));

        // then
        assertFalse(available);
    }

    @Test
    void not_pass_if_current_range_is_between_booked() {
        // given
        var givenBookedRange1 = PeriodMother.ofMonthDays(5, 8);
        var givenBookedRange2 = PeriodMother.ofMonthDays(9, 10);
        var givenCurrentRange = PeriodMother.ofMonthDays(7, 9);

        // when
        var available = validator.isAvailable(givenCurrentRange, List.of(givenBookedRange1, givenBookedRange2));

        // then
        assertFalse(available);
    }
}