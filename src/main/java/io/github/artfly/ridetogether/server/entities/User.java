package io.github.artfly.ridetogether.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

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

//    @Override
//    public String toString() {
//        return Arrays.asList(getClass().getFields())
//                .stream()
//                .map(Field::toString)
//                .collect(Collectors.joining());
//    }
}
