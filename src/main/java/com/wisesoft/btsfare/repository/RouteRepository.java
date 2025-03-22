package com.wisesoft.btsfare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisesoft.btsfare.entity.Route;
import com.wisesoft.btsfare.entity.Station;




public interface RouteRepository extends JpaRepository<Route, Long> {
    // List<Route> findByStartStation_StationId(String startStationId);

    Optional<List<Route>> findByStartStation(Station current);

    Optional<List<Route>> findByEndStation(Station current);

  
}
