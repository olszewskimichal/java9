package stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DropWhileTest {

  @Test
  void shouldCreateStringsWithLengthGreaterThen3() {
    List<String> collect = Stream.iterate("", s -> s.length() <= 5, s -> s + "s")
        .dropWhile(s -> s.length() <= 3)
        .collect(Collectors.toList());

    assertThat(collect).isNotEmpty().hasSize(2);
    assertThat(collect.stream().filter(v -> v.length() <= 3).count()).isEqualTo(0);
  }

}
