package stackwalker;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class StackWalkingTest {

  @Test
  void shouldShowStackFrames() {
    List<StackFrame> stack = StackWalker.getInstance().walk(s -> s.limit(10)
        .peek(System.out::println)
        .collect(Collectors.toList()));
    assertThat(stack).isNotEmpty();
  }

  @Test
  void giveStalkWalker_whenWalkingTheStack_thenShowStackFrames() {
    new StackWalkerDemo().methodOne();
  }

  @Test
  void giveStalkWalker_whenInvokingFindCaller_thenFindCallingClass() {
    new StackWalkerDemo().findCaller();
  }

}
