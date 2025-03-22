package com.wisesoft.btsfare.modal;

import java.util.List;

public class PriceResultBTS {
    
    private double single;

    private double adult;

    private double student;

    private double senior;

    private List<String> path;

    private int distance;

    private int time;

    private String start;

    private String end;

    private String startNameThai;

    private String endNameThai;

    public double getAdult() {
        return adult;
    }

    public void setAdult(double adult) {
        this.adult = adult;
    }

    public double getStudent() {
        return student;
    }

    public void setStudent(double student) {
        this.student = student;
    }

    public double getSenior() {
        return senior;
    }

    public void setSenior(double senior) {
        this.senior = senior;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public double getSingle() {
        return single;
    }

    public void setSingle(double single) {
        this.single = single;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartNameThai() {
        return startNameThai;
    }

    public void setStartNameThai(String startNameThai) {
        this.startNameThai = startNameThai;
    }

    public String getEndNameThai() {
        return endNameThai;
    }

    public void setEndNameThai(String endNameThai) {
        this.endNameThai = endNameThai;
    }

    
    
}
