package stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class IterateTest {

  @Test
  void shouldCreateListOfOddNumbers() {
    List<Integer> expected = List.of(0, 2, 4, 6, 8, 10);

    List<Integer> collect = Stream.iterate(0, i -> i <= 10, i -> i + 2).collect(Collectors.toList());

    assertThat(collect).isEqualTo(expected);
  }

  @Test
  void shouldReturnCollectionsOfDateFromTo(){
    LocalDate to = LocalDate.now();
    LocalDate from = LocalDate.now().minusDays(7);

    Set<LocalDate> collect = Stream.iterate(from, date -> date.isBefore(to), date -> date.plusDays(1))
        .peek(System.out::println)
        .collect(Collectors.toSet());

    assertThat(collect).isNotEmpty().hasSize(7);
  }

}
