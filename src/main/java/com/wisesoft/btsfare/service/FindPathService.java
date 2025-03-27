package com.wisesoft.btsfare.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.entity.Route;
import com.wisesoft.btsfare.entity.Station;
import com.wisesoft.btsfare.modal.PathResult;
import com.wisesoft.btsfare.repository.RouteRepository;
import com.wisesoft.btsfare.repository.StationRepository;

@Service
public class FindPathService {

    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;

    public FindPathService(StationRepository stationRepository, RouteRepository routeRepository) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
    }

    public PathResult calculateRoute(String startName, String endName) {
        Station start = stationRepository.findByStationName(startName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid start station: " + startName));
        Station end = stationRepository.findByStationName(endName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid end station: " + endName));

        List<Route> allRoutes = routeRepository.findAll();
        Map<Station, List<Route>> routeMap = new HashMap<>();
        for (Route route : allRoutes) {
            Station from = route.getStartStation();
            routeMap.computeIfAbsent(from, k -> new ArrayList<>()).add(route);
        }

        Queue<Station> queue = new LinkedList<>();
        Map<Station, Integer> distance = new HashMap<>();
        Map<Station, Integer> time = new HashMap<>();
        Map<Station, List<Station>> pathStations = new HashMap<>();
        int totalTime = 0;

        queue.add(start);
        distance.put(start, 1);
        time.put(start, totalTime);
        pathStations.put(start, new ArrayList<>(List.of(start)));

        while (!queue.isEmpty()) {
            Station current = queue.poll();
            if (current.equals(end)) {
                PathResult result = new PathResult();
                result.setStation(current);
                result.setPath(pathStations.get(current));
                result.setDistance(distance.get(current));
                result.setTime(time.get(current));
                return result;
            }

            List<Route> forwardRoutes = routeMap.getOrDefault(current, new ArrayList<>());
            for (Route route : forwardRoutes) {
                Station neighbor = route.getEndStation();
                if (!pathStations.containsKey(neighbor)) {
                    queue.add(neighbor);
                    distance.put(neighbor, distance.get(current) + 1);
                    time.put(neighbor, time.get(current) + route.getTravelTimeMinutes());

                    List<Station> updatedPath = new ArrayList<>(pathStations.get(current));
                    updatedPath.add(neighbor);
                    pathStations.put(neighbor, updatedPath);
                }
            }
        }

        // ไม่พบเส้นทาง
        return new PathResult();
    }
}
