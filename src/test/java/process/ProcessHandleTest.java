package process;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.ProcessHandle.Info;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import org.junit.jupiter.api.Test;

public class ProcessHandleTest {

  @Test
  void shouldReturnInfoAboutProcess() {
    ProcessHandle handle = ProcessHandle.current();
    System.out.println(handle);
    Info info = handle.info();
    System.out.println(info);

    assertAll(
        () -> assertThat(handle.pid()).isNotNull(),
        () -> assertEquals(false, info.arguments().isPresent()),
        () -> assertEquals(true, info.command().isPresent()),
        () -> assertTrue(info.command().get().contains("java")),
        () -> assertEquals(true, info.startInstant().isPresent()),
        () -> assertEquals(true, info.totalCpuDuration().isPresent()),
        () -> assertEquals(true, info.user().isPresent())
    );
  }

  @Test
  void shouldShowLivingProcesses() {
    ProcessHandle.allProcesses()
        .filter(ProcessHandle::isAlive)
        .forEach(v -> System.out.println(v.pid() + " " + v.info().command()));

  }

  @Test
  void showMoreInfoAboutAllProcesses() {
    ProcessHandle.allProcesses().filter(p -> p.info().command().isPresent())
        .forEach((process) -> {
          System.out.println("Process id : " + process.pid());
          ProcessHandle.Info info = process.info();
          logInfoAboutProcess(info);
        });
  }

  private void logInfoAboutProcess(Info info) {
    System.out.println("Command: " + info.command().orElse(""));
    String[] arguments = info.arguments().orElse(new String[]{});
    System.out.println("Arguments:");
    for (String arg : arguments) {
      System.out.println(" arguement :" + arg);
    }
    System.out.println("Command line: " + info.commandLine().orElse(""));
    System.out.println("Start time: " + info.startInstant().orElse(Instant.now()).toString());
    System.out.println("Run time duration: " + info.totalCpuDuration().orElse(Duration.ofMillis(0)).toMillis());
    System.out.println("User :" + info.user().orElse(""));
    System.out.println("===================");
  }

  @Test
  void shouldFindTheLongestOutRunningProcess() {

    ProcessHandle
        .allProcesses()
        .map(ProcessHandle::info)
        .filter(v->v.totalCpuDuration().isPresent())
        .sorted((v1, v2) -> v2.totalCpuDuration().orElse(Duration.ZERO).compareTo(v1.totalCpuDuration().orElse(Duration.ZERO)))
        .limit(1)
        .forEach(this::logInfoAboutProcess);
    ProcessHandle
        .allProcesses()
        .map(ProcessHandle::info)
        .filter(v->v.startInstant().isPresent())
        .sorted(Comparator.comparing(v2 -> v2.startInstant().orElse(Instant.MIN)))
        .limit(1)
        .forEach(this::logInfoAboutProcess);
  }
}
