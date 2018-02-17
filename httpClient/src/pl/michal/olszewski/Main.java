package pl.michal.olszewski;


import static java.time.temporal.ChronoUnit.SECONDS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
    shouldReturnSampleDataContentWhenConnectViaSystemProxy();
    shouldNotFollowRedirectWhenSetToDefaultNever();
    shouldFollowRedirectWhenSetToAlways();
    shouldReturnOKStatusForAuthenticatedAccess();
    shouldSendRequestAsync();
    shouldUseJustTwoThreadWhenProcessingSendAsyncRequest();
    shouldNotStoreCookieWhenPolicyAcceptNone();
    shouldProcessMultipleRequestViaStream();
    shouldStoreCookieWhenPolicyAcceptAll();
    shouldReturnStatusOKWhenSendGetRequest();
    shouldUseHttp2WhenWebsiteUsesHttp2();
    shouldFallbackToHttp1_1WhenWebsiteDoesNotUseHttp2();
    shouldReturnStatusOKWhenSendGetRequestWithDummyHeaders();
    shouldReturnStatusOKWhenSendGetRequestTimeoutSet();
    shouldReturnNoContentWhenPostWithNoBody();
    shouldReturnSampleDataContentWhenPostWithBodyText();
    shouldReturnSampleDataContentWhenPostWithInputStream();
    shouldReturnSampleDataContentWhenPostWithByteArrayProcessorStream();
    shouldReturnSampleDataContentWhenPostWithFileProcessorStream();
    shouldResponseURIDifferentThanRequestUIRWhenRedirect();
  }

  static void shouldReturnSampleDataContentWhenConnectViaSystemProxy() throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/post"))
        .headers("Content-Type", "text/plain;charset=UTF-8")
        .POST(HttpRequest.BodyProcessor.fromString("Sample body"))
        .build();

    HttpResponse<String> response = HttpClient.newBuilder()
        .proxy(ProxySelector.getDefault())
        .build()
        .send(request, HttpResponse.BodyHandler.asString());
    System.out.println(response.body() + " " + response.statusCode());
  }

  static void shouldNotFollowRedirectWhenSetToDefaultNever() throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("http://stackoverflow.com"))
        .version(HttpClient.Version.HTTP_1_1)
        .GET()
        .build();
    HttpResponse<String> response = HttpClient.newBuilder()
        .build()
        .send(request, HttpResponse.BodyHandler.asString());
    System.out.println(response.statusCode());
  }

  static void shouldFollowRedirectWhenSetToAlways() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("http://stackoverflow.com"))
        .version(HttpClient.Version.HTTP_1_1)
        .GET()
        .build();
    HttpResponse<String> response = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode() + " " + response.finalRequest().uri().toString());
  }

  static void shouldReturnOKStatusForAuthenticatedAccess() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/basic-auth"))
        .GET()
        .build();
    HttpResponse<String> response = HttpClient.newBuilder()
        .authenticator(new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("postman", "password".toCharArray());
          }
        })
        .build()
        .send(request, HttpResponse.BodyHandler.asString());
    System.out.println(response.statusCode());
  }

  static void shouldSendRequestAsync() throws URISyntaxException, ExecutionException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/post"))
        .headers("Content-Type", "text/plain;charset=UTF-8")
        .POST(HttpRequest.BodyProcessor.fromString("Sample body"))
        .build();
    CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
        .build()
        .sendAsync(request, HttpResponse.BodyHandler.asString());
    System.out.println(response.get().statusCode());
  }

  static void shouldUseJustTwoThreadWhenProcessingSendAsyncRequest() throws URISyntaxException, ExecutionException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/get"))
        .GET()
        .build();

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    CompletableFuture<HttpResponse<String>> response1 = HttpClient.newBuilder()
        .executor(executorService)
        .build()
        .sendAsync(request, HttpResponse.BodyHandler.asString());

    CompletableFuture<HttpResponse<String>> response2 = HttpClient.newBuilder()
        .executor(executorService)
        .build()
        .sendAsync(request, HttpResponse.BodyHandler.asString());

    CompletableFuture<HttpResponse<String>> response3 = HttpClient.newBuilder()
        .executor(executorService)
        .build()
        .sendAsync(request, HttpResponse.BodyHandler.asString());

    CompletableFuture.allOf(response1, response2, response3)
        .join();

    System.out.println(response1.get().statusCode() + " " + response2.get().statusCode() + " " + response3.get().statusCode() + " ");
  }

  static void shouldNotStoreCookieWhenPolicyAcceptNone() throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/get"))
        .GET()
        .build();

    HttpClient httpClient = HttpClient.newBuilder()
        .cookieManager(new CookieManager(null, CookiePolicy.ACCEPT_NONE))
        .build();

    httpClient.send(request, HttpResponse.BodyHandler.asString());

    System.out.println(httpClient.cookieManager().get().getCookieStore().getCookies());
  }

  static void shouldStoreCookieWhenPolicyAcceptAll() throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/get"))
        .GET()
        .build();

    HttpClient httpClient = HttpClient.newBuilder()
        .cookieManager(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
        .build();

    httpClient.send(request, HttpResponse.BodyHandler.asString());

    System.out.println(httpClient.cookieManager().get().getCookieStore().getCookies());
  }

  static void shouldProcessMultipleRequestViaStream() throws URISyntaxException, ExecutionException, InterruptedException {
    List<URI> targets = Arrays.asList(new URI("https://postman-echo.com/get?foo1=bar1"), new URI("https://postman-echo.com/get?foo2=bar2"));

    HttpClient client = HttpClient.newHttpClient();

    List<CompletableFuture<String>> futures = targets.stream()
        .map(target -> client.sendAsync(HttpRequest.newBuilder(target)
            .GET()
            .build(), HttpResponse.BodyHandler.asString())
            .thenApply(HttpResponse::body))
        .collect(Collectors.toList());

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .join();

    System.out.println(futures.get(0).get() + " " + futures.get(1).get());
  }

  //request
  static void shouldReturnStatusOKWhenSendGetRequest() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/get"))
        .GET()
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode());
  }

  static void shouldUseHttp2WhenWebsiteUsesHttp2() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://stackoverflow.com"))
        .version(HttpClient.Version.HTTP_2)
        .GET()
        .build();
    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode() + " " + response.version());
  }

  static void shouldFallbackToHttp1_1WhenWebsiteDoesNotUseHttp2() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/get"))
        .version(HttpClient.Version.HTTP_2)
        .GET()
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode() + " " + response.version());
  }

  static void shouldReturnStatusOKWhenSendGetRequestWithDummyHeaders() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/get"))
        .headers("key1", "value1", "key2", "value2")
        .GET()
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode());
  }

  static void shouldReturnStatusOKWhenSendGetRequestTimeoutSet() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/get"))
        .timeout(Duration.of(10, SECONDS))
        .GET()
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode());
  }

  static void shouldReturnNoContentWhenPostWithNoBody() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/post"))
        .POST(HttpRequest.noBody())
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode());
  }

  static void shouldReturnSampleDataContentWhenPostWithBodyText() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/post"))
        .headers("Content-Type", "text/plain;charset=UTF-8")
        .POST(HttpRequest.BodyProcessor.fromString("Sample request body"))
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode() + " " + response.body());
  }

  static void shouldReturnSampleDataContentWhenPostWithInputStream() throws IOException, InterruptedException, URISyntaxException {
    byte[] sampleData = "Sample request body".getBytes();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/post"))
        .headers("Content-Type", "text/plain;charset=UTF-8")
        .POST(HttpRequest.BodyProcessor.fromInputStream(() -> new ByteArrayInputStream(sampleData)))
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode() + " " + response.body());
  }

  static void shouldReturnSampleDataContentWhenPostWithByteArrayProcessorStream() throws IOException, InterruptedException, URISyntaxException {
    byte[] sampleData = "Sample request body".getBytes();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/post"))
        .headers("Content-Type", "text/plain;charset=UTF-8")
        .POST(HttpRequest.BodyProcessor.fromByteArray(sampleData))
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode() + " " + response.body());
  }

  static void shouldReturnSampleDataContentWhenPostWithFileProcessorStream() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://postman-echo.com/post"))
        .headers("Content-Type", "text/plain;charset=UTF-8")
        .POST(HttpRequest.BodyProcessor.fromFile(Paths.get("src/test/resources/sample.txt")))
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(response.statusCode() + " " + response.body());
  }

  static void shouldResponseURIDifferentThanRequestUIRWhenRedirect() throws IOException, InterruptedException, URISyntaxException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("http://stackoverflow.com"))
        .version(HttpClient.Version.HTTP_1_1)
        .GET()
        .build();
    HttpResponse<String> response = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()
        .send(request, HttpResponse.BodyHandler.asString());

    System.out.println(request.uri().toString() + " " + response.uri().toString());
  }


}
