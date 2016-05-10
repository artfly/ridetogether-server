package io.github.artfly.ridetogether.server.web.dto.comment;

import io.github.artfly.ridetogether.server.web.dto.event.UserDto;

public class CommentDto {
    private io.github.artfly.ridetogether.server.web.dto.event.UserDto creator;
    private Long id;
    private Integer rating;
    private Long addedAt;
    private ContentDto content;

    public CommentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
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

    public ContentDto getContent() {
        return content;
    }

    public void setContent(ContentDto content) {
        this.content = content;
    }
}
