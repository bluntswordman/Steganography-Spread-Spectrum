package lib;

public class Delimiter {
  private final String delimiter;
  public Delimiter(String delimiter) {
    this.delimiter = delimiter;
  }
  public StringBuilder getDelimiter() {
    return new DataConversion().stringToBinary(delimiter);
  }
}
