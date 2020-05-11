package com.example.acrigooo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.plante_consulter.HistoriqueTemperature;
import com.example.acrigooo.plante_consulter.Traiter;
import com.example.acrigooo.plante_consulter.consulter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class plantconsulterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantconsulter);

         BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_consulter);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_consulter,
                    new com.example.acrigooo.plante_description.description() ).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment =  null;

                    switch (item.getItemId()) {


                        case R.id.Traiter:
                            selectedFragment = new Traiter();
                            break;
                        case R.id.nav_consulter:
                            selectedFragment = new consulter();
                            break;
                        case R.id.nav_historiqueTemp:
                            selectedFragment = new HistoriqueTemperature();
                            break;
                        case R.id.nav_description1:
                            selectedFragment = new com.example.acrigooo.plante_description.description();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_consulter,
                            selectedFragment).commit();

                    return true;
                }
            };
}
