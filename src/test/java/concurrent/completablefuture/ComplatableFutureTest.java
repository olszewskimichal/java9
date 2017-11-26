package concurrent.completablefuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;

class ComplatableFutureTest {

  @Test
  void whenCopyIsDoneThenParentIsNotDone() {
    CompletableFuture<String> future = new CompletableFuture<>();
    CompletableFuture<String> copy = future.copy();
    assertThat(future.isDone()).isFalse();
    assertThat(copy.isDone()).isFalse();
    copy.complete("test");
    assertThat(future.isDone()).isFalse();
    assertThat(copy.isDone()).isTrue();
  }

  @Test
  void whenParentIsDoneThenCopyIsNotDone() {
    CompletableFuture<String> future = new CompletableFuture<>();
    CompletableFuture<String> copy = future.copy();
    future.complete("test");
    assertThat(future.isDone()).isTrue();
    assertThat(copy.isDone()).isTrue();
  }


  @Test
  void testDelay() throws Exception {
    Object input = new Object();
    CompletableFuture<Object> future = new CompletableFuture<>();
    future.completeAsync(() -> input, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

    Thread.sleep(100);

    assertFalse(future.isDone());

    Thread.sleep(1000);
    assertTrue(future.isDone());
    assertSame(input, future.get());
  }

  @Test
  void testTimeoutTriggered() throws Exception {
    CompletableFuture<Object> future = new CompletableFuture<>();
    future.orTimeout(1, TimeUnit.SECONDS);

    Thread.sleep(1100);

    assertTrue(future.isDone());

    try {
      future.get();
    } catch (ExecutionException e) {
      assertTrue(e.getCause() instanceof TimeoutException);
    }
  }

  @Test
  void testTimeoutNotTriggered() throws Exception {
    Object input = new Object();
    CompletableFuture<Object> future = new CompletableFuture<>();

    future.orTimeout(1, TimeUnit.SECONDS);

    Thread.sleep(100);

    future.complete(input);

    Thread.sleep(1000);

    assertTrue(future.isDone());
    assertSame(input, future.get());
  }


  @Test
  void completeOnTimeout() throws Exception {
    Object input = new Object();
    CompletableFuture<Object> future = new CompletableFuture<>();
    future.completeOnTimeout(input, 1, TimeUnit.SECONDS);

    Thread.sleep(1100);

    assertTrue(future.isDone());
    assertSame(input, future.get());
  }
}
