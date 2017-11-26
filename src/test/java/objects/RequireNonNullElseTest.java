package objects;

import static java.util.Objects.requireNonNullElse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RequireNonNullElseTest {

  private List<String> returnNullList() {
    return null;
  }

  private List<String> notNullList() {
    return List.of("item1", "item2");
  }

  @Test
  void givenNullObject_whenRequireNonNullElse_thenElse() {
    List aList = requireNonNullElse(returnNullList(), List.of());
    assertThat(aList).isEmpty();
  }

  @Test
  void givenObject_whenRequireNonNullElse_thenObject() {
    List<String> aList = requireNonNullElse(notNullList(), List.of());

    assertThat(aList).contains("item1", "item2");
  }

  @Test
  void givenNull_whenRequireNonNullElse_thenException() {
    assertThrows(NullPointerException.class, () -> requireNonNullElse(null, null));
  }

}
