package Utils;

public class BinaryManipulation {
  private static StringBuilder getPNRG(String key, int length) {
    return new PseudoRandomNumberGenerator().generate(key, length);
  }

  public StringBuilder modulasi(StringBuilder binary, String key) {
    StringBuilder newBinary = new StringBuilder();
    for (int i = 0; i < binary.length(); i++) {
      newBinary.append(binary.charAt(i) ^ getPNRG(key, binary.length() / 8).charAt(i));
    }
    return newBinary;
  }

  public StringBuilder demodulasi(StringBuilder binary, String key) {
    StringBuilder newBinary = new StringBuilder();
    for (int i = 0; i < binary.length(); i++) {
      newBinary.append(binary.charAt(i) ^ getPNRG(key, binary.length() / 8).charAt(i));
    }
    return newBinary;
  }
}
