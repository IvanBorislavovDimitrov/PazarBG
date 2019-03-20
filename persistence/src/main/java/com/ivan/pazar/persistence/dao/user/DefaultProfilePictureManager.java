package com.ivan.pazar.persistence.dao.user;

import com.ivan.pazar.persistence.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DefaultProfilePictureManager implements ProfilePictureManager {

    @Override
    public void saveProfilePicture(String fileName, byte[] fileContent) throws IOException {
        Utils.createProjectDirIfNotExist();
        Utils.createProfilePicturesDirIfNotExists();

        Files.write(Paths.get(Utils.getProfilePicturesDirectory() + File.separator + fileName), fileContent);
    }

    @Override
    public void deletePictureIfExists(String pictureName) throws IOException {
        Files.deleteIfExists(Paths.get(Utils.getProfilePicturesDirectory() + File.separator + pictureName));
    }

}
