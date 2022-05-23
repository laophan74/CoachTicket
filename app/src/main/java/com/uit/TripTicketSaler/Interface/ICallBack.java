package com.uit.TripTicketSaler.Interface;

import com.uit.TripTicketSaler.Model.Coach;

import java.util.ArrayList;

public interface ICallBack {
    void onCallback(ArrayList<Coach> coachList);
}
