package com.example.acrigooo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acrigooo.Model.Modelplante;
import com.example.acrigooo.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapterplante extends RecyclerView.Adapter<Adapterplante.MYHolder> implements Filterable {

    //

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LinearLayout linearLayout;
    public       String x;

    com.example.acrigooo.menu.consulter_plante consulter_plante ;
    com.example.acrigooo.menu.ajouter_plante ajouter_plante;

    //

    Context context ;
    List<Modelplante> planteliste;
    List<Modelplante> plantelisteFull;
    private View view;
    int index ;
    List<String> QRCodes=new ArrayList<>();;


    public Adapterplante(Context context, List<Modelplante> planteliste) {
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

        //get data

        final String plantenamee= planteliste.get(position).getNameplante();
        final String descriptionn = planteliste.get(position).getDescription();
        final String planteimagee = planteliste.get(position).getPhoto();
        final String uidd = planteliste.get(position).getUid();
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
            public void onClick(final View view) {
                //Toast.makeText(context,"good",Toast.LENGTH_LONG).show();


                Snackbar.make(view,"click log press to add the plant to your list",Snackbar.LENGTH_LONG).show();
//

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                //Toast.makeText(context,"Plant added",Toast.LENGTH_LONG).show();



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
                          //  Toast.makeText(context,stringBuilder,Toast.LENGTH_LONG).show();
                            final String[] iii= QRCodes.toArray(new String[0]);

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Choose the Qr code");

                            final int checkedItem = 0; //this will checked the item when user open the dialog
                            builder.setSingleChoiceItems(iii,checkedItem, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                     String x = iii[which];


                                    //  Toast.makeText(context, "Position: " + which + " Value: " + iii[which], Toast.LENGTH_LONG).show();



                                    index= position;
                                    notifyDataSetChanged();

                                    //init firebase
                                    firebaseAuth = FirebaseAuth.getInstance();
                                    user = firebaseAuth.getCurrentUser();
                                    //
                                    String uid = user.getUid();
                                    // Write a message to the database
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Users").child(user.getUid()).child("plante_ajouter");

                                    myRef.child(uidd).child("nameplante").setValue(plantenamee);
                                    myRef.child(uidd).child("photo").setValue(planteimagee);
                                    myRef.child(uidd).child("description").setValue(descriptionn);
                                    myRef.child(uidd).child("uid").setValue(uidd);
                                    myRef.child(uidd).child("maxtemp").setValue(maxtemp);
                                    myRef.child(uidd).child("mintemp").setValue(mintemp);
                                    myRef.child(uidd).child("maxhum").setValue(maxhum);
                                    myRef.child(uidd).child("minhum").setValue(minhum);
                                    myRef.child(uidd).child("QR").setValue(x);



                                }
                            });

                            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {




                                    Snackbar.make(view,"plant added",Snackbar.LENGTH_LONG).show();


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

        if(index==position){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }





    @Override
    public int getItemCount() {
        return planteliste.size();
    }

    public void clearApplications() {
        int size = this.planteliste.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                planteliste.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    private Filter examplefilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Modelplante> filtredList=new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtredList.addAll(plantelisteFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Modelplante item : plantelisteFull) {
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

    static class MYHolder extends RecyclerView.ViewHolder{
        ImageView planteimage;
        TextView plantename,description,maxtemp,maxhum,mintemp,minhum;
LinearLayout linearLayout;
        public MYHolder(@NonNull View itemView) {
            super(itemView);

            planteimage = itemView.findViewById(R.id.planteimage);
            plantename = itemView.findViewById(R.id.plantname);
            description = itemView.findViewById(R.id.description);
            linearLayout = itemView.findViewById(R.id.coloritem);
            maxtemp = itemView.findViewById(R.id.maxtemp);
            mintemp = itemView.findViewById(R.id.mintemp);
            maxhum= itemView.findViewById(R.id.maxhum);
            minhum = itemView.findViewById(R.id.minhum);

        }
    }

}
