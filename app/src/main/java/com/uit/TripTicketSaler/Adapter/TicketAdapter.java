package com.uit.TripTicketSaler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uit.TripTicketSaler.Interface.ICoachListener;
import com.uit.TripTicketSaler.Model.Coach;
import com.uit.TripTicketSaler.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.CarViewHolder> {

    private final ArrayList<Coach> lCoach;
    private final int start;
    private final int end;
    private final int p;
    private final int c;
    private final ICoachListener coachListener;

    public TicketAdapter(ArrayList<Coach> lCoach, int start, int end, int p, int c, ICoachListener coachListener) {
        this.lCoach = lCoach;
        this.start = start;
        this.end = end;
        this.p = p;
        this.c = c;
        this.coachListener = coachListener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new CarViewHolder(view, coachListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Coach tCar = lCoach.get(position);
        if(tCar==null) return;
        int t = Math.abs(end-start);
        holder.duration.setText(t*1.5*tCar.getSpeed() + " tiếng");
        int money = tCar.getPrice() * t;
        money *= (p + c / 2);
        holder.totalCost.setText(money + " kđ");
        holder.feature.setText(tCar.getDetail());
        holder.plate.setText(tCar.getPlate());
        Calendar date = tCar.getStart();
        int addBN = start * 3 * tCar.getSpeed() / 2;
        int addNB = (20 - end) * 3 * tCar.getSpeed() / 2;
        if(tCar.getRouteBN()) date.add(Calendar.HOUR_OF_DAY, addBN);
        else date.add(Calendar.HOUR_OF_DAY, addNB);
        String pickUp = "Đón: " +
                 date.get(Calendar.HOUR_OF_DAY) + "giờ, ngày "
                +date.get(Calendar.DAY_OF_MONTH) + "/"
                +(date.get(Calendar.MONTH)+1) + "/"
                +date.get(Calendar.YEAR);
        holder.timePickUp.setText(pickUp);
        holder.availSeat.setText("còn: " + tCar.getAvailable() + "chỗ");
    }

    @Override
    public int getItemCount() {
        if(lCoach!=null){
            return lCoach.size();
        }
        return 0;
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView timePickUp;
        private final TextView plate;
        private final TextView feature;
        private final TextView totalCost;
        private final TextView duration;
        private final TextView availSeat;
        public final ICoachListener coachListener;

        public CarViewHolder(@NonNull View itemView, ICoachListener coachListener)  {
            super(itemView);
            timePickUp = itemView.findViewById(R.id.timePickUp);
            plate = itemView.findViewById(R.id.plate);
            feature = itemView.findViewById(R.id.feature);
            totalCost = itemView.findViewById(R.id.totalCost);
            duration = itemView.findViewById(R.id.duration);
            availSeat = itemView.findViewById(R.id.availableSeat);
            this.coachListener = coachListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            coachListener.onClickTicket(getBindingAdapterPosition());
        }
    }

}
