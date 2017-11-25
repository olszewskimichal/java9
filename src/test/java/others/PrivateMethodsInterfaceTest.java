package others;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PrivateMethodsInterfaceTest {

  @Test
  void shouldReturnSomethingFromPrivateMethod() {
    InterfaceWithPrivateMethods classWithInterface = new ClassWithInterface();
    assertThat(classWithInterface.getString()).isNotNull().isNotEmpty();
  }
}