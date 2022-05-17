package com.uit.TripTicketSaler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uit.TripTicketSaler.Adapter.TicketAdapter;

public class ListCoach extends Fragment {

    private RecyclerView rcvCar;
    private TicketAdapter carAdapter;
    private TextView tvStartPoint;
    private TextView tvEndpoint;
    private TextView tvAfterD;

    public ListCoach() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_coach, container, false);

        tvAfterD = v.findViewById(R.id.afterDay);
        tvEndpoint = v.findViewById(R.id.endPoint);
        tvStartPoint = v.findViewById(R.id.startPoint);
        rcvCar = v.findViewById(R.id.rcvCar);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcvCar.setLayoutManager(llm);

        RecyclerView.ItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvCar.addItemDecoration(decor);

        return v;
    }
}