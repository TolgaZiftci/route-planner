package com.tolgaziftci.routeplanner.repository;

import com.tolgaziftci.routeplanner.dao.TransportationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<TransportationDao, Integer> {
    List<TransportationDao> findByOriginLocation_IdAndDestLocation_Id(int originLocation, int destLocation);

    List<TransportationDao> findByOriginLocation_Id(int originLocation);

    List<TransportationDao> findByDestLocation_Id(int destLocation);
}
