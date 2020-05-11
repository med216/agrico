package com.example.acrigooo.plante_consulter;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HistoriqueTemperature extends Fragment {
    private View view;
    List<String> ds;
    ListView lv;
    ListView lv1;
    Toolbar toolbar;
    EditText theFilter,theFilter1;
    ImageButton button;
    private static final int BackImage=1;
    TextView tabhum,tabtemp;

    RelativeLayout rlhum,rltemp;
    Button btn;
    private StorageReference Folder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_historie, container, false);

        String QRcode=getActivity().getIntent().getExtras().getString("QR");
       // Toast.makeText(getActivity(),QRcode,Toast.LENGTH_LONG).show();



        tabhum=view.findViewById(R.id.tabhum);
        tabtemp=view.findViewById(R.id.tabtemp);
        rlhum =view.findViewById(R.id.rlhum);
        rltemp =view.findViewById(R.id.rltemp);
        showhumidityUI();

        ds=new ArrayList<>();
        // lv = view.findViewById(R.id.list);
        lv1 = view.findViewById(R.id.list1);
        lv = view.findViewById(R.id.list11);
        setHasOptionsMenu(true);

        final EditText theFilter =view.findViewById(R.id.searchFilter);
        final EditText theFilter1 =view.findViewById(R.id.searchFilter1);








        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-d");
                String STR=simpleDateFormat.format(myCalendar.getTime());
                theFilter.setText(STR);

            }

        };


        ImageButton button =view.findViewById(R.id.calendrier);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DialogFragment datePicker = new DatePickerFragment();
                //  datePicker.show(getActivity().getSupportFragmentManager(), "date picker");

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });




        String Item1 = getActivity().getIntent().getExtras().getString("key");
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance()
                .getReference("sensors").child(QRcode).child("humidity");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ds.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren()){
                        //Toast.makeText(getContext(),dss.getValue().toString(),Toast.LENGTH_LONG).show();
                        String vv=dss.getValue(String.class);
                        String vvv=dss.getKey();
                        ds.add(vv);
                    }
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0;i<ds.size();i++)
                    {
                        stringBuilder.append(ds.get(i)+"'");
                    }
                 //   Toast.makeText(getActivity(),stringBuilder,Toast.LENGTH_LONG).show();
                    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, ds.toArray(new String[ds.size()]));
                    lv1.setAdapter(adapter1);
                    theFilter.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            adapter1.getFilter().filter(charSequence);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        DatabaseReference databaseReference11 = FirebaseDatabase.getInstance()
                .getReference("sensors").child(QRcode).child("Temperture");
        databaseReference11.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ds.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren()){
                       // Toast.makeText(getContext(),dss.getValue().toString(),Toast.LENGTH_LONG).show();
                        String vv=dss.getValue(String.class);
                        String vvv=dss.getKey();
                        ds.add(vv);
                    }
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0;i<ds.size();i++)
                    {
                        stringBuilder.append(ds.get(i)+"'");
                    }
                    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, ds.toArray(new String[ds.size()]));
                    lv.setAdapter(adapter1);
                    theFilter1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            adapter1.getFilter().filter(charSequence);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        tabtemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showtemperatureUI();

            }
        });

        tabhum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showhumidityUI();

            }
        });




        return view;
    }

    @SuppressLint("ResourceType")
    private void showhumidityUI() {
        rlhum.setVisibility(View.VISIBLE);
        rltemp.setVisibility(View.GONE);

        tabhum.setTextColor(getResources().getColor(R.color.black));
        tabhum.setBackgroundResource(R.drawable.shape_rect04);
        tabtemp.setTextColor(getResources().getColor(R.color.white));
        tabtemp.setBackgroundColor(getResources().getColor(R.color.transparent));

    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void showtemperatureUI() {
        rltemp.setVisibility(View.VISIBLE);
        rlhum.setVisibility(View.GONE);


        tabtemp.setTextColor(getResources().getColor(R.color.black));
        tabtemp.setBackgroundResource(R.drawable.shape_rect04);
        tabhum.setTextColor(getResources().getColor(R.color.white));
        tabhum.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==BackImage)
        {
            if(resultCode== RESULT_OK)
            {


                Uri  ImageData=data.getData();
                final StorageReference Imagename=Folder.child("image"+ImageData.getLastPathSegment());
                Imagename.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        // Toast.makeText(getActivity(),"uploaded",Toast.LENGTH_LONG).show();
                        Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                DatabaseReference imageStore= FirebaseDatabase.getInstance().getReference().child("id_arduino1");
                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("imageurl",String.valueOf(uri));
                                imageStore.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                        Toast.makeText(getActivity(),"Finally Completed",Toast.LENGTH_LONG).show();

                                    }
                                });

                            }
                        });
                    }
                });

            }

        }
    }
}