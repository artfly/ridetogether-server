package io.github.artfly.ridetogether.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    @Column(name = "comment_id")
    private Long id;

    @JsonProperty("content")
    @NotNull
    private String content;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images;

    @JsonProperty("creator_id")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @JsonProperty("added_at")
    private Long addedAt = System.currentTimeMillis() / 1000L;

    @JsonProperty("likes")
    @NotNull
    private Integer likes;

    @JsonProperty("dislikes")
    @NotNull
    private Integer dislikes;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
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
