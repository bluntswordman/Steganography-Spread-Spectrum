package lib;

public class DataConversion {
  public StringBuilder stringToBinary(String message) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < message.length(); i++) binary.append(String.format("%8s", Integer.toBinaryString(message.charAt(i))).replace(' ', '0'));
    return binary;
  }
  public StringBuilder binaryToString(StringBuilder binary) {
    StringBuilder message = new StringBuilder();
    if (binary.length() % 8 != 0) {
      int diff = 8 - (binary.length() % 8);
      binary.insert(0, "0".repeat(diff));
    }
    for (int i = 0; i < binary.length(); i += 8) message.append((char) Integer.parseInt(binary.substring(i, i + 8), 2));
    return message;
  }
  public StringBuilder bitsToBinary(int[] bits) {
    StringBuilder binary = new StringBuilder();
    for (int bit : bits) binary.append(bit);
    return binary;
  }
  public int[] binaryToBits(StringBuilder binary) {
    int[] bits = new int[binary.length()];
    for (int i = 0; i < binary.length(); i++) bits[i] = Integer.parseInt(String.valueOf(binary.charAt(i)));
    return bits;
  }
  public StringBuilder integerToBinary(int[] integers) {
    StringBuilder binary = new StringBuilder();
    for (int integer : integers) binary.append(String.format("%8s", Integer.toBinaryString(integer)).replace(' ', '0'));
    return binary;
  }
  public int[] binaryToInteger(StringBuilder binary) {
    int[] integers = new int[binary.length() / 8];
    for (int i = 0; i < integers.length; i++) integers[i] = Integer.parseInt(binary.substring(i * 8, (i + 1) * 8), 2);
    return integers;
  }
}
