package com.wisesoft.btsfare.modal;

import java.util.List;

import com.wisesoft.btsfare.entity.Station;

public class PathResult {
    private Station station;

    private List<Station> path;

    private int distance;

    private int time;

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Station> getPath() {
        return path;
    }

    public void setPath(List<Station> path) {
        this.path = path;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    
}
