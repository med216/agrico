package com.example.acrigooo.plante_description;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class consulter extends Fragment {

    private View view;
    EditText textreference;
    Button buutonreffence;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference_hum,databaseReference_temp;
    com.example.acrigooo.plantconsulterActivity plantconsulterActivity;
    String refference ;
    List<String> ds;

    public void displaylist()
    {
        final    String s = textreference.getText().toString();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("sensors");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ds.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren()){
                        //Toast.makeText(getContext(),dss.getValue(),Toast.LENGTH_LONG).show();
                        String vv= dss.getKey();
                        ds.add(vv);
                    }
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0;i<ds.size();i++)
                    {
                        stringBuilder.append(ds.get(i)+"'");
                        if(  s.equals(ds.get(i))){
                            Intent intent = new Intent(getContext(), com.example.acrigooo.plantconsulterActivity.class);
                         String v = String.valueOf(intent.putExtra("key",ds.get(i)));

                            Toast.makeText(getContext(),"ok "+v,Toast.LENGTH_LONG).show();
                            startActivity(intent);

                        //    Bundle args = new Bundle();

                         //   order fragment = new order();

                          //  args.putString("key", s);

                          //  fragment.setArguments(args);


                        }


                    }
                   // Toast.makeText(getContext(),stringBuilder.toString(),Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consulter_description, container, false);

        textreference = view.findViewById(R.id.textreference);

        buutonreffence = view.findViewById(R.id.refference);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ds=new ArrayList<>();




        buutonreffence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    displaylist();


                }catch (Exception E){
                    Log.d(TAG, "onClick: 9" );
                    Toast.makeText(getContext(),""+E,Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onClick: "+E);
                }



            }
        });



        return view;
    }


}



/*
 Log.d(TAG, "onClick: 1");

                    databaseReference_hum = (DatabaseReference) firebaseDatabase.getReference("sf");
                    Log.d(TAG, "onClick: 2");
                    databaseReference_hum.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 Log.d(TAG, "onClick: 3");
                                //get data
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String hum_hum_hum = ("" + ds.getKey());

                                Toast.makeText(getContext(), "  " + hum_hum_hum + "" + s, Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onClick: 4");
                                if (hum_hum_hum.equals(s)) {
                                    Log.d(TAG, "onClick: 7");
                                    Intent intent = new Intent(getContext(), com.example.acrigooo.plantconsulterActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onClick: 8");
                            Toast.makeText(getContext(),"try again...............",Toast.LENGTH_LONG).show();
                        }
                    });
 */