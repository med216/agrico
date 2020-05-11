package com.example.acrigooo.menu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acrigooo.Model.Upload;
import com.example.acrigooo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class profile extends Fragment {

    Button qr;
    private View view;

    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Storage
    StorageReference storageReference;


    //view from xml
    ImageView avatarIv , cover ;
    TextView name,email,phone;
    FloatingActionButton fab,fab2,fab3;
    ProgressDialog progressDialog;

    //uri of picked image
    Uri image_uri;
    //for checking profile or cover photo
    String profileOrcoverPhotot;


    //
    private static final int CHOOSE_IMAGE = 1;
    private static final int CHOOSE_IMAGE1 = 2;
    private Uri imgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    //

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_profile, container, false);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        storageReference = getInstance().getReference();

        //init views
        avatarIv = view.findViewById(R.id.avatarIv);
        name = view.findViewById(R.id.Namee);
        email = view.findViewById(R.id.Emaill);
        phone = view.findViewById(R.id.phonee);
        cover = view.findViewById(R.id.cover);
        fab = view.findViewById(R.id.fab);
        fab2 = view.findViewById(R.id.fab2);
        fab3 = view.findViewById(R.id.fab3);
        //
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        //
        progressDialog = new ProgressDialog(getActivity());
        Query query = databaseReference.orderByChild("Uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // check util required data get
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //get data
                    String name_name   = ""+ds.child("name").getValue();
                    String email_email = ""+ds.child("email").getValue();
                    String phone_phone = ""+ds.child("phone").getValue();
                    String image_image = ""+ds.child("imageUrl").child("imageUrl").getValue();
                    String cover_cover = ""+ds.child("cover").child("imageUrl").getValue();


                    //set data
                    name.setText(name_name);
                    email.setText(email_email);
                    phone.setText(phone_phone);
                    try {

                        Picasso.get().load(image_image).into(avatarIv);

                    }catch (Exception e){
                        Picasso.get().load(R.drawable.ic_face).into(avatarIv);

                    }
                    try {

                        Picasso.get().load(cover_cover).into(cover);

                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // fab button click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileDialog();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(avatarIv!=null){
                    uploadImage();
                }
                if(cover!=null){
                    uploadImage_1();
                }

            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth.getInstance().signOut(); //signout firebase


            }
        });



        return view;
    }

    private void uploadImage() {
        if (imgUrl != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUrl));

            mUploadTask = fileReference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(  uri.toString());

                                    mDatabaseRef.child(user.getUid()).child("imageUrl").setValue(upload);
                                    // mDatabaseRef.child(uploadID).setValue(upload);
                                    Toast.makeText(getActivity(), "Upload successfully", Toast.LENGTH_LONG).show();
                                    //imgPreview.setImageResource(R.drawable.imagepreview);
                                    // cover.setImageResource(R.drawable.ic_add_a_photo);
                                    //imgDescription.setText("");
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadImage_1() {
        if (image_uri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(image_uri));

            mUploadTask = fileReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(  uri.toString());

                                    mDatabaseRef.child(user.getUid()).child("cover").setValue(upload);
                                    // mDatabaseRef.child(uploadID).setValue(upload);
                                    Toast.makeText(getActivity(), "Upload successfully", Toast.LENGTH_LONG).show();
                                    //imgPreview.setImageResource(R.drawable.imagepreview);
                                    // cover.setImageResource(R.drawable.ic_add_a_photo);
                                    //imgDescription.setText("");
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditProfileDialog() {

        // option to show in dialogue
        String option [] = {"Edit Profile Picture","Edit cover Photo","Edit Name","Edit Phone"};
        //alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle("Chose Action");
        // set item to dialogue
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                //handle dialogue item click
                if(which==0){
                    // Edit profile clicked
                    progressDialog.setMessage("Updating Profile Picture");


                    showFileChoose();

                    // Toast.makeText(getActivity(), "Not yet", Toast.LENGTH_SHORT).show();

                }else if(which == 1 ){
                    // Edit cover clicked
                    progressDialog.setMessage("Updating cover Picture");
                    showFileChoose_1();

                    // Toast.makeText(getActivity(), "Not yet", Toast.LENGTH_SHORT).show();


                }else if(which == 2){

                    // Edit name clicked
                    progressDialog.setMessage("Updating Name");
                    //calling methode and pass key "name" as parameter to updatate it's value in database
                    shoawnamephoneupdatedialog("name");


                }else if (which == 3){

                    // Edit phone clicked
                    progressDialog.setMessage("Updating Phone");
                    //calling methode and pass key "phone" as parameter to updatate it's value in database
                    shoawnamephoneupdatedialog1("phone");


                }

            }
        });
        builder.create().show();

    }

    private void showFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }
    private void showFileChoose_1() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();
            Picasso.get().load(imgUrl).into(avatarIv);


        }
        if (requestCode == CHOOSE_IMAGE1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            image_uri=data.getData();
            Picasso.get().load(image_uri).into(cover);


        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void shoawnamephoneupdatedialog(final String key) {

        //custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update"+key);
        //set layout of dialogue
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        //add edit text
        final EditText editText = new EditText(getActivity());
        editText.setHint("Enter "+ key);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        Query query = databaseReference.orderByChild("Uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // check util required data get
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //get data
                    String name_name   = ""+ds.child("name").getValue();
                    String phone_phone = ""+ds.child("phone").getValue();
                    editText.setText("  "+ name_name);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //add buttons in dialogue to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input text from edit text
                String value = editText.getText().toString().trim();
                //validate if user has entered something or not
                if(!TextUtils.isEmpty(value)){
                    progressDialog.dismiss();
                    HashMap<String,Object> result = new HashMap<>();
                    result.put(key,value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    progressDialog.dismiss();

                                    Toast.makeText(getActivity(),"Update",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
        //add buttons in dialogue to cancel

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        //creat and show dialog
        builder.create().show();
    }
    private void shoawnamephoneupdatedialog1(final String key) {

        //custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update"+key);
        //set layout of dialogue
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        //add edit text
        final EditText editText = new EditText(getActivity());
        editText.setHint("Enter "+ key);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        Query query = databaseReference.orderByChild("Uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // check util required data get
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //get data

                    String phone_phone = ""+ds.child("phone").getValue();
                    editText.setText("  "+ phone_phone);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //add buttons in dialogue to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input text from edit text
                String value = editText.getText().toString().trim();
                //validate if user has entered something or not
                if(!TextUtils.isEmpty(value)){
                    progressDialog.dismiss();
                    HashMap<String,Object> result = new HashMap<>();
                    result.put(key,value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    progressDialog.dismiss();

                                    Toast.makeText(getActivity(),"Update",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
        //add buttons in dialogue to cancel

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        //creat and show dialog
        builder.create().show();
    }
}
