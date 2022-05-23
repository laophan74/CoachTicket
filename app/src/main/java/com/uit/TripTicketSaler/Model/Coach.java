package com.uit.TripTicketSaler.Model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Coach implements Serializable {
    private String detail;
    private int duration;
    private String plate;
    private int price;
    private Boolean routeBN;
    private ArrayList<Boolean> seat;
    private ArrayList<Boolean> seat2nd;
    private Timestamp start;
    private int available;
    private int speed;

    public Coach() {}

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getRouteBN() {
        return routeBN;
    }

    public void setRouteBN(Boolean routeBN) {
        this.routeBN = routeBN;
    }

    public ArrayList<Boolean> getSeat() {
        return seat;
    }

    public void setSeat(ArrayList<Boolean> seat) {
        this.seat = seat;
    }

    public ArrayList<Boolean> getSeat2nd() {
        return seat2nd;
    }

    public void setSeat2nd(ArrayList<Boolean> seat2nd) {
        this.seat2nd = seat2nd;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Calendar getStart() {
        Date d = start.toDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }
}
