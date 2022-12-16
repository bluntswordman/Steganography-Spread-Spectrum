package Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileManagement {
  public BufferedImage getFileImage(String path) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File(path));
    } catch (IOException e) {
      System.err.println("Maaf, gambar tidak ditemukan");
    }

    return image;
  }

  public String getFileMessage(String path) {
    StringBuilder message = new StringBuilder();
    File file = new File(path);
    try {
      Scanner scan = new Scanner(file);
      while (scan.hasNextLine()) {
        String next = scan.nextLine();
        message.append(next);
        if (scan.hasNextLine()) {
          message.append("\n");
        }
      }
      scan.close();
    } catch (FileNotFoundException e) {
      System.err.println("Maaf, file pesan tidak ditemukan");
    }

    return message.toString();
  }
}
