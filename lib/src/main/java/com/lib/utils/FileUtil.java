package com.lib.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by LSD on 15/9/23.
 */
public class FileUtil {
    public static String CACHE_BASE = "";

    /**
     * @return
     */
    public static File getFoder(){
        if(!FileUtil.isSDExist()){
            return null;
        }
        File folder = new File(getFolderPath());
        if(!folder.exists()){
            folder.mkdir();
        }
        return folder;
    }

    private static String getFolderPath(){
        return android.os.Environment.getExternalStorageDirectory().toString() + CACHE_BASE;
    }

    public static boolean isSDExist(){
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static boolean mkdirs(File directory) {
        try {
            forceMkdir(directory);
            return true;
        } catch (IOException e){
        }
        return false;
    }

    public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                String message =
                        "File "
                                + directory
                                + " exists and is "
                                + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                if (!directory.isDirectory()) {
                    String message =
                            "Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }

}
