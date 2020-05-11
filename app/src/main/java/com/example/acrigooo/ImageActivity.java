package com.example.acrigooo;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private View view;
    List<String> ds;
    ListView lv;
    ListView lv1;
    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ds=new ArrayList<>();

        lv1 = (ListView) findViewById(R.id.list1);
        displaylisthumidity();


    }
    public void displaylisthumidity()
    {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ImageFolder");

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ds.clear();
                    for (DataSnapshot dss : dataSnapshot.getChildren()){

                        String vv=dss.getValue(String.class);
                        String vvv=dss.getKey();
                        ds.add(vv);
                    }
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0;i<ds.size();i++)
                    {
                        stringBuilder.append(ds.get(i)+"'");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ImageActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, ds.toArray(new String[ds.size()]));
                    lv1.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
