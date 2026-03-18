package com.rgt.ebanking.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class ImageValidator {
    public void validateImage(MultipartFile file) {
    if (file.isEmpty()) {
        throw new IllegalArgumentException("No file received");
    }

    if (file.getSize() > 5 * 1024 * 1024) {
        throw new IllegalArgumentException("Image is too large");
    }

    String contentType = file.getContentType();
    if (!List.of("image/jpeg", "image/png", "image/webp").contains(contentType)) {
        throw new IllegalArgumentException("Unsupported image type");
    }

    try (InputStream in = file.getInputStream()) {
        if (ImageIO.read(in) == null) {
            throw new IllegalArgumentException("File is not a valid image");
        }
    } catch (IOException ex) {
        throw new IllegalStateException("Could not read uploaded file");
    }
}
}
