package com.ivan.pazar.persistence.dao;

import com.ivan.pazar.persistence.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DefaultFileSaver implements FileSaver {

    @Override
    public void saveProfilePicture(String fileName, byte[] fileContent) throws IOException {
        File projectFolderDirectory = new File(Utils.getProjectFilesFolder());
        if (!projectFolderDirectory.exists()) {
            projectFolderDirectory.mkdir();
        }
        File profilePicturesDirectory = new File(Utils.getProfilePicturesDirectory());
        if (!profilePicturesDirectory.exists()) {
            profilePicturesDirectory.mkdir();
        }

        Files.write(Paths.get(Utils.getProfilePicturesDirectory() + File.separator + fileName), fileContent);
    }

}
