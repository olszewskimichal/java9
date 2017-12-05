package process;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
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

  @Test
  void shouldStartElasticSeatch() throws IOException, InterruptedException {
    Process process = new ProcessBuilder("F:\\kibana\\elasticsearch-5.6.1\\elasticsearch-5.6.1\\bin\\elasticsearch.bat").start();
    System.out.println("elasticSearch wstal jako proces " + process.pid());
    Thread.sleep(5000);
    Process process2 = new ProcessBuilder("F:\\kibana\\logstash-5.6.1\\logstash-5.6.1\\bin\\logstash.bat -f logstash-rss.conf").start();
    System.out.println("logstash wstal jako proces " + process2.pid());
    Thread.sleep(5000);
    Process process3 = new ProcessBuilder("F:\\kibana\\kibana-5.6.1-windows-x86\\kibana-5.6.1-windows-x86\\bin\\kibana.bat").start();
    System.out.println("kibana wstala jako proces " + process3.pid());
  }
}
