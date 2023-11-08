package test.makcon.domain.mother;

import test.makcon.domain.model.DateRange;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PeriodMother {

    public static DateRange random() {
        LocalDate start = randomDate();
        return DateRange.builder()
                .start(start)
                .end(start.plusDays(new Random().nextInt(60)))
                .build();
    }

    public static DateRange ofMonthDays(int start, int end) {
        return DateRange.builder()
                .start(LocalDate.of(2023, 12, start))
                .end(LocalDate.of(2023, 12, end))
                .build();
    }

    private static LocalDate randomDate() {
        int hundredYears = 100 * 365;
        return LocalDate.ofEpochDay(
                ThreadLocalRandom
                        .current()
                        .nextInt(-hundredYears, hundredYears)
        );
    }
}
