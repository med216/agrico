package com.example.acrigooo.auth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SomeDialog extends DialogFragment {
    FirebaseAuth fAuth;
    //loginfragemnt loginfragemnt;
    Context context;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText reseat = new EditText(getContext());
        fAuth = FirebaseAuth.getInstance();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Reset Password !")
                .setMessage("Enter Your Email To Received Reset Link")
                .setView(reseat)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)

                    }
                })
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Extract email and send email link
                        String mail = reseat.getText().toString() ;
                       Log.d(TAG, "onClick: paas1 ");
                        final Task<Void> voidTask = fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                               // Log.d(TAG, "onClick: paas2 ");
                               Toast.makeText(getContext(), "Reset link send to your Email", Toast.LENGTH_SHORT).show();
                               Log.d(TAG, "onClick: paas3 ");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onClick: paas4 ");
                                // close dialoge
                              //  Toast.makeText(getContext(), " Error Reset link is not  send to your Email", Toast.LENGTH_SHORT).show();
                               Log.d(TAG, "onClick: paas5 ");
                            }
                        });

                    }
                })
                .create();
    }


}
