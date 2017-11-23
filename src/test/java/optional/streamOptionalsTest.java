package optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class streamOptionalsTest {

  @Test
  void shouldReturnNotEmptyList() {
    List<Optional<String>> listOfOptionals = Arrays.asList(Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));
    List<String> collect = listOfOptionals.stream()
        .flatMap(Optional::stream)
        .map(String::toUpperCase)
        .collect(Collectors.toList());
    assertThat(collect).isNotEmpty().hasSize(2).contains("FOO", "BAR");
  }

  @Test
  void shouldReturnEmptyList() {
    // given
    Optional<String> value = Optional.empty();

    // when
    List<String> collect = value.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());

    // then
    assertThat(collect).isEmpty();
  }
}
