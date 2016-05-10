package io.github.artfly.ridetogether.server.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Coordinate {
    @Id
    @GeneratedValue
    @Column(name = "coordinate_id")
    private Long id;

    private String latitude;

    private String longitude;

    Coordinate() {
    }

    Coordinate(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude.toString();
        this.longitude = longitude.toString();
    }
//
//    @JsonIgnore
//    @ManyToOne
//    private Route route;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    BigDecimal getLatitude() {
        return new BigDecimal(latitude);
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude.toString();
    }

    BigDecimal getLongitude() {
        return new BigDecimal(longitude);
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude.toString();
    }
}
