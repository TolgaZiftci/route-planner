package com.tolgaziftci.routeplanner.repository;

import com.tolgaziftci.routeplanner.dao.TransportationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationRepository extends JpaRepository<TransportationDao, Integer> {
}
