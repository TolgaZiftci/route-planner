package com.tolgaziftci.routeplanner.dao;

import com.tolgaziftci.routeplanner.entity.TransportationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "transportation")
@Table(name = "transportations")
public class TransportationDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "origin-id", referencedColumnName = "id")
    private LocationDao originLocation;

    @OneToOne
    @JoinColumn(name = "dest-id", referencedColumnName = "id")
    private LocationDao destLocation;

    @Enumerated(EnumType.ORDINAL)
    private TransportationType type;

    public TransportationDao() {
    }

    public TransportationDao(int id, LocationDao originLocation, LocationDao destLocation, TransportationType type) {
        this.id = id;
        this.originLocation = originLocation;
        this.destLocation = destLocation;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocationDao getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(LocationDao originLocation) {
        this.originLocation = originLocation;
    }

    public LocationDao getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(LocationDao destLocation) {
        this.destLocation = destLocation;
    }

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }
}
