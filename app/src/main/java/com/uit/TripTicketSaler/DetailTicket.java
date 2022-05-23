package com.uit.TripTicketSaler;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uit.TripTicketSaler.Model.Coach;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailTicket extends Fragment {
    private LinearLayout linearSeat;
    private ViewGroup groupSeats;
    private ImageView btnBack;
    private TextView tvStart, tvEnd, tvTime, timePickUp, plate, feature, totalCost, duration;
    private View cardDetail;
    private int numCus, numChild, totalN;
    private String desStart, desEnd;

    String dataSeats;
    ArrayList<Integer> selected = new ArrayList<>();
    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;

    private NavController navController;

    private ArrayList<TextView> seatTexViewList = new ArrayList<>();
    private int seatSize = 150;
    private int seatGaping = 10;

    public DetailTicket() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_detail_ticket, container, false);

        groupSeats = v.findViewById(R.id.groupSeats);
        btnBack = v.findViewById(R.id.backPress2);
        cardDetail = v.findViewById(R.id.carDetail);
        timePickUp = cardDetail.findViewById(R.id.timePickUp);
        plate = cardDetail.findViewById(R.id.plate);
        feature = cardDetail.findViewById(R.id.feature);
        totalCost = cardDetail.findViewById(R.id.totalCost);
        duration = cardDetail.findViewById(R.id.duration);
        tvStart = v.findViewById(R.id.txtStart);
        tvEnd = v.findViewById(R.id.txtEnd);

        assert getArguments() != null;
        desStart = getArguments().getString("pickUp");
        desEnd = getArguments().getString("dest");
        int start = getArguments().getInt("start");
        int end = getArguments().getInt("end");
        numCus = getArguments().getInt("adult");
        numChild = getArguments().getInt("child");
        Coach lCoach = (Coach) getArguments().getSerializable("coach");
        totalN = numChild + numCus;

        tvStart.setText(desStart);
        tvEnd.setText(desEnd);

        NavHostFragment hostFragment = (NavHostFragment) getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = hostFragment.getNavController();
        LoadCardView(lCoach, start, end, numCus, numChild);

        dataSeats = "/" + dataSeats;

        linearSeat = new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearSeat.setOrientation(LinearLayout.VERTICAL);
        linearSeat.setLayoutParams(params);
        linearSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        Load3RowSeat();
        groupSeats.addView(linearSeat);
        btnBack.setOnClickListener(view -> {
            BackPressClick();
        });

        return v;
    }

    private void Load3RowSeat(){
        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < dataSeats.length(); index++) {
            if (dataSeats.charAt(index) == '/') {
                layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setGravity(Gravity.CENTER);
                linearSeat.addView(layout);
            } else if (dataSeats.charAt(index) == 'U') {
                TextView tv = new TextView(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                tv.setLayoutParams(layoutParams);
                tv.setPadding(0, 0, 0, 2 * seatGaping);
                tv.setId(count);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.booked_img);
                tv.setTextColor(Color.WHITE);
                tv.setTag(STATUS_BOOKED);
                tv.setText(ReturnSeatNum(count));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                layout.addView(tv);
                seatTexViewList.add(tv);
                tv.setOnClickListener(this::SelectSeat);
                count++;
            } else if (dataSeats.charAt(index) == 'A') {
                TextView tv = new TextView(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                tv.setLayoutParams(layoutParams);
                tv.setPadding(0, 0, 0, 2 * seatGaping);
                tv.setId(count);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.available_img);
                tv.setText(ReturnSeatNum(count));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                tv.setTextColor(Color.BLACK);
                tv.setTag(STATUS_AVAILABLE);
                layout.addView(tv);
                seatTexViewList.add(tv);
                tv.setOnClickListener(this::SelectSeat);
                count++;
            }else if (dataSeats.charAt(index) == '_') {
                TextView tv = new TextView(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                tv.setLayoutParams(layoutParams);
                tv.setBackgroundColor(Color.TRANSPARENT);
                tv.setText("");
                layout.addView(tv);
            }
        }
    }

    private StringBuilder ReturnSeatNum(int count){
        StringBuilder builder = new StringBuilder("");
        if(count%3==0) builder.append("A");
        else if(count%3==1) builder.append("B");
        else builder.append("C");
        int numS = (count / 3) + 1;
        builder.append(numS);
        return builder;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                BackPressClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void LoadCardView(Coach coach, int s, int e, int p, int c){
        int t = Math.abs(s-e);
        duration.setText(t*1.5*coach.getSpeed() + " tiếng");
        int money = coach.getPrice() * t;
        money *= (p + c / 2);
        totalCost.setText(money + " kđ");
        feature.setText(coach.getDetail());
        plate.setText(coach.getPlate());
        Calendar date = coach.getStart();
        int addBN = s * 3 * coach.getSpeed() / 2;
        int addNB = (20 - e) * 3 * coach.getSpeed() / 2;
        if(coach.getRouteBN()) date.add(Calendar.HOUR_OF_DAY, addBN);
        else date.add(Calendar.HOUR_OF_DAY, addNB);
        String pickUp = "Đón: " +
                date.get(Calendar.HOUR_OF_DAY) + "giờ, ngày "
                +date.get(Calendar.DAY_OF_MONTH) + "/"
                +(date.get(Calendar.MONTH)+1) + "/"
                +date.get(Calendar.YEAR);
        timePickUp.setText(pickUp);

        ArrayList<Boolean> arrSeat = coach.getSeat();
        for(int i = 0; i < arrSeat.size(); i++){
            if(arrSeat.get(i)) dataSeats += "U";
            else dataSeats += "A";
            if(i%3!=2) dataSeats += "_";
            else dataSeats += "/";
        }
    }

    private void SelectSeat(View view){
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selected.contains(view.getId())) {
                selected.remove((Integer) view.getId());
                view.setBackgroundResource(R.drawable.available_img);
            } else {
                if(selected.size()>=totalN){
                    Toast.makeText(getActivity(), "Bạn đang chọn quá số chỗ!", Toast.LENGTH_SHORT).show();
                }
                else{
                    selected.add(view.getId());
                    view.setBackgroundResource(R.drawable.your_seat_img);
                }
            }
        } else {
            Toast.makeText(getActivity(), "Chỗ ngồi đã được đặt", Toast.LENGTH_SHORT).show();
        }


    }

    private void BackPressClick(){
        navController.navigate(R.id.action_detailTicket_to_listCoach);
    }
}