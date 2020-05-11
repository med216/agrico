package com.example.acrigooo.plante_description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.Model.Modelplanteadded;
import com.example.acrigooo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class description extends Fragment {
    private View view;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView description_image,  img;
    ;
    TextView title,description;
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desription, container, false);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        description= view.findViewById(R.id.textdescription);
        img= view.findViewById(R.id.img);

        //

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
 title = view.findViewById(R.id.texttitle);
        title.setText(getActivity().getIntent().getStringExtra("nameplant"));
        //   String city = getIntent().getExtra("City");
        description.setText(getActivity().getIntent().getStringExtra("description"));

        //description_image.setImageDrawable(Drawable.createFromPath(getActivity().getIntent().getStringExtra("photo")));

        // description_image.setImageResource(Integer.parseInt(getActivity().getIntent().getStringExtra("photo")));
        // Toast.makeText(getContext(),""+description_image,Toast.LENGTH_LONG).show();

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
                    String image_image = ""+ds.child("photo").getValue();


                    if(modelplante.getNameplante().equals(getActivity().getIntent().getStringExtra("nameplant")))
                  {
                      try {

                          Picasso.get().load(image_image).into(img);

                      }catch (Exception e){

                      }

                  }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view ;


    }}


