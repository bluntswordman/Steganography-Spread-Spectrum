package lib;

public class Spreader {
  public StringBuilder spreading(StringBuilder binary) {
    StringBuilder spreadingBits = new StringBuilder();
    for (int i = 0; i < binary.length(); i++) spreadingBits.append(binary.charAt(i)).append(binary.charAt(i)).append(binary.charAt(i)).append(binary.charAt(i));
    return spreadingBits;
  }
  public  StringBuilder deSpreading(StringBuilder spreadingBits) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < spreadingBits.length(); i += 4) binary.append(spreadingBits.charAt(i));
    return binary;
  }
}
