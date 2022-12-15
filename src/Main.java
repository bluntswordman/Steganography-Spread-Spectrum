import Controllers.SteganographyController;
import Helpers.Path;

public class Main {
  private final static String KEY = "UIGM";

  public static void main(String[] args) {
    SteganographyController steganographyController = new SteganographyController();
    steganographyController.embedding(Path.MESSAGE_PATH.getPath() + "message.txt", Path.IMAGE_PATH.getPath() + "sample.png", Path.IMAGE_PATH.getPath() + "stego-image.png", KEY);
    steganographyController.extracting(Path.IMAGE_PATH.getPath() + "stego-image.png", Path.MESSAGE_PATH.getPath() + "extracted-message.txt", KEY);
  }
}