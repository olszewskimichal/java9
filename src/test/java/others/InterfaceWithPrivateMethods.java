package others;

public interface InterfaceWithPrivateMethods {

  private static String staticPrivate() {
    return "static";
  }

  private String instancePrivate() {
    return "instance private";
  }

  default String getString() {
    String s = staticPrivate() + " " + instancePrivate();
    System.out.println(s);
    return s;
  }

}
