package com.uit.TripTicketSaler;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.uit.TripTicketSaler.Model.AppUser;
import com.uit.TripTicketSaler.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentSignUpBinding binding;

    public SignUpFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        binding.btnSignUp.setOnClickListener(view -> {
            SignUpAccount();
        });

        return binding.getRoot();
    }

    private void SignUpAccount(){
        String _username = binding.tvUsername.getText().toString().trim();
        String _pass = binding.tvPassword.getText().toString().trim();
        String _repass = binding.tvRepeatPass.getText().toString().trim();
        String _email = binding.tvEmail.getText().toString().trim();
        if(_username.equals("") || _pass.equals("") || _email.equals("") || _repass.equals("")){
            Toast.makeText(getActivity(), "Xin điền thông tin đăng ký đầy đủ", Toast.LENGTH_SHORT).show();
        }
        if(!_pass.equals(_repass)){
            Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }
        else{
            ClientAuth.mAuth.createUserWithEmailAndPassword(_email, _pass)
                    .addOnCompleteListener(getActivity(), task -> {
                        if(task.isSuccessful()){
                            AppUser newUser = new AppUser(_username, "", "");
                            ClientAuth.Client = newUser;
                            ClientAuth.mClient = ClientAuth.mAuth.getCurrentUser();
                            db.collection("Users").document(ClientAuth.mClient.getUid()).set(newUser);
                            Toast.makeText(getActivity(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getActivity(), "Tạo tài khoản thất bại: "
                                    + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}