package stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TransferToTest {

  @Test
  void testTransferToJava9Syntax() throws IOException {
    byte[] inBytes = "Hello Java 9".getBytes();
    ByteArrayInputStream bis = new ByteArrayInputStream(inBytes);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    try (bis; bos) {

      long count = bis.transferTo(bos);

      byte[] outBytes = bos.toByteArray();
      assertTrue(Arrays.equals(inBytes, outBytes));
      assertTrue(count > 0);
    }
  }

}
