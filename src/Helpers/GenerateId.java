package Helpers;

public class GenerateId {
  public String generateId() {
    StringBuilder id = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      id.append((int) (Math.random() * 10));
    }
    return id.toString();
  }
}
