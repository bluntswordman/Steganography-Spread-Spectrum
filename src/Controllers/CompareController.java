package Controllers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CompareController {
  private static int[][][] matrixRGB(String path) throws IOException {
    BufferedImage image = ImageIO.read(new File(path));
    int[][][] matrix = new int[image.getWidth()][image.getHeight()][3];
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        Color color = new Color(image.getRGB(i, j));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        matrix[i][j][0] = red;
        matrix[i][j][1] = green;
        matrix[i][j][2] = blue;
      }
    }
    return matrix;
  }
  private static double countMSE(int[][][] matrixA, int[][][] matrixB) {
    double mse = 0;
    for (int i = 0; i < matrixA.length; i++) {
      for (int j = 0; j < matrixA[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          mse += Math.pow(matrixA[i][j][k] - matrixB[i][j][k], 2);
        }
      }
    }
    mse /= (matrixA.length * matrixA[0].length * 3);
    return mse;
  }
  private static double countPSNR(double mse) {
    return 10 * Math.log10(Math.pow(255, 2) / mse);
  }
  public HashMap<String, Double> generate(String pathCover, String pathStego) throws IOException {
    int[][][] matrixCover = matrixRGB(pathCover);
    int[][][] matrixStego = matrixRGB(pathStego);
    double mse = countMSE(matrixCover, matrixStego);
    double psnr = countPSNR(mse);
    HashMap<String, Double> imageQuality = new HashMap<>();
    imageQuality.put("MSE", Double.valueOf(String.format("%.5f", mse)));
    imageQuality.put("PSNR", Double.valueOf(String.format("%.5f", psnr)));
    return imageQuality;
  }
}
