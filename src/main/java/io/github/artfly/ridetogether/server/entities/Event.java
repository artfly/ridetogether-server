package io.github.artfly.ridetogether.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("route_id")
    @OneToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @JsonProperty("creator_id")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @Column(nullable = false)
    private String title;

    private String description;

    private Long date;

    @Column(nullable = false)
    private String placeId;

    @JsonProperty("image_path")
    @OneToOne
    @JoinColumn(name = "image_path")
    private Image image;

    private Long addedAt = System.currentTimeMillis() / 1000L;

    @JsonProperty("participants")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<User> participants;

    @JsonProperty("subscribers")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<User> subscribers;

    Event() {
    }

    public Event(String title, String description, Long date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getRouteId() {
        return route.getId();
    }

    public Long getCreatorId() {
        return creator.getId();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getDate() {
        return date;
    }

    public String getImagePath() {
        return image.getImagePath();
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setAddedAt(Long addedAt) {
        this.addedAt = addedAt;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
