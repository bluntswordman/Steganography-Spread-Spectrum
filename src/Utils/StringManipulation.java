package Utils;

public class StringManipulation {
  public StringBuilder spreadingBits(StringBuilder binary) {
    StringBuilder spreadingBits = new StringBuilder();
    for (int i = 0; i < binary.length(); i++) spreadingBits.append(binary.charAt(i)).append(binary.charAt(i)).append(binary.charAt(i)).append(binary.charAt(i));

    return spreadingBits;
  }

  public StringBuilder deSpreadingBits(StringBuilder spreadingBits) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < spreadingBits.length(); i += 4) binary.append(spreadingBits.charAt(i));

    return binary;
  }

  public int[] convertBinaryToBits(StringBuilder binary) {
    int[] bits = new int[binary.length()];
    for (int i = 0; i < binary.length(); i++) bits[i] = Integer.parseInt(String.valueOf(binary.charAt(i)));

    return bits;
  }

  public StringBuilder convertBitsToBinary(int[] bits) {
    StringBuilder binary = new StringBuilder();
    for (int bit : bits) binary.append(bit);

    return binary;
  }
}
