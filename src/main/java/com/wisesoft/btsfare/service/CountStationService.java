package com.wisesoft.btsfare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.entity.Station;

@Service
public class CountStationService {
    
    public int countFareStations(List<Station> path) {
        int count = 0;
        int extensionCount = 0;
        for (Station station : path) {
            if (station.isExtension()) {
                if (extensionCount == 0) {
                    count++;
                }
                extensionCount++;
            } else {
                count++;
                extensionCount = 0;
            }
        }
        // ลบ 1 เนื่องจากเริ่มต้นนับที่สถานีแรก (หรือปรับตาม business rule)
        return Math.max(0, count - 1);
    }
    
    // public int countFareStations(List<Station> path) {
    //     int count = 0, extensionCount = 0;
    //     for (Station station : path) {
    //         if (station.isExtension()) {
    //             if (extensionCount == 0) 
    //             count++;
    //             extensionCount++;
    //         } else {
    //             count++;
    //             extensionCount = 0;
    //         }
    //     }
    //     return Math.max(0, count - 1);
    // }
}
