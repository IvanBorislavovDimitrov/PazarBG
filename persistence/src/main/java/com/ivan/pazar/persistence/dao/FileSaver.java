package com.ivan.pazar.persistence.dao;

import org.apache.tomcat.jni.File;

import java.io.IOException;

public interface FileSaver {

    void saveProfilePicture(String fileName, byte[] fileContent) throws IOException;
}
