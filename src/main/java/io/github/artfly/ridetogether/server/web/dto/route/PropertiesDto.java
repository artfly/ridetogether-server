package io.github.artfly.ridetogether.server.web.dto.route;


public class PropertiesDto {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long creatorId;
    private String routeType;
    private Long addedAt;
    private String placeId;

    public PropertiesDto() {
    }

    public PropertiesDto(Long id, String title, String description, Double rating, Long creatorId, String routeType, Long addedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.creatorId = creatorId;
        this.routeType = routeType;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
