package com.example.acrigooo.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.acrigooo.Authentication;
import com.example.acrigooo.MainActivity;
import com.example.acrigooo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class fragmentlogin extends Fragment {

    private View view;
    Authentication activity;
    TextView reseat;


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final EditText mEmail,mPassword;
        Button mLoginBtn;
        final FirebaseAuth fAuth;

     View view = inflater.inflate(R.layout.fragment_login, container, false);
        activity = (Authentication) getActivity();
        ConstraintLayout swipeLeft111 =   view.findViewById(R.id.swipeLeft111);
        swipeLeft111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addFragment(new fragmentregistr(), false);
            }
        });
        mEmail = view.findViewById(R.id.Email);
        mPassword = view.findViewById(R.id.password);
         fAuth = FirebaseAuth.getInstance();
        mLoginBtn = view.findViewById(R.id.LoginBtn);
        reseat = view.findViewById(R.id.reseat);
        reseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                // Create and show the dialog.
                SomeDialog newFragment = new SomeDialog ();
                newFragment.show(ft, "dialog");
            }


        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }


                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }else {
                            Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }

                    }
                });

            }
        });







        return view;
    }
}
