package collectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class ListFactory {

  @Test
  void shouldCreateNotEmptyList() {
    List<Integer> integers = List.of(1, 2, 3);

    assertThat(integers).isNotEmpty().hasSize(3);
  }

  @Test
  void shouldCreateEmptyList() {
    List<Integer> integers = List.of();

    assertThat(integers).isEmpty();
  }

  @Test
  void shouldThrowExceptionOnChange() {
    List<Integer> integers = List.of(1, 2, 3);

    assertThrows(UnsupportedOperationException.class, () -> integers.add(4));
  }
}
