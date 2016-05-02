package io.github.artfly.ridetogether.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Coordinates")
public class Coordinate {
    @Id
    @GeneratedValue
    @Column(name = "coordinate_id")
    private Long coordinateId;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @JsonIgnore
    @ManyToOne
    private Route route;

    Coordinate() {
    }

    public Coordinate(String latitude, String longitude) {
        this.latitude = new BigDecimal(latitude);
        this.longitude = new BigDecimal(longitude);
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
