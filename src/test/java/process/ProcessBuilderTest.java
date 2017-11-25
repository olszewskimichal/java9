package process;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

public class ProcessBuilderTest {

  @Test
  void shouldRunProcessWithNotepadAndExit() throws IOException, ExecutionException, InterruptedException {
    Process process = new ProcessBuilder("notepad.exe").start();
    ProcessHandle handle = process.toHandle();

    assertThat(handle.pid()).isNotNull();
    handle.info().command().ifPresent(System.out::println);

    CompletableFuture<ProcessHandle> onExit = handle.onExit();
    handle.destroy();  // bez tego trzeba by recznie ubic process
    onExit.get();
    onExit
        .thenAccept(ph -> System.out.println("PID " + ph.pid() + " został zabity"));
    assertThat(handle.isAlive()).isFalse();
  }
}
