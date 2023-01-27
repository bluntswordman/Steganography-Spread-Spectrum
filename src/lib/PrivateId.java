package lib;

public class PrivateId {
  public String generate() {
    StringBuilder id = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      id.append((int) (Math.random() * 10));
    }
    return id.toString();
  }
}
