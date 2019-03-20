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
        Utils.createAdvertisementsDirIfNotExists();

        for (int i = 0; i < imagesNames.size(); i++) {
            String fileName = imagesNames.get(i);
            byte[] fileContent = imagesContents.get(i);
            Files.write(Paths.get(Utils.getAdvertisementsDirectory() + File.separator + fileName), fileContent);
        }
    }
}
