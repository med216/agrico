package com.example.acrigooo.plante_consulter;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class consulter extends Fragment {

    public String QRcode;
    private View view;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView description_image;
    TextView title,description;



    TextView temp,hum;
    DatabaseReference databaseReference_hum,databaseReference_temp;
    DatabaseReference databaseReference_hum1,databaseReference_temp1,databaseReference_max,databaseReference1;
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
   // private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    LineGraphSeries<DataPoint> list = new LineGraphSeries<DataPoint>();

    /////////

     ArrayList<Entry> yData;

    LineDataSet lineDataSet;
    private LineChart Temp_linechart;

    ///////////

    PieChart pieChart,pieChart_1;
    DatabaseReference vpDBReference;
    ArrayList<PieEntry> yvalues;
    int conta;
    String nomVoto;
    float numVoto;

    Button btn;

    /////////////////
TextView texthummax,texthummin,texttempmax;
RelativeLayout rlt,rlt1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_consulter, container, false);
        //init firebase
       // Toast.makeText(getActivity(),QRcode,Toast.LENGTH_LONG).show();


        final Integer maxtemp=getActivity().getIntent().getExtras().getInt("maxtemp");
        final Integer mintemp=getActivity().getIntent().getExtras().getInt("mintemp");
        final Integer   maxhum=getActivity().getIntent().getExtras().getInt("maxhum");
        final Integer minhum=getActivity().getIntent().getExtras().getInt("minhum");

//    Toast.makeText(getContext(),maxtemp+mintemp+maxhum+minhum,Toast.LENGTH_LONG).show();

        rlt=view.findViewById(R.id.rlt);
        rlt1=view.findViewById(R.id.rlt1);
        title = view.findViewById(R.id.texttitle);
        title.setText(getActivity().getIntent().getStringExtra("nameplant"));

        //texthummin=view.findViewById(R.id.texthummin);
        texthummax=view.findViewById(R.id.texthummax);
        texttempmax=view.findViewById(R.id.texttempmax);

        hum = view.findViewById(R.id.texthum);
        temp = view.findViewById(R.id.texttemp);

       // texthummin.setText(minhum);
        texttempmax.setText(mintemp+"-"+maxtemp+" (°C)");
        texthummax.setText(minhum+"-"+maxhum+" (%)");

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Users");

        ///

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference_max= firebaseDatabase.getReference("Users").child("id_arduino1").child("humidity1");



        final String QRcode=getActivity().getIntent().getExtras().getString("QR");

        final DatabaseReference    databaseReference_hum = firebaseDatabase.getReference("sensors").child(QRcode).child("humidity1");
        final DatabaseReference    databaseReference_temp = firebaseDatabase.getReference("sensors").child(QRcode).child("Temperture1");








        final  DatabaseReference databaseReference1 = FirebaseDatabase.getInstance()
                .getReference("Users").child(user.getUid()).child("system").child("systemon").child("systemon");








        databaseReference_hum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //yData.clear();
                //   int UIDs = dataSnapshot.getValue(Integer.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    final String UIDss = ds.getValue(String.class);
                    String xx=UIDss;
                    final int i= Integer.valueOf(xx);

                    //Toast.makeText(getActivity(), i, Toast.LENGTH_SHORT).show();
                    //get data

                    //  Toast.makeText(getActivity(), ultimaVersion, Toast.LENGTH_LONG).show();
                    databaseReference1.addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String UIDs = dataSnapshot.getValue(String.class);

                                    if(UIDs.equals("true"))
                                    {
                                      //  Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();

                                        if((minhum > i ))
                                        {
                                            hum.setText(UIDss);
                                            hum.setTextColor(getResources().getColor(R.color.rouge));

                                            ////activer arrocage

////////////////////////////////////////////////////
                                            DatabaseReference databaseRefArrocage = FirebaseDatabase.getInstance()
                                                    .getReference("sensors").child(QRcode).child("HumiditeMin").child("HumiditeMin");
                                            databaseRefArrocage.setValue(minhum);


                                            ////////////////////////////







                                        }else if((minhum <=i )&&(maxhum >= i ))
                                        {

                                            hum.setText(UIDss);

                                            hum.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        }

                                        else if(maxhum <= i )
                                        {

                                            hum.setText(UIDss);
                                            hum.setTextColor(getResources().getColor(R.color.colorPrimary));






                                        }





                                    }

                                    else if(UIDs.equals("false")){

                                       // Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();

                                        if((minhum > i ))
                                        {
                                            hum.setText(UIDss);

                                            hum.setTextColor(getResources().getColor(R.color.rouge));


                                        }else if((minhum <=i )&&(maxhum >= i ))
                                        {

                                            hum.setText(UIDss);

                                            hum.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        }

                                        else if(maxhum <= i )
                                        {

                                            hum.setText(UIDss);

                                            hum.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        }




                                    }




                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });




                    rlt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            databaseReference1.addValueEventListener(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String UIDs = dataSnapshot.getValue(String.class);

                                            if(UIDs.equals("true")||UIDs.equals("false") ) {
                                                if ((minhum > i)) {

                                                    Snackbar.make(view, " your plant needs sun when the value  "+minhum , Snackbar.LENGTH_LONG).show();

                                                } else if ((minhum <= i) && (maxhum >= i)) {
                                                    Snackbar.make(view, "your plant verry good", Snackbar.LENGTH_LONG).show();


                                                }

                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });




                        }
                    });


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

















        databaseReference_temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //yData.clear();
                //   int UIDs = dataSnapshot.getValue(Integer.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    final String UIDss = ds.getValue(String.class);
                    String xx=UIDss;
                    final int i= Integer.valueOf(xx);

                    //Toast.makeText(getActivity(), i, Toast.LENGTH_SHORT).show();
                    //get data

                    //  Toast.makeText(getActivity(), ultimaVersion, Toast.LENGTH_LONG).show();
                    databaseReference1.addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String UIDs = dataSnapshot.getValue(String.class);

                                    if(UIDs.equals("true"))
                                    {
                                        //  Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();

                                        if((mintemp > i ))
                                        {
                                            temp.setText(UIDss);
                                            temp.setTextColor(getResources().getColor(R.color.rouge));

                                            ////activer chauffage

////////////////////////////////////////////////////

                                            DatabaseReference databaseRefchauffage = FirebaseDatabase.getInstance()
                                                    .getReference("sensors").child(QRcode).child("TemperatureMin").child("TemperatureMin");
                                            databaseRefchauffage.setValue(mintemp);
                                            ////////////////////////////



                                        }else if((mintemp <=i )&&(maxtemp >= i ))
                                        {

                                            temp.setText(UIDss);

                                            temp.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        }

                                        else if(maxtemp <= i )
                                        {

                                            temp.setText(UIDss);
                                            temp.setTextColor(getResources().getColor(R.color.colorPrimary));

////////////////////////////////////////////////////////

                                            ///////activer ventilateur
                                            DatabaseReference databaseRefventilateur = FirebaseDatabase.getInstance()
                                                    .getReference("sensors").child(QRcode).child("TemperatureMax").child("TemperatureMax");
                                            databaseRefventilateur.setValue(maxtemp);



                                        }





                                    }

                                    else if(UIDs.equals("false")){

                                        Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();

                                        if((mintemp > i ))
                                        {
                                            temp.setText(UIDss);

                                            temp.setTextColor(getResources().getColor(R.color.rouge));


                                        }else if((mintemp <=i )&&(maxtemp >= i ))
                                        {

                                            temp.setText(UIDss);

                                            temp.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        }

                                        else if(maxtemp <= i )
                                        {

                                            temp.setText(UIDss);

                                            temp.setTextColor(getResources().getColor(R.color.colorPrimary));

                                        }




                                    }




                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });




                    rlt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            databaseReference1.addValueEventListener(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String UIDs = dataSnapshot.getValue(String.class);

                                            if(UIDs.equals("true")||UIDs.equals("false") ) {
                                                if ((mintemp > i)) {

                                                    Snackbar.make(view, " your plant needs Temperature when reaches the value  "+minhum , Snackbar.LENGTH_LONG).show();

                                                } else if ((mintemp <= i) && (maxtemp >= i)) {
                                                    Snackbar.make(view, "your plant verry good", Snackbar.LENGTH_LONG).show();


                                                }
                                            } else if ( (maxtemp >= i)) {
                                                Snackbar.make(view, "your plant needs air temperature with max value  "+maxtemp, Snackbar.LENGTH_LONG).show();




                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });




                        }
                    });


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





















        return view;

    }



    private void addNotification() {


    }

}
