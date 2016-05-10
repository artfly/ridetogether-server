package io.github.artfly.ridetogether.server.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.artfly.ridetogether.server.utils.RouteConvertor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Routes")
//@JsonSerialize(using = RouteConvertor.Serializer.class)
//@JsonDeserialize(using = RouteConvertor.Deserializer.class)
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
    private Double rating;

    @Column(nullable = false)
    private String placeId;

    private Long addedAt = System.currentTimeMillis() / 1000L;

    @JsonProperty("creator_id")
    @OneToOne
    @JoinColumn(name = "creator_id")
    private User creator;

//    @OneToMany(mappedBy = "route")
    @OneToMany
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

    public void setId(Long id) {
        this.id = id;
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

    public List<List<BigDecimal>> getCoordinates() {
        return coordinates.stream()
                .map(coordinate -> Arrays.asList(coordinate.getLatitude(), coordinate.getLongitude()))
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

    public void setCoordinates(List<List<BigDecimal>> coordinates) {
        this.coordinates = coordinates.stream()
                .map(decimals -> new Coordinate(decimals.get(0), decimals.get(1)))
                .collect(Collectors.toList());
    }
}
