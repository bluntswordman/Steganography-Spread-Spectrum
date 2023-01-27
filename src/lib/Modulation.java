package lib;

public class Modulation {
  RandomGenerator randomGenerator = new RandomGenerator();
  public StringBuilder modulate(StringBuilder binary, String key) {
    StringBuilder newBinary = new StringBuilder();
    for (int i = 0; i < binary.length(); i++) newBinary.append(binary.charAt(i) ^ randomGenerator.generate(key, binary.length() / 8).charAt(i));
    return newBinary;
  }
  public StringBuilder demodulate(StringBuilder binary, String key) {
    StringBuilder newBinary = new StringBuilder();
    StringBuilder pnrg = randomGenerator.generate(key, binary.length() / 8);
    if (pnrg.length() < binary.length()) {
      int diff = binary.length() - pnrg.length();
      pnrg.append("0".repeat(Math.max(0, diff)));
      for (int i = 0; i < binary.length(); i++) newBinary.append(binary.charAt(i) ^ pnrg.charAt(i));
    } else if (pnrg.length() > binary.length()) {
      int diff = pnrg.length() - binary.length();
      binary.append("0".repeat(Math.max(0, diff)));
      for (int i = 0; i < pnrg.length(); i++) newBinary.append(binary.charAt(i) ^ pnrg.charAt(i));
    } else {
      for (int i = 0; i < binary.length(); i++) newBinary.append(binary.charAt(i) ^ randomGenerator.generate(key, binary.length() / 8).charAt(i));
    }
    return newBinary;
  }
}
