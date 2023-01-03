package Helpers;

public enum Path {
  COVER_IMAGE_PATH("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Assets\\Images\\Cover-Image\\"),
  STEGO_IMAGE_PATH("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Assets\\Images\\Stego-Image\\"),
  ORIGINAL_MESSAGE_PATH("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Assets\\Messages\\Text-Original\\"),
  RESULT_MESSAGE_PATH("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Assets\\Messages\\Hasil-Extracting\\"),
  STEGO_IMAGE_MANIPULATION_PATH("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Assets\\Images\\Hasil-Manipulasi\\Pengubahan-Kontras-Warna\\"),
  TEMP_STEGO_IMAGE("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Temp\\Stego\\"),
  TEMP_MESSAGE("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Temp\\Message\\"),
  RESULT_MESSAGE_MANIPULATION_PATH("D:\\Sobat-Pengkodingan\\skripsi-code\\real-app\\src\\Assets\\Messages\\Hasil Manipulasi\\Pengubahan-Kontras-Warna\\");

  private final String path;

  Path(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}