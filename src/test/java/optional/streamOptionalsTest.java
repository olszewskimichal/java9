package optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * Another small detail: If we want to, we can now more easily move from eager operations on Optional to lazy operations on Stream.
 *
 * public List<Order> findOrdersForCustomer(String customerId) { return findCustomer(customerId) // 'List<Order> getOrders(Customer)' is expensive; // this is 'Optional::map', which is eager
 * .map(this::getOrders) .orElse(new ArrayList<>()); }
 *
 * public Stream<Order> findOrdersForCustomer(String customerId) { return findCustomer(customerId) .stream() // this is 'Stream::map', which is lazy .map(this::getOrders) .flatMap(List::stream); }
 */
public class streamOptionalsTest {

  @Test
  void shouldReturnNotEmptyList() {
    List<Optional<String>> listOfOptionals = Arrays.asList(Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));
    List<String> collect = listOfOptionals.stream()
        .flatMap(Optional::stream)
        .map(String::toUpperCase)
        .collect(Collectors.toList());
    assertThat(collect).isNotEmpty().hasSize(2).contains("FOO", "BAR");
  }

  @Test
  void shouldReturnEmptyList() {
    // given
    Optional<String> value = Optional.empty();

    // when
    List<String> collect = value.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());

    // then
    assertThat(collect).isEmpty();
  }
}
