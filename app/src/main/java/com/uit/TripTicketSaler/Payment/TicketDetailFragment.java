package com.uit.TripTicketSaler.Payment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.uit.TripTicketSaler.AccountManager.ClientAuth;
import com.uit.TripTicketSaler.Model.Ticket;
import com.uit.TripTicketSaler.Model.Trip;
import com.uit.TripTicketSaler.R;
import com.uit.TripTicketSaler.databinding.FragmentTicketDetailBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TicketDetailFragment extends Fragment {
    private FragmentTicketDetailBinding binding;
    private NavController navController;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Trip trip;

    public TicketDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentTicketDetailBinding.inflate(inflater, container, false);
        NavHostFragment hostFragment = (NavHostFragment) getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = hostFragment.getNavController();
        Ticket ticket = (Ticket)getArguments().getSerializable("ticket");

        db.collection("Trips").document(ticket.getTrip())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        trip = task.getResult().toObject(Trip.class);
                        binding.tvCoachPlate.setText(trip.getCoach());
                        binding.tvTripName.setText(trip.getTripName());
                        binding.tvStartDate.setText(trip.GetDepartureDate());
                    }
                });

        ArrayList<String> arrSelected =  ticket.getSeatNumber();
        String seatNum ="";
        for(String item : arrSelected){
            seatNum += item + " ";
        }
        int nA = ticket.getNumAdult();
        int nC = ticket.getNumChild();
        String service = "";
        if(ticket.getService().get("suitcase")) service += "Hành lý";
        if(ticket.getService().get("breakfast")) service += ",Bữa sáng";
        if(ticket.getService().get("meal")) service += ",Bữa chính";
        if(ticket.getService().get("insurance")) service += ",Bảo hiểm";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(ticket.ticketID, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            binding.imgQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if(ticket.getStatus()=="Chưa thanh toán") binding.tvStatus.setTextColor(Color.RED);
        binding.tvStatus.setText(ticket.getStatus());
        binding.tvTicketDetail.setText(ticket.getDetail());
        binding.tvTotalCost.setText(Integer.toString(ticket.getTotalCost()));
        binding.tvBookingDate.setText(ticket.GetPurTime());
        binding.tvCustomerName.setText(ClientAuth.Client.getUsername());
        String slkh = nA + " người lớn ";
        if(nC>0) slkh += nC + " trẻ em";
        binding.tvSLKH.setText(slkh);

        binding.tvService.setText(service);
        binding.tvSelectedSeats.setText(seatNum);

        binding.btnHomePage.setOnClickListener(view -> {
            navController.navigate(R.id.action_ticketDetailFragment_to_searchTicket);
        });

        return binding.getRoot();
    }
}