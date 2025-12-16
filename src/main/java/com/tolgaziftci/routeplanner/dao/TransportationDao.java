package com.tolgaziftci.routeplanner.dao;

import com.tolgaziftci.routeplanner.entity.TransportationType;
import io.hypersistence.utils.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity(name = "transportation")
@Table(name = "transportations")
public class TransportationDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private LocationDao originLocation;

    @ManyToOne
    @JoinColumn(name = "dest_id", referencedColumnName = "id")
    private LocationDao destLocation;

    @Enumerated(EnumType.ORDINAL)
    private TransportationType type;

    @Type(IntArrayType.class)
    @Column(columnDefinition = "integer[]")
    private int[] operatingDays;

    public TransportationDao() {
    }

    public TransportationDao(int id, LocationDao originLocation, LocationDao destLocation, TransportationType type, int[] operatingDays) {
        this.id = id;
        this.originLocation = originLocation;
        this.destLocation = destLocation;
        this.type = type;
        this.operatingDays = operatingDays;
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

    public int[] getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(int[] operatingDays) {
        this.operatingDays = operatingDays;
    }
}
