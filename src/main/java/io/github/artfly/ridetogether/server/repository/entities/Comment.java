package io.github.artfly.ridetogether.server.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "creator_id", updatable = false)
    private User creator;

    @Column(nullable = false)
    private Integer rating;

    @JsonIgnore
    private Long addedAt = System.currentTimeMillis() / 1000L;

    @Column(nullable = false)
    private String text;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Coordinate> coordinates;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Image> pics;

    @OneToOne
    @JoinColumn(name = "route_id", updatable = false)
    private Route route;

    Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Long addedAt) {
        this.addedAt = addedAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<List<BigDecimal>> getCoordinates() {
        return coordinates.stream()
                .map(coordinate -> Arrays.asList(coordinate.getLatitude(), coordinate.getLongitude()))
                .collect(Collectors.toList());
    }

    public List<Coordinate> getDbCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<BigDecimal>> coordinates) {
        this.coordinates = coordinates.stream()
                .map(decimals -> new Coordinate(decimals.get(0), decimals.get(1)))
                .collect(Collectors.toList());
    }

    public List<String> getPicsPaths() {
        return pics.stream()
                .map(Image::getImagePath)
                .collect(Collectors.toList());
    }

    public void setPics(List<Image> pics) {
        this.pics = pics;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
