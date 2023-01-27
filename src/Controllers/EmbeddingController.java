package Controllers;

import lib.*;

import java.awt.image.BufferedImage;

public class EmbeddingController {
  private final String messagePath;
  private final String imagePath;
  private final String stegoPath;
  private final String key;

  public EmbeddingController(String messagePath, String imagePath, String stegoPath, String key) {
    this.messagePath = messagePath;
    this.imagePath = imagePath;
    this.stegoPath = stegoPath;
    this.key = key;
  }

  public boolean generateStegoImage() {
    String message = new FileHandler().getMessage(messagePath);
    StringBuilder binaryMessage = new DataConversion().stringToBinary(message);
    StringBuilder messageSpreading = new Spreader().spreading(binaryMessage);
    StringBuilder result = new Modulation().modulate(messageSpreading, key);
    result.append(new Delimiter("#").getDelimiter());
    int[] bits = new DataConversion().binaryToBits(result);

    BufferedImage image = new FileHandler().getImage(imagePath);
    BufferedImage stegoImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
    int[] stegoPixels = new int[pixels.length];
    int bitIndex = 0;

    for (int i = 0; i < pixels.length; i++) {
      int pixel = pixels[i];
      int red = (pixel >> 16) & 0xff;
      int green = (pixel >> 8) & 0xff;
      int blue = (pixel) & 0xff;
      int alpha = (pixel >> 24) & 0xff;

      if (bitIndex < bits.length) {
        red = new LeastSignificantBit(red, bits[bitIndex]).generate();
        bitIndex++;
      }
      if (bitIndex < bits.length) {
        green = new LeastSignificantBit(green, bits[bitIndex]).generate();
        bitIndex++;
      }
      if (bitIndex < bits.length) {
        blue = new LeastSignificantBit(blue, bits[bitIndex]).generate();
        bitIndex++;
      }
      int stegoPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
      stegoPixels[i] = stegoPixel;
    }
    stegoImage.setRGB(0, 0, image.getWidth(), image.getHeight(), stegoPixels, 0, image.getWidth());

    return new FileHandler().saveImage(stegoImage, stegoPath);
  }
}
