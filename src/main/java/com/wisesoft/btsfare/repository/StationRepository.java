package com.wisesoft.btsfare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wisesoft.btsfare.entity.Station;


public interface StationRepository extends JpaRepository<Station, String> {

    Optional<Station> findByStationName(String startName);
    @Query("SELECT s FROM Station s WHERE s.line.lineName = :lineName ORDER BY s.order ASC")
    Optional<List<Station>> findByLineName(@Param("lineName") String lineName);
}