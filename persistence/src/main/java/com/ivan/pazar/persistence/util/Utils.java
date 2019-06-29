package com.ivan.pazar.persistence.util;

import com.ivan.pazar.persistence.constants.PersistenceConstants;
import java.io.File;

public class Utils {

    public static String getFileNameExtension(String name) {
        if (name == null) {
            return null;
        }
        String[] filenameAndExtension = name.split("[.]");
        if (filenameAndExtension.length <= 1) {
            return null;
        }

        return filenameAndExtension[filenameAndExtension.length - 1];
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

    public static void createVideoDirIfNotExists() {
        File videosDirectory = new File(Utils.getVideosDirectory());
        if (!videosDirectory.exists()) {
            videosDirectory.mkdir();
        }

    }

    public static String getProjectFilesFolder() {
        return System.getProperty(PersistenceConstants.USER_HOME) + File.separator + PersistenceConstants.PROJECT_FILES_DIR;
    }

    public static String getProfilePicturesDirectory() {
        return getProjectFilesFolder() + File.separator + PersistenceConstants.PROFILE_PICTURES_DIR;
    }

    public static String getAdvertisementsDirectory() {
        return getProjectFilesFolder() + File.separator + PersistenceConstants.ADVERTISEMENTS_DIR;
    }

    public static String getVideosDirectory() {
        return getProjectFilesFolder() + File.separator + PersistenceConstants.VIDEO_DIR;

    }
}
