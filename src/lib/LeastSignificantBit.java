package lib;

public class LeastSignificantBit {
  private final int rgb;
  private final int bit;
  public LeastSignificantBit(int rgb, int bit) {
    this.rgb = rgb;
    this.bit = bit;
  }
  public int generate() {
    int newRGB = rgb;
    if (bit == 1) {
      newRGB = rgb | 1;
    } else if (bit == 0) {
      newRGB = rgb & 0xfe;
    }
    return newRGB;
  }
}
