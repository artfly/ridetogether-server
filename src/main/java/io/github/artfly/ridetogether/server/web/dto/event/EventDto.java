package io.github.artfly.ridetogether.server.web.dto.event;


import java.util.Set;

public class EventDto {
    private Long id;
    private Long addedAt;
    private Long routeId;
    private String title;
    private Long date;
    private String description;
    private Long creatorId;
    private String imagePath;
    private Set<UserDto> participants;
    private Set<UserDto> subscribers;

    EventDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Long addedAt) {
        this.addedAt = addedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<UserDto> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserDto> participants) {
        this.participants = participants;
    }

    public Set<UserDto> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<UserDto> subscribers) {
        this.subscribers = subscribers;
    }
}
