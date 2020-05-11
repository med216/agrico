package com.example.acrigooo.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.Authentication;
import com.example.acrigooo.MainActivity;
import com.example.acrigooo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class fragmentregistr extends Fragment {

    private View view;
    Authentication activity;
    public static final String TAG = "TAG";
    EditText  mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registre, container, false);
        activity = (Authentication) getActivity();
        ConstraintLayout swiperight111 = (ConstraintLayout) view.findViewById(R.id.swiperight111);
        swiperight111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick", "onClick: ");
                activity.addFragment(new fragmentlogin(), false);
            }
        });

        mEmail      = view.findViewById(R.id.Email);
        mPassword   = view.findViewById(R.id.password);

        mRegisterBtn= view.findViewById(R.id.btnregistre);


        fAuth = FirebaseAuth.getInstance();
       // fStore = FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser() != null){
          //  startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
          //  activity.finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
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


                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                         Toast.makeText(getActivity(), "User Created.", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = fAuth.getCurrentUser();
                            // get user email and uid from auth

                            String email = user.getEmail();
                            String uid = user.getUid();
                            // whene user registred Store user info in firebase realtime database too
                            // using Hashmap
                            HashMap<Object,String> hashMap = new HashMap<>();
                            //put info in hashmap
                            hashMap.put("email",email);
                            hashMap.put("Uid",uid);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("imageUrl","");
                            hashMap.put("cover","");


                            // firebase instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //path to store user data named "users"
                            DatabaseReference reference = database.getReference("Users");
                            // put data whith in hashmap in database
                            reference.child(uid).setValue(hashMap);

                            startActivity(new Intent(getActivity(), MainActivity.class));


                        }else {
                            Toast.makeText( getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            activity.addFragment(new fragmentlogin(), false);

                        }
                    }
                });
            }
        });






        return view;
    }
}
