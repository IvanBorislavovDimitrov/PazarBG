package com.ivan.pazar.persistence.dao.advertisements;

import java.io.IOException;
import java.util.List;

public interface AdvertisementPicturesManager {

    void savePictures(List<String> imagesNames, List<byte[]> imagesContents) throws IOException;
}
