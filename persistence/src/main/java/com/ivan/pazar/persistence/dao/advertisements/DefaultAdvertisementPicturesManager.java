package com.ivan.pazar.persistence.dao.advertisements;

import com.ivan.pazar.persistence.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DefaultAdvertisementPicturesManager implements AdvertisementPicturesManager{

    @Override
    public void savePictures(List<String> imagesNames, List<byte[]> imagesContents) throws IOException {
        if (imagesNames == null || imagesContents == null) {
            return;
        }
        Utils.createProjectDirIfNotExist();
        Utils.createAdvertisementsDirIfNotExists();

        for (int i = 0; i < imagesNames.size(); i++) {
            String fileName = imagesNames.get(i);
            byte[] fileContent = imagesContents.get(i);
            Files.write(Paths.get(Utils.getAdvertisementsDirectory() + File.separator + fileName), fileContent);
        }
    }

    @Override
    public void deletePicturesIfExist(List<String> picturesNames) {
        if (picturesNames == null) {
            return;
        }
        picturesNames.forEach(pictureName -> {
            try {
                Files.deleteIfExists(Paths.get(Utils.getAdvertisementsDirectory() + File.separator + pictureName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
