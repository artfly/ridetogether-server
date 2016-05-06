package io.github.artfly.ridetogether.server.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;


    @Column(nullable = false)
    private String text;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images;

    @JsonProperty("creator_id")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User creator;

    private Long addedAt = System.currentTimeMillis() / 1000L;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    private Integer dislikes;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Image> getImages() {
        return images;
    }

    public User getCreator() {
        return creator;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }
}
