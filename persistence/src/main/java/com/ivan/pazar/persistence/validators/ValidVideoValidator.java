package com.ivan.pazar.persistence.validators;

import com.ivan.pazar.persistence.util.Utils;
import com.ivan.pazar.persistence.validation_annotations.ValidVideo;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidVideoValidator implements ConstraintValidator<ValidVideo, MultipartFile> {

    private static final String MP4 = "mp4";
    private static final String MKV = "mkv";
    private static final String THREEGP = "3gp";

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String fileNameExtension = Utils.getFileNameExtension(file.getOriginalFilename());
        if (fileNameExtension == null) {
            return true;
        }
        return MP4.equalsIgnoreCase(fileNameExtension) || THREEGP.equalsIgnoreCase(fileNameExtension)
                || MKV.equalsIgnoreCase(fileNameExtension);
    }
}
