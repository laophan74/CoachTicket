package com.uit.TripTicketSaler.Model;

public class AppUser {
    private String username = "";
    private String cmnd = "";
    private String phoneNum = "";

    public AppUser() {
    }

    public AppUser(String username, String cmnd, String phoneNum) {
        this.username = username;
        this.cmnd = cmnd;
        this.phoneNum = phoneNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
