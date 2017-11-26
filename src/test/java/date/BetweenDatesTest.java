package date;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class BetweenDatesTest {

  @Test
  void shouldReturnDatesFromDateToDateInJava8() {
    LocalDate from = LocalDate.now();
    LocalDate to = from.plusDays(7);

    long numOfDaysBetween = ChronoUnit.DAYS.between(from, to);
    List<LocalDate> collect = IntStream.iterate(0, i -> i + 1)
        .limit(numOfDaysBetween)
        .mapToObj(from::plusDays)
        .collect(Collectors.toList());
    assertThat(numOfDaysBetween).isEqualTo(7);
    assertThat(collect).isNotNull().isNotEmpty().hasSize(7);
  }

  @Test
  void shouldReturnDatesFromDateToDateInJava9() {
    LocalDate from = LocalDate.now();
    LocalDate to = from.plusDays(7);
    List<LocalDate> collect = from.datesUntil(to).collect(Collectors.toList());
    assertThat(collect).isNotNull().isNotEmpty().hasSize(7);
  }

}
