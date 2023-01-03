package Utils;

public class MessageManipulation {
  public StringBuilder convertStringToBinary(String message) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < message.length(); i++) binary.append(String.format("%8s", Integer.toBinaryString(message.charAt(i))).replace(' ', '0'));

    return binary;
  }

  public StringBuilder convertBinaryToString(StringBuilder binary) {
    StringBuilder message = new StringBuilder();
    if (binary.length() % 8 != 0) {
      int diff = 8 - (binary.length() % 8);
      binary.insert(0, "0".repeat(diff));
    }
    for (int i = 0; i < binary.length(); i += 8) message.append((char) Integer.parseInt(binary.substring(i, i + 8), 2));

    return message;
  }
}