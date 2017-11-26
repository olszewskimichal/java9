package objects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class RequireNonNullElseGetTest {

  /**
   * This method is similar to requireNonNullElse, except that the second parameter, is a java.util.function.Supplier interface which allows a lazy instantiation of the provided collection
   */
  @Test
  void givenObject_whenRequireNonNullElseGet_thenObject() {
    List<String> aList = Objects.requireNonNullElseGet(null, List::of);
    assertThat(aList).isNotEmpty();
  }

}
