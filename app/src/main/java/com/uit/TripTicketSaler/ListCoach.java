package com.uit.TripTicketSaler;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.uit.TripTicketSaler.Adapter.TicketAdapter;
import com.uit.TripTicketSaler.Model.Coach;

import java.util.ArrayList;

public class ListCoach extends Fragment {

    private RecyclerView rcvCar;
    private TicketAdapter ticketAdapter;
    private TextView tvStartPoint;
    private TextView tvEndpoint;
    private TextView tvAfterD;
    private ImageView btnBackP;

    private NavController navController;
    private int start;
    private int end;
    private int numCus;
    private int numChild;

    public ListCoach() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_coach, container, false);

        NavHostFragment hostFragment = (NavHostFragment) getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = hostFragment.getNavController();

        ArrayList<Coach> lCoach = (ArrayList<Coach>) getArguments().getSerializable("Coaches");

        tvAfterD = v.findViewById(R.id.afterDay);
        tvEndpoint = v.findViewById(R.id.endPoint);
        tvStartPoint = v.findViewById(R.id.startPoint);
        rcvCar = v.findViewById(R.id.rcvCar);
        btnBackP = v.findViewById(R.id.backPress);

        tvStartPoint.setText(getArguments().getString("startS"));
        tvEndpoint.setText(getArguments().getString("endS"));
        start = getArguments().getInt("startInt");
        end = getArguments().getInt("endInt");
        numCus = getArguments().getInt("numCus");
        numChild = getArguments().getInt("numChild");

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcvCar.setLayoutManager(llm);
        RecyclerView.ItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvCar.addItemDecoration(decor);

        ticketAdapter = new TicketAdapter(lCoach, start, end, numCus, numChild);
        rcvCar.setAdapter(ticketAdapter);

        btnBackP.setOnClickListener(view -> {
            BackPressClick();
        });

        return v;
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

    private void BackPressClick(){
        navController.navigate(R.id.action_listCoach_to_searchTicket);
    }
}