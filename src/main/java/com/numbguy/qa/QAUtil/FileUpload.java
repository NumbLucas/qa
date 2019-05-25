package com.numbguy.qa.QAUtil;

public class FileUpload {
    public static String TOUTIAO_DOMAIN = "http://127.0.0.1:8080/";
    public static  String IMAGE_DIR = "D:/uploadImage/";
    public static String[] IMAGE_FILE_EXT = new String[]{"png", "bmp", "jpg"};

    public static  boolean isFileValid(String fileExt) {
        for(String ext : IMAGE_FILE_EXT) {
            if(ext.equals(fileExt)) {
                return  true;
            }
        }
        return false;
    }
}
