package io.github.artfly.ridetogether.server.web.dto;

import io.github.artfly.ridetogether.server.web.dto.event.UserDto;

import java.math.BigDecimal;

public class ImageDto {
    private String imagePath;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private UserDto creator;

    ImageDto() {
    }

    public ImageDto(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }
}
