package io.github.artfly.ridetogether.server.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "Images")
public class Image {
    @Id
    @JsonProperty("image_path")
    @Column(name = "image_path")
    private String id;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    Image() {
    }

    public Image(String imagePath) {
        this.id = imagePath;
    }

    public Image(String imagePath, BigDecimal latitude, BigDecimal longitude) {
        this.id = imagePath;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public String getImagePath() {
        return id;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public void setImagePath(String imagePath) {
        this.id = imagePath;
    }
}
