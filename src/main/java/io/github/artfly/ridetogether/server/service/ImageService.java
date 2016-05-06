package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.ImageDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageDto addImageFile( MultipartFile image);
    FileSystemResource getImageFile(String imagePath);
    ImageDto addImage(CurrentUser currentUser, ImageDto image);
    ImageDto getCoordinates(String imagePath);
}
