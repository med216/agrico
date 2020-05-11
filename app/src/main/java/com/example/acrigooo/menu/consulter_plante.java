package com.example.acrigooo.menu;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acrigooo.Adapter.Adapterplanteadded;
import com.example.acrigooo.Model.Modelplanteadded;
import com.example.acrigooo.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class consulter_plante extends Fragment {

    private View view;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    RecyclerView recyclerView;
    Adapterplanteadded adapterplante;
    List<Modelplanteadded> planteList;

    Toolbar toolbar;
    Context context;

    private EditText mserch;
    private ImageButton mImg;
    public  String deleteID = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consulterplante, container, false);

        //init rectcleview
        recyclerView = view.findViewById(R.id.planterecycleview);

        //set proprities
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
       // new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               final int position= viewHolder.getAdapterPosition();
                adapterplante.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(recyclerView,"plant deleted ",Snackbar.LENGTH_LONG);


                snackbar.show();


            }
        }).attachToRecyclerView(recyclerView);



        return view;
    }

    private void getAllPlante() {
        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
// get path of database named "plant" containing plant info
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users").child(user.getUid()).child("plante_ajouter");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Modelplanteadded modelplante = ds.getValue(Modelplanteadded.class);

                    // get all plant
                    Log.d(TAG, "onDataChange: 4");
                    planteList.add(modelplante);

                    Log.d(TAG, "onDataChange: 5");
                    //adapter
                    adapterplante = new Adapterplanteadded(getActivity(), planteList);
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
 /* @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

            adapterplante.removeItem(viewHolder.getAdapterPosition());



}*/


}
