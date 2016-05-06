package io.github.artfly.ridetogether.server.web;

import io.github.artfly.ridetogether.server.RidetogetherServerApplication;
import io.github.artfly.ridetogether.server.repository.entities.Image;
import io.github.artfly.ridetogether.server.service.exceptions.NotFoundException;
import io.github.artfly.ridetogether.server.repository.ImageRepository;
import io.github.artfly.ridetogether.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageRepository imageRepository;

    @Autowired
    ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> postImage(@RequestParam("image") MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new NotFoundException("error : image is empty");
        }

        File file = Utils.generateFile(RidetogetherServerApplication.IMAGES);
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
            FileCopyUtils.copy(image.getInputStream(), stream);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(imageRepository.save(new Image(file.getName())), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{imagePath}", method = RequestMethod.PUT)
    ResponseEntity<?> updateImage(@PathVariable String imagePath, @RequestBody Image image) {
        Utils.validate(imagePath, imageRepository);
        Image dbImage = imageRepository.findOne(imagePath);
        dbImage.setLatitude(image.getLatitude());
        dbImage.setLongitude(image.getLongitude());
        dbImage.setImagePath(image.getImagePath());
        imageRepository.save(dbImage);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{imagePath}", method = RequestMethod.GET)
    FileSystemResource getImage(@PathVariable String imagePath) {
        return new FileSystemResource(new File(RidetogetherServerApplication.IMAGES, imagePath));
    }

    @RequestMapping(value = "{imagePath}/coordinates", method = RequestMethod.GET)
    ResponseEntity<?> getCoordinates(@PathVariable String imagePath) {
        Utils.validate(imagePath, imageRepository);
        return new ResponseEntity<>(imageRepository.findOne(imagePath), HttpStatus.OK);
    }
}
