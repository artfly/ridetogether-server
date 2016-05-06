package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.ImageDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageDto addImageFile(MultipartFile image);
    ImageDto addImage(CurrentUser currentUser, ImageDto imageDto);
    FileSystemResource getImageFile(String imagePath);
    ImageDto getImage(String imagePath);
    void updateCoordinates(CurrentUser currentUser, ImageDto imageDto);
}
