package process;

import java.io.IOException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KillAllNotepadProcess {

  @BeforeEach
  void setUp() {
    IntStream.rangeClosed(1, 5).parallel().forEach(v -> {
      try {
        new ProcessBuilder("notepad.exe").start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  void shouldKillALlProcess() {
    ProcessHandle.allProcesses()
        .filter(v -> v.info().command().isPresent())
        .filter(v -> v.info().command().get().contains("notepad.exe"))
        .forEach(this::closeAndLog);
  }

  void closeAndLog(ProcessHandle process) {
    String status = process.destroyForcibly() ? " Success!" : " Failed";   //ProcessHandle has two different ways of killing processes - destroy() and destroyForcibly(). These are roughly equivalent to the difference between sending a SIGTERM and SIGKILL on a unix system. destroy() will request the process exit, destroyForcibly() will force it to terminate even if it want’s to stay around. We use the latter here as it deals with cases where our application process has hung more effectively.
    System.out.println("Killing ... " + process.info().command() + status);
  }
}
