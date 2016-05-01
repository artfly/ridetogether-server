package io.github.artfly.ridetogether.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Events")
public class Event {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("route_id")
    private Long routeId;

    @JsonProperty("creator_id")
    @OneToOne
    private User creator;

    @JsonProperty("title")

    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("image_path")
    private String imagePath;

    @Column(name = "added_at")
    @NotNull
    private Long addedAt = System.currentTimeMillis() / 1000L;

    Event() {
    }

    public Event(Long routeId, User creator, String title, String description, String imagePath) {
        this.routeId = routeId;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Long getRouteId() {
        return routeId;
    }

    public User getCreator() {
        return creator;
    }

    public void add(User creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public Long getId() {
        return id;
    }
}
