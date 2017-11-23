package optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class OrOptionalTest {

  @Test
  void ifOptionalPresentThenReturnResult() {
    //given
    String expected = "properValue";
    Optional<String> value = Optional.of(expected);
    Optional<String> defaultValue = Optional.of("default");
    //when
    Optional<String> stringOptional = value.or(() -> defaultValue);
    //then
    assertThat(stringOptional.get()).isEqualTo(expected);
  }

  @Test
  void ifOptionalNotPresentThenReturnDefaultValue() {
    // given
    String defaultString = "default";
    Optional<String> value = Optional.empty();
    Optional<String> defaultValue = Optional.of(defaultString);

    // when
    Optional<String> result = value.or(() -> defaultValue);

    // then
    assertThat(result.get()).isEqualTo(defaultString);
  }
}
