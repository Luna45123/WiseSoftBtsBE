package com.wisesoft.btsfare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.entity.Station;

@Service
public class CountStationService {
    
    public int countFareStations(List<Station> path) {
        int count = 0;
        boolean isOneExtensionCount = true;
        for (Station station : path) {
            if (station.isExtension()) {
                if (isOneExtensionCount) {
                    count++;
                }
                isOneExtensionCount = false;
            } else {
                count++;
                isOneExtensionCount = true;
            }
        }
        return Math.max(0, count - 1);
    }
}
