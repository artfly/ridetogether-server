package io.github.artfly.ridetogether.server.web;


import io.github.artfly.ridetogether.server.service.ImageService;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/images")
class ImageController {
    private final ImageService imageService;

    @Autowired
    ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

//    @PreAuthorize("isAuthenticated()")
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<ImageDto> postImageFile(MultipartFile image) {
//        return new ResponseEntity<>(imageService.addImageFile(image), HttpStatus.CREATED);
//    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{imagePath}/coordinates",method = RequestMethod.POST)
    public ResponseEntity<ImageDto> postImage(@AuthenticationPrincipal CurrentUser currentUser,
                                              @PathVariable String imagePath, @RequestBody ImageDto imageDto) {
        return new ResponseEntity<>(imageService.addImage(currentUser, imageDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{imagePath}/coordinates", method = RequestMethod.GET)
    public ResponseEntity<ImageDto> getImage(@PathVariable String imagePath) {
        return new ResponseEntity<>(imageService.getCoordinates(imagePath), HttpStatus.OK);
    }
//
//    @RequestMapping(value = "{imagePath}", method = RequestMethod.GET)
//    public FileSystemResource getImageFile(@PathVariable String imagePath) {
//        return imageService.getImageFile(imagePath);
//    }
}