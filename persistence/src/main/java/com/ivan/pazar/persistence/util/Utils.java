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

    public static void createAdvertisementsDirIfNotExists() {
        File advertisementsDirectory = new File(Utils.getAdvertisementsDirectory());
        if (!advertisementsDirectory.exists()) {
            advertisementsDirectory.mkdir();
        }
    }

    public static void createProfilePicturesDirIfNotExists() {
        File profilePicturesDirectory = new File(Utils.getProfilePicturesDirectory());
        if (!profilePicturesDirectory.exists()) {
            profilePicturesDirectory.mkdir();
        }
    }

    public static void createProjectDirIfNotExist() {
        File projectFolderDirectory = new File(Utils.getProjectFilesFolder());
        if (!projectFolderDirectory.exists()) {
            projectFolderDirectory.mkdir();
        }
    }

    public static String getProjectFilesFolder() {
        return System.getProperty(ConfigConstants.USER_HOME) + File.separator + ConfigConstants.PROJECT_FILES_DIR;
    }

    public static String getProfilePicturesDirectory() {
        return getProjectFilesFolder() + File.separator + ConfigConstants.PROFILE_PICTURES_DIR;
    }

    public static String getAdvertisementsDirectory() {
        return getProjectFilesFolder() + File.separator + ConfigConstants.ADVERTISEMENTS_DIR;
    }
}
