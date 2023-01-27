package Controllers;

import lib.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ExtractingController {
  private final String stegoPath;
  private final String resultMessagePath;
  private final String key;

  public ExtractingController(String stegoPath, String resultMessagePath, String key) {
    this.stegoPath = stegoPath;
    this.resultMessagePath = resultMessagePath;
    this.key = key;
  }

  public boolean extractMessage() {
    BufferedImage stegoImage = new FileHandler().getImage(stegoPath);
    int[] pixels = stegoImage.getRGB(0, 0, stegoImage.getWidth(), stegoImage.getHeight(), null, 0, stegoImage.getWidth());
    int[] bits = new int[pixels.length * 3];
    int bitIndex = 0;

    for (int pixel : pixels) {
      int red = (pixel >> 16) & 0xff;
      int green = (pixel >> 8) & 0xff;
      int blue = (pixel) & 0xff;

      bits[bitIndex++] = red & 1;
      bits[bitIndex++] = green & 1;
      bits[bitIndex++] = blue & 1;
    }

    StringBuilder result = new DataConversion().bitsToBinary(bits);
    ArrayList<String> arrayResult = new ArrayList<>();
    for (int i = 0; i < result.length(); i += 8) {
      arrayResult.add(result.substring(i, i + 8));
    }
    StringBuilder resultBinary = new StringBuilder();
    for (String ar : arrayResult) {
      if (ar.equals(new Delimiter("#").getDelimiter().toString())) {
        break;
      }
      resultBinary.append(ar);
    }
    StringBuilder messageDemodulation = new Modulation().demodulate(resultBinary, key);
    StringBuilder messageDespreading = new Spreader().deSpreading(messageDemodulation);
    String message = String.valueOf(new DataConversion().binaryToString(messageDespreading));
    return new FileHandler().saveMessage(message, resultMessagePath);
  }
}
