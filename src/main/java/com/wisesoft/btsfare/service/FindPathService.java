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
                // สามารถ log หรือแสดงผลข้อมูลเพิ่มเติมได้ที่นี่
                return result;
            }

            List<Route> forwardRoutes = routeRepository.findByStartStation(current).get();
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
        // ถ้าไม่พบเส้นทางที่เหมาะสม สามารถส่งกลับ PathResult ว่างหรือโยน Exception ตามความเหมาะสม
        return new PathResult();
    }
}


// package com.wisesoft.btsfare.service;

// import org.springframework.stereotype.Service;

// import com.wisesoft.btsfare.entity.Route;
// import com.wisesoft.btsfare.entity.Station;
// import com.wisesoft.btsfare.modal.PathResult;
// import com.wisesoft.btsfare.repository.RouteRepository;
// import com.wisesoft.btsfare.repository.StationRepository;
// import java.util.*;

// @Service
// public class FindPathService {

//     private final StationRepository stationRepository;
//     private final RouteRepository routeRepository;
   
    

//     public FindPathService(StationRepository stationRepository, RouteRepository routeRepository) {
//         this.stationRepository = stationRepository;
//         this.routeRepository = routeRepository;
       
//     }
   
//     public PathResult calculateRoute(String startName, String endName) {
//         Optional<Station> start = stationRepository.findByStationName(startName);
//         Optional<Station> end = stationRepository.findByStationName(endName);

//         if (start.get() == null || end.get() == null) {
//             throw new IllegalArgumentException("Invalid station name");
//         }

//         Queue<Station> queue = new LinkedList<>();
//         Map<Station, Integer> distance = new HashMap<>();
//         Map<Station, Integer> time = new HashMap<>();
//         Map<Station, List<Station>> pathStations = new HashMap<>();
//         // LocalTime totalTime = LocalTime.of(0, 0, 0);
//         int totalTime = 0;
//         // เริ่มต้นที่สถานีแรก
//         queue.add(start.get());
//         distance.put(start.get(), 1);
//         time.put(start.get(), totalTime);
//         pathStations.put(start.get(), new ArrayList<>(List.of(start.get())));

//         while (!queue.isEmpty()) {
//             Station current = queue.poll();
//             if (current.equals(end.get())) {
//                 int totalDistance = distance.get(current);
//                 PathResult testDTO = new PathResult();
//                 testDTO.setStation(current);
//                 testDTO.setPath(pathStations.get(current));
//                 testDTO.setDistance(totalDistance);
//                 testDTO.setTime(time.get(current));
//                 List<Station> path = pathStations.get(current);
//                 System.out.println("Total Distance: " + totalDistance);
//                 System.out.println("Path: " + path.stream().map(Station::getStationName).toList());
//                 System.out.println("Total Time: " + time.get(current) + " minutes");
//                 return testDTO;
//             }

//             List<Route> forwardRoutes = routeRepository.findByStartStation(current);
//             for (Route route : forwardRoutes) {
//                 Station neighbor = route.getEndStation();
//                 if (!pathStations.containsKey(neighbor)) {
//                     // totalTime = totalTime.plusHours(route.getTravelTime().getHour())
//                     // .plusMinutes(route.getTravelTime().getMinute())
//                     // .plusSeconds(route.getTravelTime().getSecond());
//                     queue.add(neighbor);
//                     distance.put(neighbor, distance.get(current) + 1);
//                     time.put(neighbor, time.get(current) + route.getTravelTimeMinutes());

//                     List<Station> updatedPath = new ArrayList<>(pathStations.get(current));
//                     updatedPath.add(neighbor);
//                     pathStations.put(neighbor, updatedPath);
//                 }
//             }
//         }
//         return new PathResult();
//     }
// }
