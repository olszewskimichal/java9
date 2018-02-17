package reactive;

import java.util.concurrent.SubmissionPublisher;

public class SubmissionPublisherExample {

  public static void main(String... args) throws InterruptedException {
    SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
    PlusTenProcessor processor = new PlusTenProcessor();
    PrintSubscriber subscriber = new PrintSubscriber();
    publisher.subscribe(processor);
    processor.subscribe(subscriber);
    System.out.println("Submitting items...");
    for (int i = 0; i < 10; i++) {
      publisher.submit(i);
    }
    Thread.sleep(1000);
    publisher.close();
  }

}