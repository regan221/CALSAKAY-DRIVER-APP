package com.example.calsakay_driver;

import java.io.Serializable;
import java.util.Date;

public class Histories implements Serializable {

    private int id;
    private int frontliner_id;
    private int driver_id;
    private Date ride_time_start;
    private Date ride_time_end;
    private String pickup;
    private String drop;
    private int seat_num;
    private int status;


    public Histories(int id, int frontliner_id, int driver_id, Date ride_time_start, Date ride_time_end, String pickup, String drop, int seat_num, int status) {
        this.id = id;
        this.frontliner_id = frontliner_id;
        this.driver_id = driver_id;
        this.ride_time_start = ride_time_start;
        this.ride_time_end = ride_time_end;
        this.pickup = pickup;
        this.drop = drop;
        this.seat_num = seat_num;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Histories{" +
                "id=" + id +
                ", frontliner_id=" + frontliner_id +
                ", driver_id=" + driver_id +
                ", ride_time_start='" + ride_time_start + '\'' +
                ", ride_time_end='" + ride_time_end + '\'' +
                ", pickup='" + pickup + '\'' +
                ", drop='" + drop + '\'' +
                ", seat_num=" + seat_num +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrontliner_id() {
        return frontliner_id;
    }

    public void setFrontliner_id(int frontliner_id) {
        this.frontliner_id = frontliner_id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public Date getRide_time_start() {
        return ride_time_start;
    }

    public void setRide_time_start(Date ride_time_start) {
        this.ride_time_start = ride_time_start;
    }

    public Date getRide_time_end() {
        return ride_time_end;
    }

    public void setRide_time_end(Date ride_time_end) {
        this.ride_time_end = ride_time_end;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }





}
