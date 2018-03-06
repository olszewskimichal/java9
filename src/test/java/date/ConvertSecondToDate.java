package date;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ConvertSecondToDate {

  private LocalTime convertSecondToHHMMSSString(int nSecondTime) {
    return LocalTime.MIN.plusSeconds(nSecondTime);
  }

  @Test
  void shouldConvertSecondToDate(){
    LocalTime localTime = convertSecondToHHMMSSString(134);
    assertThat(localTime.getSecond()).isEqualTo(14);
    assertThat(localTime.getMinute()).isEqualTo(2);
  }

}
