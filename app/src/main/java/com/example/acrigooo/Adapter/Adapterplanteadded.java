package com.example.acrigooo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acrigooo.MainActivity;
import com.example.acrigooo.Model.Modelplanteadded;
import com.example.acrigooo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapterplanteadded extends RecyclerView.Adapter<Adapterplanteadded.MYHolder> implements Filterable {

    //

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LinearLayout linearLayout;
    //
    Modelplanteadded description;
    //
    MainActivity mainActivity;
    com.example.acrigooo.plantconsulterActivity plantconsulterActivity;
    com.example.acrigooo.plantedescription plantedescription;


    //

    Context context ;
    List<Modelplanteadded> planteliste;
    private View view;
    int index ;
    List<Modelplanteadded> plantelisteFull;

    List<String> QRCodes=new ArrayList<>();;


    public Adapterplanteadded(Context context, List<Modelplanteadded> planteliste) {
        this.context = context;
        this.planteliste = planteliste;
        plantelisteFull=new ArrayList<>(planteliste);

    }



    @NonNull
    @Override
    public MYHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout(ajouterplante.xml)


        View view = LayoutInflater.from(context).inflate(R.layout.row_plante,parent,false);


        return new MYHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MYHolder holder, final int position) {
        final String QR= planteliste.get(position).getQR();

        final String plantenamee= planteliste.get(position).getNameplante();
        final String descriptionn = planteliste.get(position).getDescription();
        final String planteimagee = planteliste.get(position).getPhoto();
        final  String uid = planteliste.get(position).getUid();
        final  Integer maxtemp = planteliste.get(position).getMaxtemp();
        final  Integer mintemp = planteliste.get(position).getMintemp();

        final  Integer maxhum = planteliste.get(position).getMaxhum();
        final  Integer minhum = planteliste.get(position).getMinhum();




        //set data

        holder.plantename.setText(plantenamee);
        holder.description.setText(descriptionn);
        try {

            Picasso.get().load(planteimagee)
                    .placeholder(R.drawable.ic_photo_camera_black_24dp)
                    .into(holder.planteimage);

        }catch (Exception e){

        }
        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(context,"user plant",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(holder.itemView.getContext(), com.example.acrigooo.plantconsulterActivity.class);

                intent.putExtra("nameplant",planteliste.get(position).getNameplante());
                intent.putExtra("description",planteliste.get(position).getDescription());


                intent.putExtra("maxtemp",planteliste.get(position).getMaxtemp());
                intent.putExtra("mintemp",planteliste.get(position).getMintemp());
                intent.putExtra("maxhum",planteliste.get(position).getMaxhum());
                intent.putExtra("minhum",planteliste.get(position).getMinhum());
                intent.putExtra("QR",planteliste.get(position).getQR());
               // Toast.makeText(context,planteliste.get(position).getQR(),Toast.LENGTH_LONG).show();

                Intent s =intent.putExtra("photo",planteliste.get(position)
                        .getPhoto());
                //Toast.makeText(context,""+s,Toast.LENGTH_LONG).show();

                holder.itemView.getContext().startActivity(intent);



            }
        });
        final String[] listItems = {"one", "two", "three", "four", "five"};


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();





        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {


            @Override
            public boolean onLongClick(View v) {

                //init firebase
                firebaseAuth = FirebaseAuth.getInstance();
                user = firebaseAuth.getCurrentUser();
// get path of database named "plant" containing plant info
                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Users").child(user.getUid()).child("QRcode");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            QRCodes.clear();

                            for (DataSnapshot dss : dataSnapshot.getChildren()){
                                //Toast.makeText(getContext(),dss.getValue().toString(),Toast.LENGTH_LONG).show();
                                String vv=dss.getValue(String.class);
                                String vvv=dss.getKey();
                                QRCodes.add(vv);


                            }

                            StringBuilder stringBuilder=new StringBuilder();
                            for(int i=0;i<QRCodes.size();i++)
                            {
                                stringBuilder.append(QRCodes.get(i)+"'");
                            }
                           // Toast.makeText(context,stringBuilder,Toast.LENGTH_LONG).show();
                            String[] iii= QRCodes.toArray(new String[0]);

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Choose the Qr code");

                            int checkedItem = 0; //this will checked the item when user open the dialog
                            builder.setSingleChoiceItems(iii, checkedItem, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(context, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();


                                }
                            });

                            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


























                return true;
            }
        });
    }


/*
holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {


        @Override
        public boolean onLongClick(View v) {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose the Qr code");

            int checkedItem = 0; //this will checked the item when user open the dialog
            builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(context, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();


                }
            });

            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();






            return true;
        }
    });*/





        @Override
    public int getItemCount() {
        return planteliste.size();
    }

    public void removeItem(int position) {

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //
        String uid = user.getUid();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(user.getUid());


        Query applesQuery = myRef.child("plante_ajouter").orderByChild("nameplante").equalTo(planteliste.get(position).getNameplante());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    planteliste.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    private Filter examplefilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Modelplanteadded> filtredList=new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtredList.addAll(plantelisteFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Modelplanteadded item : plantelisteFull) {
                    if (item.getNameplante().toLowerCase().contains(filterPattern)) {
                        filtredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            planteliste.clear();
            planteliste.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    // view holder class
    // view holder class

    class MYHolder extends RecyclerView.ViewHolder {
        ImageView planteimage;
        TextView plantename,description,maxtemp,maxhum,mintemp,minhum,QR;

        public MYHolder(@NonNull View itemView) {
            super(itemView);

            planteimage = itemView.findViewById(R.id.planteimage);
            plantename = itemView.findViewById(R.id.plantname);
            description = itemView.findViewById(R.id.description);
            maxtemp = itemView.findViewById(R.id.maxtemp);
            mintemp = itemView.findViewById(R.id.mintemp);
            maxhum= itemView.findViewById(R.id.maxhum);
            minhum = itemView.findViewById(R.id.minhum);
            QR = itemView.findViewById(R.id.QR);

        }
    }

}
