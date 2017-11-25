package process;

import java.lang.Runtime.Version;
import org.junit.jupiter.api.Test;

class VersionTest {

  @Test
  void shouldReturnInfoAboutVersion(){
    Version version = Runtime.version();
    System.out.println(version);
    System.out.println(version.build());
    System.out.println(version.major());
    System.out.println(version.minor());
    System.out.println(version.security());
    System.out.println(version.pre());
  }

}
