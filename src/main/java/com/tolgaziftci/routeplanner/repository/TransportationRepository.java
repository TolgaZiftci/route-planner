package com.tolgaziftci.routeplanner.repository;

import com.tolgaziftci.routeplanner.dao.TransportationDao;
import com.tolgaziftci.routeplanner.dao.TransportationSummary;
import com.tolgaziftci.routeplanner.entity.TransportationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportationRepository extends JpaRepository<TransportationDao, Integer> {

    @Query("""
            select new com.tolgaziftci.routeplanner.dao.TransportationSummary(
                    t.id,
                    t.originLocation.id,
                    t.destLocation.id,
                    t.type,
                    t.operatingDays
                )
                from transportation t
                where t.originLocation.id = :originLocation
                  and t.destLocation.id = :destLocation
            """)
    List<TransportationSummary> findKeysByOriginLocation_IdAndDestLocation_Id(int originLocation, int destLocation);

    @Query("""
            select new com.tolgaziftci.routeplanner.dao.TransportationSummary(
                    t.id,
                    t.originLocation.id,
                    t.destLocation.id,
                    t.type,
                    t.operatingDays
                )
                from transportation t
                where t.originLocation.id = :originLocation
            """)
    List<TransportationSummary> findKeysByOriginLocation_Id(int originLocation);

    Optional<TransportationDao> findByOriginLocation_IdAndDestLocation_IdAndType(int originLocation, int destLocation, TransportationType type);

    boolean existsByOriginLocation_IdAndDestLocation_IdAndType(int originLocation, int destLocation, TransportationType type);
}
