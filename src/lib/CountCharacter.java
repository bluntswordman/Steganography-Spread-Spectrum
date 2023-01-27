package lib;

import java.awt.image.BufferedImage;

public class CountCharacter {
  FileHandler fileHandler = new FileHandler();
  public int byPixel(String path) {
    BufferedImage image = fileHandler.getImage(path);
    int totalBit = (image.getWidth() * 3) * (image.getHeight() * 3);
    return totalBit / 32;
  }
}
