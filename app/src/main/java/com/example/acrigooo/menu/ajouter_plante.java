package com.example.acrigooo.menu;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acrigooo.Adapter.Adapterplante;
import com.example.acrigooo.Model.Modelplante;
import com.example.acrigooo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ajouter_plante extends Fragment {

    private View view;

    RecyclerView recyclerView;
    Adapterplante adapterplante;
    List<Modelplante> planteList;
    String q;

    Toolbar toolbar;

    Context context;

    private EditText mserch;
    private ImageButton mImg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_ajouterplante, container, false);

        //init rectcleview
        recyclerView = view.findViewById(R.id.planterecycleview);
        //set proprities
      recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //init plante list
        Log.d(TAG, "1");

         planteList = new ArrayList<>();
        //get all plante
        Log.d(TAG, "2");

        setHasOptionsMenu(true);
        getAllPlante();

        SearchView searchView = view.findViewById(R.id.action_search);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterplante.getFilter().filter(newText);
                return false;
            }
        });


        return view;
    }




    private void getAllPlante() {
String plante="plante";
         // get path of database named "plant" containing plant info
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("plante");

        Log.d(TAG, "getAllPlante: 1");
        //get all data from path
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: 2");
                //modelplanteList.clear();
                Log.d(TAG, "onDataChange: 3");
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Modelplante modelplante = ds.getValue(Modelplante.class);

                    // get all plant
                    Log.d(TAG, "onDataChange: 4");
                     planteList.add(modelplante);

                    Log.d(TAG, "onDataChange: 5");
                    //adapter
                    adapterplante = new Adapterplante(getActivity(), planteList);
                    Log.d(TAG, "onDataChange: 6");

                    //set adapter to recycleview
                    recyclerView.setAdapter(adapterplante);
                    Log.d(TAG, "onDataChange: 7");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_option,menu);
        MenuItem item=menu.findItem(R.id.nav_find);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(!TextUtils.isEmpty(query.trim())){
                    SearchPlante(query);
                }else
                {
                    getAllPlante();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(!TextUtils.isEmpty(newText.trim())){
                    SearchPlante(newText);
                }else
                {
                    getAllPlante();
                }
                return false;
            }
        });



        super.onCreateOptionsMenu(menu, inflater);
    }

    public void SearchPlante(final String q) {

        // get path of database named "plant" containing plant info
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("plante");

        Log.d(TAG, "getAllPlante: 1");
        //get all data from path
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: 2");
                //modelplanteList.clear();
                Log.d(TAG, "onDataChange: 3");
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Modelplante modelplante = ds.getValue(Modelplante.class);

                    // get all plant
                    Log.d(TAG, "onDataChange: 4");

                    Log.d(TAG, "onDataChange: 5");
                    //adapter
                    adapterplante = new Adapterplante(getActivity(), planteList);
                    Log.d(TAG, "onDataChange: 6");
                    adapterplante.notifyDataSetChanged();
                    //set adapter to recycleview
                    recyclerView.setAdapter(adapterplante);
                    Log.d(TAG, "onDataChange: 7");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}



