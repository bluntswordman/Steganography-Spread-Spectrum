package Helpers;

public enum Path {
//  for IMAGE_PATH => fill in with the location of your image file storage
//  for MESSAGE_PATH => fill in with the location of your message file storage
//  example: C:\\Users\\User\\Desktop\\Java\\ChatApp\\src\\Images\\
//  example: C:\\Users\\User\\Desktop\\Java\\ChatApp\\src\\Messages\\
  IMAGE_PATH(""),
  MESSAGE_PATH("");

  private final String path;

  Path(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
