package optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class IfPresentOrElseTest {

  @Test
  void givenOptional_whenPresent_thenShouldExecuteProperCallback() {
    //given
    Optional<String> value = Optional.of("test");
    AtomicInteger successCounter = new AtomicInteger(0);
    AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);
    //when
    value.ifPresentOrElse(
        v -> successCounter.incrementAndGet(),
        onEmptyOptionalCounter::incrementAndGet);
    // then
    assertThat(successCounter.get()).isEqualTo(1);
    assertThat(onEmptyOptionalCounter.get()).isEqualTo(0);
  }

  @Test
  void givenOptional_whenNotPresent_thenShouldExecuteProperCallback() {
    // given
    Optional<String> value = Optional.empty();
    AtomicInteger successCounter = new AtomicInteger(0);
    AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

    // when
    value.ifPresentOrElse(
        v -> successCounter.incrementAndGet(),
        onEmptyOptionalCounter::incrementAndGet);

    // then
    assertThat(successCounter.get()).isEqualTo(0);
    assertThat(onEmptyOptionalCounter.get()).isEqualTo(1);
  }
}