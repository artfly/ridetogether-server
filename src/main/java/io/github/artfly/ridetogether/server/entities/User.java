package io.github.artfly.ridetogether.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @JsonProperty("image_path")
    @OneToOne
    @JoinColumn(name = "image_path")
    private Image image;

    // Google Places API id
    @JsonProperty("place_id")
    @NotNull
    private String placeId;

    @JsonProperty("route_type")
    private String routeType;

    @JsonProperty("bike_model")
    private String bikeModel;

    @JsonProperty("registered_at")
    private Long registeredAt = System.currentTimeMillis() / 1000L;

    User () {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image.getImagePath();
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getBikeModel() {
        return bikeModel;
    }

    public Long getRegisteredAt() {
        return registeredAt;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public void setBikeModel(String bikeModel) {
        this.bikeModel = bikeModel;
    }
}
