package com.example.acrigooo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acrigooo.Model.ImageModel;
import com.example.acrigooo.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MYHolder>  {

     List<ImageModel> images;
    Context context ;
    private AdapterView.OnItemClickListener mListener;
    private View view;
    int index ;

    public RecyclerAdapter(Context context,List<ImageModel> images) {
        this.context = context;
            this.images=images;

    }


    @NonNull
    @Override
    public MYHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout(ajouterplante.xml)


        View view = LayoutInflater.from(context).inflate(R.layout.row_model,parent,false);


        return new MYHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.MYHolder holder, final int position) {


       // ImageModel currentTeacher = images.get(position);
        final String planteimagee = images.get(position).getImageUrl();

      //  holder.dateTextView.setText(getDateToday());
        try {

            Picasso.get().load(planteimagee)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.teacherImageView);

        }catch (Exception e){

        }





        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"good",Toast.LENGTH_LONG).show();

            }
        });





    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
    static class MYHolder extends RecyclerView.ViewHolder{
       // public TextView dateTextView;
        public ImageView teacherImageView;


        public MYHolder(@NonNull View itemView) {
            super(itemView);

           // dateTextView = itemView.findViewById(R.id.dateTextView);
            teacherImageView = itemView.findViewById(R.id.image_view_upload);


        }
    }
}
