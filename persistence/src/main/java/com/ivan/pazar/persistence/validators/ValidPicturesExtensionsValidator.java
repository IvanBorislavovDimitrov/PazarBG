package com.ivan.pazar.persistence.validators;

import com.ivan.pazar.persistence.util.Utils;
import com.ivan.pazar.persistence.validation_annotations.ValidPicturesExtensions;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class ValidPicturesExtensionsValidator implements ConstraintValidator<ValidPicturesExtensions, List<MultipartFile>> {

    private static final String JPG = "jpg";
    private static final String PNG = "png";
    private static final String JPEG = "jpeg";

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null) {
            return true;
        }
        if (files.isEmpty()) {
            return true;
        }
        if (files.size() == 1) {
            if (Utils.getFileNameExtension(files.get(0).getOriginalFilename()) == null) {
                return true;
            }
        }
        return files.stream()
                .noneMatch(file -> {
                    String fileNameExtension = Utils.getFileNameExtension(file.getOriginalFilename());
                    return !JPG.equalsIgnoreCase(fileNameExtension) && !PNG.equalsIgnoreCase(fileNameExtension) &&
                            !JPEG.equalsIgnoreCase(fileNameExtension);
                });
    }
}
