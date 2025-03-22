package com.wisesoft.btsfare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wisesoft.btsfare.entity.BtsFare;

public interface FareRepository extends JpaRepository<BtsFare, Long> {
    Optional<BtsFare> findByStationNumber(int stationNumber);

    @Query("SELECT f FROM BtsFare f ORDER BY f.stationNumber ASC")
    Optional<List<BtsFare>> findAllOrderByNumberStation();
    
    @Query("SELECT MAX(f.stationNumber) FROM BtsFare f")
    Optional<Integer> findMaxStationsNumber();
}
