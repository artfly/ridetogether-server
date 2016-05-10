package io.github.artfly.ridetogether.server.web.dto.route;

public class RouteDto {
    private String type;
    private PropertiesDto properties;
    private GeometryDto geometry;

    RouteDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PropertiesDto getProperties() {
        return properties;
    }

    public void setProperties(PropertiesDto properties) {
        this.properties = properties;
    }

    public GeometryDto getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDto geometry) {
        this.geometry = geometry;
    }
}
