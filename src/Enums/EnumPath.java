package Enums;

import java.io.File;

public enum EnumPath {
  TEMP_STEGO_IMAGE(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Temp" + File.separator + "Stego" + File.separator),
  TEMP_MESSAGE(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Temp" + File.separator + "Message" + File.separator);
  private final String path;
  EnumPath(String path) {
    this.path = path;
  }
  public String getPath() {
    return path;
  }
}
