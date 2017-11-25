package stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OfNullableTest {

  @Test
  void shouldReturnCountOfNullable() {
    assertThat(Stream.ofNullable("42").count()).isEqualTo(1);
    assertThat(Stream.ofNullable(null).count()).isEqualTo(0);
  }

  @Test
  void shouldReturnListOfNotNullObjectsFromMap() {
    Map<String, Integer> map = new HashMap<>();
    map.put("String", 1);
    map.put("StringSecond", null);

    List<Integer> collection = Stream.of("String", "StringSecond")
        .flatMap(element -> Stream.ofNullable(map.get(element)))
        .collect(toList());

    assertThat(collection).isNotEmpty().hasSize(1).contains(1);
  }

}
