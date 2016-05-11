package io.github.artfly.ridetogether.server.repository.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToOne
    @JoinColumn
    private User creator;

    @Column(nullable = false)
    private String title;

    private String description;

    private Long date;

    @OneToOne
    @JoinColumn(name = "image_path")
    private Image image;

    private Long addedAt = System.currentTimeMillis() / 1000L;

    @ManyToMany
    @JoinColumn
    private Set<User> participants = new HashSet<>();

    @ManyToMany
    @JoinColumn
    private Set<User> subscribers = new HashSet<>();

    Event() {
    }

    public Event(Set<User> participants, Set<User> subscribers, Long id, Long addedAt) {
        this.participants = participants;
        this.subscribers = subscribers;
        this.id = id;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public Set<User> getParticipants() {
        return participants;
    }

//    public void setSubscribers(Set<User> subscribers) {
//        this.subscribers = subscribers;
//    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(User subscriber) {
        subscribers.add(subscriber);
    }

    public void addParticipant(User participant) {
        participants.add(participant);
    }

    public void removeSubscriberOrParticipant(Long userId) {
        if (!subscribers.removeIf(sub -> sub.getId().equals(userId))) {
            participants.removeIf(sub -> sub.getId().equals(userId));
        }
    }
}
