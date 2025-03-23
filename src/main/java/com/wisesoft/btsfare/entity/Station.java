package com.wisesoft.btsfare.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stations")
public class Station {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String stationId;

    @Column(nullable = false)
    private String stationName;

    private boolean isExtension;

    @Column(length = 10)
    private int order;

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "lineId")
    private Line line;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

 

    public String getStationNameThai() {
        return stationNameThai;
    }

    public void setStationNameThai(String stationNameThai) {
        this.stationNameThai = stationNameThai;
    }
    
    
}