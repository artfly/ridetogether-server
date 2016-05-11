package io.github.artfly.ridetogether.server.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @JsonProperty("image_path")
    @OneToOne
    @JoinColumn(name = "image_path")
    private Image image;

    // Google Places API id
    @Column(nullable = false)
    private String placeId;

    private String routeType;

    private String bikeModel;


    private Long registeredAt = System.currentTimeMillis() / 1000L;

    User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String username, String password, String placeId, String routeType, String bikeModel) {
        this.password = password;
        this.username = username;
        this.placeId = placeId;
        this.routeType = routeType;
        this.bikeModel = bikeModel;
    }

    public Long getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getBikeModel() {
        return bikeModel;
    }

    public void setBikeModel(String bikeModel) {
        this.bikeModel = bikeModel;
    }

    public Long getRegisteredAt() {
        return registeredAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
