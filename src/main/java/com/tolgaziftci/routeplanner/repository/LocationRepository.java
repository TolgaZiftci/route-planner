package com.tolgaziftci.routeplanner.repository;

import com.tolgaziftci.routeplanner.dao.LocationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationDao, Integer> {
    LocationDao findByName(String name);
}
