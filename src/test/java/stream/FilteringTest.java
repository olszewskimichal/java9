package stream;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class FilteringTest {

  private static boolean test(BigDecimal v) {
    return v.compareTo(BigDecimal.ZERO) > 0;
  }

  @Test
  void shouldFilterCollectionsWithCollectorsFiltering() {
    Stream<BigDecimal> stream = Stream.of(BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(5), BigDecimal.valueOf(-5));
    List<BigDecimal> collect = stream.collect(Collectors.filtering(FilteringTest::test, Collectors.toList()));
    assertThat(collect).isNotNull().hasSize(3).contains(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(5));
  }

}
