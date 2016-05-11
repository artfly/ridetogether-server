package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.RidetogetherServerApplication;
import io.github.artfly.ridetogether.server.repository.ImageRepository;
import io.github.artfly.ridetogether.server.repository.entities.Image;
import io.github.artfly.ridetogether.server.service.exceptions.AuthorizeException;
import io.github.artfly.ridetogether.server.service.exceptions.NotFoundException;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.utils.Utils;
import io.github.artfly.ridetogether.server.web.dto.ImageDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    ImageServiceImpl(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ImageDto addImageFile(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }

        File file = Utils.generateFile(RidetogetherServerApplication.IMAGES);
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
            FileCopyUtils.copy(image.getInputStream(), stream);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        imageRepository.save(new Image(file.getName(), null, null));
        return new ImageDto(file.getName());
    }

    @Override
    public FileSystemResource getImageFile(String imagePath) {
        File file = new File(RidetogetherServerApplication.IMAGES, imagePath);
        if (!file.exists()) {
            throw new NotFoundException(imagePath);
        }
        return new FileSystemResource(file);
    }

    @Override
    public ImageDto getImage(String imagePath) {
        return modelMapper.map(Utils.validate(imagePath, imageRepository), ImageDto.class);
    }

    @Override
    public ImageDto addCoordinates(CurrentUser currentUser, ImageDto imageDto) {
        Image image = Utils.validate(imageDto.getImagePath(), imageRepository);
        if (image.getCreator() == null) {
            image.setCreator(currentUser.getUser());
        } else if (!image.getCreator().getUsername().equals(currentUser.getUsername())) {
            throw new AuthorizeException(currentUser.getUsername());
        }
        image.setLatitude(imageDto.getLatitude());
        image.setLongitude(imageDto.getLongitude());
        imageRepository.save(image);
        return modelMapper.map(image, ImageDto.class);
    }
}
