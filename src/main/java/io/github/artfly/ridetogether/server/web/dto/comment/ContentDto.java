package io.github.artfly.ridetogether.server.web.dto.comment;

import io.github.artfly.ridetogether.server.web.dto.route.GeometryDto;

import java.util.List;

public class ContentDto {
    private String text;
    private GeometryDto geometry;
    private List<String> pics;

    public ContentDto() {
    }

    public ContentDto(String text, GeometryDto geometry, List<String> pics) {
        this.text = text;
        this.geometry = geometry;
        this.pics = pics;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GeometryDto getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDto geometry) {
        this.geometry = geometry;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
