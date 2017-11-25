package others;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class TryWithResourcesTest {

  @Test
  void shouldCloseReader() throws IOException {
    BufferedReader reader1 = new BufferedReader(new FileReader(".gitignore"));
    try (reader1) {
      System.out.println(reader1.readLine());
    }
    IOException ioException = assertThrows(IOException.class, reader1::readLine);
    assertThat(ioException.getMessage()).contains("Stream closed");
  }

}
