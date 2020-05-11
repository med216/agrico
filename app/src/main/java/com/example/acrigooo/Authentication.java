package com.example.acrigooo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.auth.fragmentlogin;

public class Authentication extends AppCompatActivity {

    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);



        fragmentlogin fragment = new fragmentlogin();

        addFragment(fragment,false);





    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        androidx.fragment.app.FragmentManager manager = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right,R.anim.exit_to_left,R.anim.exit_to_right);
        transaction.replace(R.id.frameLayout, fragment, "list Fragment");
        if (addToBackStack) {
            transaction.addToBackStack("");
        }
        transaction.commit();
    }

}