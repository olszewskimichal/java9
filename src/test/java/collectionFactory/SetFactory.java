package collectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import org.junit.jupiter.api.Test;

class SetFactory {

  @Test
  void shouldCreateNotEmptySet() {
    Set<Integer> integers = Set.of(1, 2, 3);

    assertThat(integers).isNotEmpty().hasSize(3);
  }

  @Test
  void shouldCreateEmptySet() {
    Set<Integer> integers = Set.of();

    assertThat(integers).isEmpty();
  }

  @Test
  void shouldThrowExceptionOnChange() {
    Set<Integer> integers = Set.of(1, 2, 3);

    assertThrows(UnsupportedOperationException.class, () -> integers.add(4));
  }
}
