package lib;

public class RandomGenerator {
  DataConversion stringConversion = new DataConversion();
  private static int getSeed(StringBuilder binary) {
    String[] binaryArrays = new String[binary.length() / 8];
    for (int i = 0; i < binaryArrays.length; i++) binaryArrays[i] = binary.substring(i * 8, (i + 1) * 8);
    StringBuilder xorBinary = new StringBuilder();
    for (int i = 0; i < binaryArrays[0].length(); i++) {
      int xor = 0;
      for (String binaryArray : binaryArrays) {
        xor ^= Integer.parseInt(String.valueOf(binaryArray.charAt(i)));
      }
      xorBinary.append(xor);
    }
    return Integer.parseInt(xorBinary.toString(), 2);
  }
  private static int[] getBlumBlumShub(int seed, int length) {
    int PRIME_NUMBER_P = 11;
    int PRIME_NUMBER_Q = 23;
    int n = PRIME_NUMBER_P * PRIME_NUMBER_Q;
    int[] blumBlumShub = new int[length];
    for (int i = 0; i < blumBlumShub.length; i++) {
      seed = (seed * seed) % n;
      blumBlumShub[i] = seed;
    }
    return blumBlumShub;
  }
  public StringBuilder generate(String key, int length) {
    StringBuilder binary = stringConversion.stringToBinary(key);
    int seed = getSeed(binary);
    int[] pnrg = getBlumBlumShub(seed, length);
    return stringConversion.integerToBinary(pnrg);
  }
}
