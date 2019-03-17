package com.ivan.pazar.persistence.dao;

import org.apache.tomcat.jni.File;

import java.io.IOException;

public interface ProfilePictureManager {

    void saveProfilePicture(String fileName, byte[] fileContent) throws IOException;

    void deletePictureIfExists(String pictureName) throws IOException;
}
