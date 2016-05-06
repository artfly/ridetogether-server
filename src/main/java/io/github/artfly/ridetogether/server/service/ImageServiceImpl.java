package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.RidetogetherServerApplication;
import io.github.artfly.ridetogether.server.repository.ImageRepository;
import io.github.artfly.ridetogether.server.repository.UserRepository;
import io.github.artfly.ridetogether.server.repository.entities.Image;
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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    ImageServiceImpl(ImageRepository imageRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
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
        return new ImageDto(file.getName());
    }

    @Override
    public FileSystemResource getImageFile(String imagePath) {
        return new FileSystemResource(new File(RidetogetherServerApplication.IMAGES, imagePath));
    }

    @Override
    public ImageDto addImage(CurrentUser currentUser, ImageDto imageDto) {
        File file = new File(RidetogetherServerApplication.IMAGES, imageDto.getImagePath());
        if (!file.exists()) {
            throw new NotFoundException(imageDto.getImagePath());
        }
        Image image = modelMapper.map(imageDto, Image.class);
        image.setCreator(currentUser.getUser());
        imageRepository.save(image);
        return modelMapper.map(image, ImageDto.class);
    }

    @Override
    public ImageDto getCoordinates(String imagePath) {
        return modelMapper.map(Utils.validate(imagePath, imageRepository), ImageDto.class);
    }
}
