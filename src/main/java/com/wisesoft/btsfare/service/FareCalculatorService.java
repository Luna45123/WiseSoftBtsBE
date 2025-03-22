// package com.wisesoft.btsfare.service;


// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.wisesoft.btsfare.entity.BtsFare;
// import com.wisesoft.btsfare.entity.Station;
// import com.wisesoft.btsfare.repository.FareRepository;
// import com.wisesoft.btsfare.repository.StationRepository;

// @Service
// public class FareCalculatorService {
//     @Autowired
//     private StationRepository stationRepository;

//     @Autowired
//     private FareRepository fareRepository;

//     public int calculateStationCount(String startStationName, String endStationName) {
//         Station start = stationRepository.findByStationName(startStationName);
//         Station end = stationRepository.findByStationName(endStationName);
       
//         System.out.println("End station: " + end);
//         List<Station> exStation = stationRepository.findByExtensionStation(true);
//         List<Station> notExStation = stationRepository.findByExtensionStation(false);
//         if (start.getLine().getLineId().equals(end.getLine().getLineId())) {
//             if (exStation.contains(end)) {
//                 return Math.abs(start.getStationOrder() - notExStation.getFirst().getStationOrder()+1);
//             }
//             return Math.abs(start.getStationOrder() - end.getStationOrder());
//         } else {
//             Station interchange = stationRepository.findByStationName("สยาม");
//             int countToInterchange = Math.abs(start.getStationOrder() - interchange.getStationOrder());
//             int countFromInterchange = Math.abs(interchange.getStationOrder() - end.getStationOrder());
//             return countToInterchange + countFromInterchange;
//         }
//     }

//     public double calculateFare(String startStation, String endStation) {
//         int stationCount = calculateStationCount(startStation, endStation);
//         System.out.println("Calculated Station Count = " + stationCount);
//         BtsFare fare = null;
//         if (stationCount < 8) {
//             fare = fareRepository.findByStationNumber(stationCount);
//         }else {
//             fare = fareRepository.findByStationNumber(8);
//         }
        
    
//         if (fare == null) {
//             throw new RuntimeException("Fare not found for station number = " + stationCount);
//         }
    
//         System.out.println("Fares: " + fare.getFare() + " for " + stationCount + " stations");
//         Station end = stationRepository.findByStationName(endStation);
//         List<Station> exStation = stationRepository.findByExtensionStation(true);
//         if (end.isExtensionStation() && !end.getStationName().equals(exStation.getLast().getStationName())) {
//             return fare.getFare() + 15;
//         }
//         return fare.getFare();
//     }
// }