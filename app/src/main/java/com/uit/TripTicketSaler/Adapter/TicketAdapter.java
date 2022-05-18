package com.uit.TripTicketSaler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.uit.TripTicketSaler.Model.TravelCar;
import com.uit.TripTicketSaler.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.CarViewHolder> {

    private ArrayList<TravelCar> lTravelCars;
    private int start;
    private int end;

    public ArrayList<TravelCar> getlTravelCars() {
        return lTravelCars;
    }

    public void setlTravelCars(ArrayList<TravelCar> lTravelCars) {
        this.lTravelCars = lTravelCars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        TravelCar tCar = lTravelCars.get(position);
        if(tCar==null) return;
        int t = Math.abs(end-start);
        holder.duration.setText( t*1.5 + " tiếng");
        holder.totalCost.setText("$" + t*50);
        holder.feature.setText(tCar.getDetail());
        holder.plate.setText(tCar.getPlate());
        Calendar date = tCar.getStart();
        if(tCar.getRouteBN()) date.add(Calendar.HOUR_OF_DAY, (int) (start*1.5));
        else date.add(Calendar.HOUR_OF_DAY, (int) ((20-end)*1.5));
        String pickUp = date.toString();
        holder.timePickUp.setText("Đón: " + pickUp);
    }

    @Override
    public int getItemCount() {
        if(lTravelCars!=null){
            return lTravelCars.size();
        }
        return 0;
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        private TextView timePickUp;
        private TextView plate;
        private TextView feature;
        private TextView totalCost;
        private TextView duration;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            timePickUp = itemView.findViewById(R.id.timePickUp);
            plate = itemView.findViewById(R.id.plate);
            feature = itemView.findViewById(R.id.feature);
            totalCost = itemView.findViewById(R.id.totalCost);
            duration = itemView.findViewById(R.id.duration);
        }
    }

}
