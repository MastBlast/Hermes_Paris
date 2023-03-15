package controller;

public class Utils {
  /**
   * Checks if a String is a valid identity number (12 digits)
   * 
   * @param identityNo the String to check
   * @return true if the String is a valid identity number
   */
  public static boolean isValidIdentityNo(String identityNo) {
    if (identityNo.matches("[0-9]+") && identityNo.length() == 12) {
      return true;
    } else {
      return false;
    }
  }
}
