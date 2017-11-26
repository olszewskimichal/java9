package objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;
import org.junit.jupiter.api.Test;

/**
 * This method is used for checking if the index is within the given length.
 * It returns the index if 0 <= index < length. Otherwise, it throws an IndexOutOfBoundsException
 */
class CheckIndexTest {

  @Test
  void givenNumber_whenInvokeCheckIndex_thenNumber() {
    int length = 5;

    assertThat(Objects.checkIndex(4, length)).isEqualTo(4);
  }

  @Test
  void givenOutOfRangeNumber_whenInvokeCheckIndex_thenException() {
    int length = 5;
    assertThrows(IndexOutOfBoundsException.class, () -> Objects.checkIndex(5, length));
  }
}
