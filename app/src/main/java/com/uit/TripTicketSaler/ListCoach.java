package com.uit.TripTicketSaler;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uit.TripTicketSaler.Adapter.TicketAdapter;
import com.uit.TripTicketSaler.Interface.ICoachListener;
import com.uit.TripTicketSaler.Model.Coach;

import java.util.ArrayList;
import java.io.Serializable;

public class ListCoach extends Fragment  implements ICoachListener {

    private RecyclerView rcvCar;
    private TicketAdapter ticketAdapter;
    private TextView tvStartPoint;
    private TextView tvEndpoint;
    private TextView tvAfterD;
    private ImageView btnBackP;
    private ImageView btnFilter;

    private NavController navController;
    private static String desStart;
    private static String desEnd;
    private static String afterD;
    private static int start;
    private static int end;
    private static int numCus;
    private static int numChild;
    private static ArrayList<Coach> lCoach = new ArrayList<>();

    public ListCoach() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_coach, container, false);

        NavHostFragment hostFragment = (NavHostFragment) getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert hostFragment != null;
        navController = hostFragment.getNavController();

        tvAfterD = v.findViewById(R.id.afterDay);
        tvEndpoint = v.findViewById(R.id.endPoint);
        tvStartPoint = v.findViewById(R.id.startPoint);
        rcvCar = v.findViewById(R.id.rcvCar);
        btnBackP = v.findViewById(R.id.backPress);
        btnFilter = v.findViewById(R.id.filter);

        if(getArguments() != null){
            desStart = getArguments().getString("startS");
            desEnd = getArguments().getString("endS");
            start = getArguments().getInt("startInt");
            end = getArguments().getInt("endInt");
            numCus = getArguments().getInt("numCus");
            numChild = getArguments().getInt("numChild");
            afterD = getArguments().getString("afterD");
            lCoach = (ArrayList<Coach>) getArguments().getSerializable("Coaches");
        }

        tvStartPoint.setText(desStart);
        tvEndpoint.setText(desEnd);
        tvAfterD.setText(afterD);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rcvCar.setLayoutManager(llm);
        RecyclerView.ItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvCar.addItemDecoration(decor);
        ticketAdapter = new TicketAdapter(lCoach, start, end, numCus, numChild, this);
        rcvCar.setAdapter(ticketAdapter);

        btnBackP.setOnClickListener(view -> {
            BackPressClick();
        });
        btnFilter.setOnClickListener(view -> {

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

    @Override
    public void onClickTicket(int pos) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("coach", lCoach.get(pos));
        bundle.putString("pickUp", desStart);
        bundle.putString("dest", desEnd);
        bundle.putInt("start", start);
        bundle.putInt("end", end);
        bundle.putInt("adult", numCus);
        bundle.putInt("child", numChild);

        navController.navigate(R.id.action_listCoach_to_detailTicket, bundle);
    }
}