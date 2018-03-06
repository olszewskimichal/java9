package stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class FlatMappingTest {

  @Test
  void shouldFlatMapCollectionsWithCollectorsFlatMapping() {
    Stream<List<BigDecimal>> stream = Stream
        .of(List.of(BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.ONE), List.of(BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.ONE), List.of(BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.ONE));
    List<BigDecimal> collect = stream.collect(Collectors.flatMapping(Collection::stream, Collectors.toList()));
    assertThat(collect).isNotNull().hasSize(9);
  }

}
