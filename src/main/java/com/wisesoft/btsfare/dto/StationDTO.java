package com.wisesoft.btsfare.dto;

public class StationDTO {

    private String stationId;

    private String stationName;

    private boolean isExtension;

    private LineDTO line;

    private String stationNameThai;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public boolean isExtension() {
        return isExtension;
    }

    public void setExtension(boolean isExtension) {
        this.isExtension = isExtension;
    }

    public LineDTO getLine() {
        return line;
    }

    public void setLine(LineDTO line) {
        this.line = line;
    }

    public String getStationNameThai() {
        return stationNameThai;
    }

    public void setStationNameThai(String stationNameThai) {
        this.stationNameThai = stationNameThai;
    }

    

    
}
