package test.makcon.rest.mother;

import test.makcon.dto.DateRangeV1;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PeriodV1Mother {

    public static DateRangeV1 random() {
        LocalDate start = randomDate();
        return DateRangeV1.builder()
                .start(start)
                .end(start.plusDays(new Random().nextInt(60)))
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
