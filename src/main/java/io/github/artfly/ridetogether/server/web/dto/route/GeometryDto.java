package io.github.artfly.ridetogether.server.web.dto.route;

import java.math.BigDecimal;
import java.util.List;

public class GeometryDto {
    private String type;
    private List<List<BigDecimal>> coordinates;

    public GeometryDto() {
    }

    public GeometryDto(String type, List<List<BigDecimal>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<BigDecimal>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<BigDecimal>> coordinates) {
        this.coordinates = coordinates;
    }
}
