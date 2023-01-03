package Utils;

import Helpers.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageQuality {
  public static int[][][] getRGBMatrix(String path) throws IOException {
    BufferedImage image = ImageIO.read(new File(path));
    int[][][] rgbMatrix = new int[image.getWidth()][image.getHeight()][3];
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        Color color = new Color(image.getRGB(i, j));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        rgbMatrix[i][j][0] = red;
        rgbMatrix[i][j][1] = green;
        rgbMatrix[i][j][2] = blue;
      }
    }
    return rgbMatrix;
  }

  public static double countMSE(int[][][] rgbMatrix1, int[][][] rgbMatrix2) {
    double mse = 0;
    for (int i = 0; i < rgbMatrix1.length; i++) {
      for (int j = 0; j < rgbMatrix1[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          mse += Math.pow(rgbMatrix1[i][j][k] - rgbMatrix2[i][j][k], 2);
        }
      }
    }
    mse /= (rgbMatrix1.length * rgbMatrix1[0].length * 3);
    return mse;
  }

  public static double countPSNR(double mse) {
    return 10 * Math.log10(Math.pow(255, 2) / mse);
  }

  public static void main(String[] args) throws IOException {
    String pathCover = Path.COVER_IMAGE_PATH.getPath() + "windows-xp.png";
    String pathStego = Path.STEGO_IMAGE_PATH.getPath() + "stego-windows-xp.png";

    int[][][] rgbMatrixCover = getRGBMatrix(pathCover);
    int[][][] rgbMatrixStego = getRGBMatrix(pathStego);

    double mse = countMSE(rgbMatrixCover, rgbMatrixStego);
    System.out.println("MSE : " + String.format("%.5f", mse));
    double psnr = countPSNR(mse);
    System.out.println("PSNR : " + String.format("%.5f", psnr) + " dB");
  }
}
