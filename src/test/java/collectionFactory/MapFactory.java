package collectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class MapFactory {

  @Test
  void shouldCreateNotEmptyMap() {
    Map<String, String> stringMap = Map.of("hello", "world");

    assertThat(stringMap).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateEmptyMap() {
    Map<Object, Object> map = Map.of();

    assertThat(map).isEmpty();
  }

  @Test
  void shouldThrowExceptionOnChange() {
    Map<String, String> stringMap = Map.of("hello", "world");

    assertThrows(UnsupportedOperationException.class, () -> stringMap.put("1", "2"));
  }
}
