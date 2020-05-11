package com.example.acrigooo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.plante_description.consulter;
import com.example.acrigooo.plante_description.description;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class plantedescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantedescription);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_description);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_description,
                    new description()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_description_desc:
                            selectedFragment = new com.example.acrigooo.plante_description.description();
                            break;
                        case R.id.nav_consulter_desc:
                            selectedFragment = new consulter();
                            break;


                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_description,
                            selectedFragment).commit();

                    return true;
                }
            };
}