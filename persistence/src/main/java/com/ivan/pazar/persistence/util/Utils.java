package com.ivan.pazar.persistence.util;

import com.ivan.pazar.persistence.constants.ConfigConstants;
import java.io.File;

public class Utils {

    public static String getFileNameExtension(String name) {
        if (name == null) {
            return null;
        }
        String[] filenameAndExtension = name.split("[.]");
        if (filenameAndExtension.length == 1) {
            return null;
        }

        return filenameAndExtension[1];
    }

    public static String getProjectFilesFolder() {
        return System.getProperty(ConfigConstants.USER_HOME) + File.separator + ConfigConstants.PROJECT_FILES_DIR;
    }

    public static String getProfilePicturesDirectory() {
        return getProjectFilesFolder() + File.separator + ConfigConstants.PROFILE_PICTURES_DIR;
    }
}
