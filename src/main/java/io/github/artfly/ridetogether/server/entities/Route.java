package io.github.artfly.ridetogether.server.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.artfly.ridetogether.server.convertors.RouteConvertor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Routes")
@JsonSerialize(using = RouteConvertor.Serializer.class)
@JsonDeserialize(using = RouteConvertor.Deserializer.class)
public class Route {
    @Id
    @GeneratedValue
    @Column(name = "route_id")
    private Long id;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private Double rating;

    private Long addedAt = System.currentTimeMillis() / 1000L;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "route")
    private List<Coordinate> coordinates = new ArrayList<>();

    private String routeType;

    public Route() {
    }

    public Route(String routeType, Double rating, String description, String title, User creator) {
        this.routeType = routeType;
        this.rating = rating;
        this.description = description;
        this.title = title;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getRating() {
        return rating;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public User getCreator() {
        return creator;
    }

    public String getRouteType() {
        return routeType;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setAddedAt(Long addedAt) {
        this.addedAt = addedAt;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public void addCoordinate(Coordinate coordinate) {
        coordinates.add(coordinate);
    }
}
