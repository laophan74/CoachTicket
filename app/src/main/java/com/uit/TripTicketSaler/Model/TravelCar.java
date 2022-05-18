package com.uit.TripTicketSaler.Model;

import java.util.ArrayList;
import java.util.Calendar;

public class TravelCar {
    private String detail;
    private int duration;
    private String plate;
    private String price;
    private Boolean routeBN;
    private ArrayList<Boolean> seat;
    private ArrayList<Boolean> seat2nd;
    private Calendar start;

    public TravelCar() {}

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }
}
