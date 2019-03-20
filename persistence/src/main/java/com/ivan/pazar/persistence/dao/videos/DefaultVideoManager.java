package com.ivan.pazar.persistence.dao.videos;

import com.ivan.pazar.persistence.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DefaultVideoManager implements VideoManager {

    @Override
    public void saveVideo(String fileName, byte[] fileContent) throws IOException {
        Utils.createProjectDirIfNotExist();
        Utils.createVideoDirIfNotExists();

        Files.write(Paths.get(Utils.getVideosDirectory() + File.separator + fileName), fileContent);

    }
}
