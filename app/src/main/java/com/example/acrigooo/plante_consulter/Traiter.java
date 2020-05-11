package com.example.acrigooo.plante_consulter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.Main2Activity_kotlin;
import com.example.acrigooo.R;

public class Traiter extends Fragment {
    private View view;
    Button button_traiter;
     @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_traiter, container, false);

         String QRcode=getActivity().getIntent().getExtras().getString("QR");
        // Toast.makeText(getActivity(),QRcode,Toast.LENGTH_LONG).show();
         button_traiter = (Button) view.findViewById(R.id.button_traiter);

         button_traiter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getContext(), Main2Activity_kotlin.class);
                 getContext().startActivity(intent);
             }
         });

        return view;



    }





}



































