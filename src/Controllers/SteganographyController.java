package Controllers;

import Utils.BinaryManipulation;
import Utils.FileManagement;
import Utils.MessageManipulation;
import Utils.StringManipulation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SteganographyController {
  final FileManagement fileManagement = new FileManagement();
  final MessageManipulation messageManipulation = new MessageManipulation();
  final StringManipulation stringManipulation = new StringManipulation();
  final BinaryManipulation binaryManipulation = new BinaryManipulation();

  private int setLeastSignificantBit(int rgb, int bit) {
    int newRGB = rgb;
    if (bit == 1) {
      newRGB = rgb | 1;
    } else if (bit == 0) {
      newRGB = rgb & 0xfe;
    }
    return newRGB;
  }

  final StringBuilder getDelimiter() {
    return messageManipulation.convertStringToBinary("#");
  }

  public void embedding(String messagePath, String imagePath, String stegoPath, String key) {
    String message = fileManagement.getFileMessage(messagePath);
    StringBuilder binaryMessage = messageManipulation.convertStringToBinary(message);
    StringBuilder messageSpreading = stringManipulation.spreadingBits(binaryMessage);
    StringBuilder result = binaryManipulation.modulasi(messageSpreading, key);
    result.append(getDelimiter());
    int[] bits = stringManipulation.convertBinaryToBits(result);

    BufferedImage image = fileManagement.getFileImage(imagePath);
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
        red = setLeastSignificantBit(red, bits[bitIndex]);
        bitIndex++;
      }
      if (bitIndex < bits.length) {
        green = setLeastSignificantBit(green, bits[bitIndex]);
        bitIndex++;
      }
      if (bitIndex < bits.length) {
        blue = setLeastSignificantBit(blue, bits[bitIndex]);
        bitIndex++;
      }

      int stegoPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
      stegoPixels[i] = stegoPixel;
    }

    stegoImage.setRGB(0, 0, image.getWidth(), image.getHeight(), stegoPixels, 0, image.getWidth());
    File file = new File(stegoPath);
    try {
      ImageIO.write(stegoImage, "png", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void extracting(String stegoPath, String plainTextPath, String key) {
    BufferedImage image = fileManagement.getFileImage(stegoPath);
    int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
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

    StringBuilder result = stringManipulation.convertBitsToBinary(bits);
    ArrayList<String> arrayResult = new ArrayList<String>();
    for (int i = 0; i < result.length(); i += 8) {
      arrayResult.add(result.substring(i, i + 8));
    }
    StringBuilder resultBinary = new StringBuilder();
    for (String s : arrayResult) {
      if (s.equals(getDelimiter().toString())) {
        break;
      }
      resultBinary.append(s);
    }
    StringBuilder messageDeModulasi = binaryManipulation.demodulasi(resultBinary, key);
    StringBuilder messageDeSpreading = stringManipulation.deSpreadingBits(messageDeModulasi);
    String message = String.valueOf(messageManipulation.convertBinaryToString(messageDeSpreading));

    try {
      File file = new File(plainTextPath);
      FileWriter fileWriter = new FileWriter(file);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(message);
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}