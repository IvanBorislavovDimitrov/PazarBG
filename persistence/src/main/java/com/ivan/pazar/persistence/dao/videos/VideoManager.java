package com.ivan.pazar.persistence.dao.videos;

import java.io.IOException;

public interface VideoManager {

    void saveVideo(String fileName, byte[] fileContent) throws IOException;

    void deleteVideo(String videoName);
}
