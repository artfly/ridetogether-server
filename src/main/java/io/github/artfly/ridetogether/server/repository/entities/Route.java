package io.github.artfly.ridetogether.server.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Routes")
public class Route {
    @Id
    @GeneratedValue
    @Column(name = "route_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double rating = 0.0;

    @Column(nullable = false)
    private String placeId;

    private Long addedAt = System.currentTimeMillis() / 1000L;

    @JsonProperty("creator_id")
    @OneToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany
    private List<Coordinate> coordinates = new ArrayList<>();

    private String routeType;

    public Route() {
    }

    public Route(String routeType, String description, String title, User creator) {
        this.routeType = routeType;
        this.description = description;
        this.title = title;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Long addedAt) {
        this.addedAt = addedAt;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public List<List<BigDecimal>> getCoordinates() {
        return coordinates.stream()
                .map(coordinate -> Arrays.asList(coordinate.getLatitude(), coordinate.getLongitude()))
                .collect(Collectors.toList());
    }

    public void setCoordinates(List<List<BigDecimal>> coordinates) {
        this.coordinates = coordinates.stream()
                .map(decimals -> new Coordinate(decimals.get(0), decimals.get(1)))
                .collect(Collectors.toList());
    }

    public List<Coordinate> getDbCoordinates() {
        return coordinates;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void addCoordinate(Coordinate coordinate) {
        coordinates.add(coordinate);
    }
}
