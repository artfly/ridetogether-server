package io.github.artfly.ridetogether.server.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Image {
    @Id
    @JsonProperty("image_path")
    @Column(name = "image_path")
    private String id;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @OneToOne
    @JoinColumn
    private User creator;

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

    String getImagePath() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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
