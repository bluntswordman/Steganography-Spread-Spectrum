package lib;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
  public BufferedImage getImage(String path) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File(path));
    } catch (IOException e) {
      System.err.println("Maaf, File gambar tidak ditemukan");
    }
    return image;
  }
  public String getMessage(String path) {
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
  public boolean saveImage(BufferedImage image, String path) {
    try {
      ImageIO.write(image, "png", new File(path));
      return true;
    } catch (IOException e) {
      System.err.println("Maaf, file gambar tidak dapat disimpan");
      return false;
    }
  }
  public boolean saveMessage(String message, String path) {
    try {
      File file = new File(path);
      FileWriter fileWriter = new FileWriter(file);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(message);
      bufferedWriter.close();
      return true;
    } catch (IOException e) {
      System.err.println("Maaf, file pesan tidak dapat disimpan");
      return false;
    }
  }
}
