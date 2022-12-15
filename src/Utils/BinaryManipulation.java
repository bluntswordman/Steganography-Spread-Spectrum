package Utils;

public class BinaryManipulation {
  private final PseudoRandomNumberGenerator pseudoRandomNumberGenerator = new PseudoRandomNumberGenerator();

  public StringBuilder modulasi(StringBuilder binary, String key) {
    String[] binaryArrays = new String[binary.length() / 8];
    for (int i = 0; i < binaryArrays.length; i++) {
      binaryArrays[i] = binary.substring(i * 8, (i + 1) * 8);
    }
    StringBuilder pnrg = pseudoRandomNumberGenerator.generate(key, binaryArrays.length);
    StringBuilder newBinary = new StringBuilder();
    for (int i = 0; i < binary.length(); i++) {
      newBinary.append(binary.charAt(i) ^ pnrg.charAt(i));
    }
    return newBinary;
  }

  public StringBuilder demodulasi(StringBuilder binary, String key) {
    String[] binaryArrays = new String[binary.length() / 8];
    for (int i = 0; i < binaryArrays.length; i++) {
      binaryArrays[i] = binary.substring(i * 8, (i + 1) * 8);
    }
    StringBuilder pnrg = pseudoRandomNumberGenerator.generate(key, binaryArrays.length);
    StringBuilder newBinary = new StringBuilder();
    for (int i = 0; i < binary.length(); i++) {
      newBinary.append(binary.charAt(i) ^ pnrg.charAt(i));
    }
    return newBinary;
  }
}
