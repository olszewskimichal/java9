/*
package http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.junit.jupiter.api.Test;

*/
/***
 * https://howtoprogram.xyz/2017/10/19/java-9-http-2-client-api-example/
 *//*

class HttpClientTest {

  @Test
  void shouldSynchronousGet() throws URISyntaxException, IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://github.com/olszewskimichal"))
        .GET()
        .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
    assertEquals(200, response.statusCode());
  }

  @Test
  void ShouldAsynchronousGet() throws Exception {

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://github.com/olszewskimichal"))
        .GET()
        .build();
    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
        HttpResponse.BodyHandler.asString());

    HttpResponse<String> actualResponse = response.get(10, TimeUnit.SECONDS);
    assertEquals(200, actualResponse.statusCode());

  }
}
*/
