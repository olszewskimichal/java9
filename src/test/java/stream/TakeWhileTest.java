package stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TakeWhileTest {

  @Test
  void shouldReturnListWith3Values() {
    List<String> stringList = Stream.of("a", "b", "c", "", "e")
        .takeWhile(v -> !v.isEmpty())
        .collect(Collectors.toList());

    assertThat(stringList).isNotEmpty().hasSize(3);
  }

  @Test
  @Disabled
  void shouldProcessFileWhilePredicate() throws IOException {
    Files.lines(Paths.get("file.txt"))
        .takeWhile(s -> !s.equals("END"))
        .forEach(System.out::println);
  }

  @Test
  void shouldCreateStringsWithMaxLengthEquals3() {
    List<String> collect = Stream.iterate("", s -> s + "s")
        .takeWhile(s -> s.length() <= 3)
        .collect(Collectors.toList());

    long count = collect.stream().filter(v -> v.length() > 3).count();
    assertThat(count).isEqualTo(0);
  }

}
