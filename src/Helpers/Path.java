package Helpers;

import java.io.File;

public enum Path {
  TEMP_STEGO_IMAGE(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Temp" + File.separator + "Stego" + File.separator),
  TEMP_MESSAGE(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Temp" + File.separator + "Message" + File.separator);
  private final String path;

  Path(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}