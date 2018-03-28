package com.example.evan.androidviewertemplates.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.drawer_fragments.FirstPicklistFragmentOldBackup;
import com.example.evan.androidviewertools.utils.Constants;

import java.util.Map;

/**
 * Created by lockshaw on 3/26/18.
 */

public class PasswordDialogFragment extends DialogFragment {
    public interface PasswordDialogListener {
        public boolean checkPassword(DialogFragment dialog);
        public void onPasswordCorrect(DialogFragment dialog);
        public void onPasswordFail(DialogFragment dialog);
    }

    PasswordDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (PasswordDialogListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PasswordDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.passworddialog, null))
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Boolean didAuthenticate = listener.checkPassword(PasswordDialogFragment.this);
                        if (didAuthenticate) {
                            listener.onPasswordCorrect(PasswordDialogFragment.this);
                            PasswordDialogFragment.this.getDialog().dismiss();
                        } else {
                            listener.onPasswordFail(PasswordDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PasswordDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    final Dialog passwordDialog = new Dialog(context);

    Boolean value = false;
    final Button passwordButton = passwordDialog.findViewById(R.id.passwordButton);
                            passwordButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText passwordEditText = (EditText) passwordDialog.findViewById(R.id.passwordEditText);
            if (passwordEditText.getText().toString().equals("ahh")) {
                Log.e("passwordEditText:",passwordEditText.getText().toString());
                Log.e("Password is correct","Password inputed: "+passwordEditText.getText().toString());
                picklistEditButton.setText("YOU ARE IN EDIT MODE");
                Toast.makeText(getActivity(), "YOU ARE IN EDIT MODE", Toast.LENGTH_LONG).show();
                passwordDialog.dismiss();
                FirstPicklistFragmentOldBackup.picklistValue = true;
                Log.e("picklistValue pre.", FirstPicklistFragmentOldBackup.picklistValue.toString());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                        TextView teamNumberTextView = (TextView)view.findViewById(R.id.teamNumber);
                        final Integer teamString = Integer.parseInt(teamNumberTextView.getText().toString());
                        Log.e("teamString",teamString.toString());
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.picklistdialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button upButton = (Button) dialog.findViewById(R.id.upButton);
                        Button downButton = (Button) dialog.findViewById(R.id.downButton);
                        upButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //upButton onClick
                                Integer myTeam = getKeyByValue(Constants.picklistMap, teamString.toString());
                                Integer otherTeam = myTeam - 1;
                                Map<Integer, String> onClickMap = sortByValue(Constants.picklistMap);
                                String extraValue;
                                extraValue = onClickMap.get(myTeam);
                                String team = onClickMap.get(otherTeam);
                                Constants.picklistMap.put(myTeam, onClickMap.get(otherTeam));
                                Constants.picklistMap.put(otherTeam, extraValue);
                                if (myTeam > 0) {
                                    dref.child("picklist").child(myTeam.toString()).setValue(Integer.parseInt(Constants.picklistMap.get(myTeam)));
                                    dref.child("picklist").child(otherTeam.toString()).setValue(Integer.parseInt(Constants.picklistMap.get(otherTeam)));
                                } else {
                                    Toast.makeText(getActivity(), "Nice try.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        downButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //downButton onClick
                                Constants.counter = Constants.counter + 1;
                                Integer myTeam = getKeyByValue(Constants.picklistMap, teamString.toString());
                                Integer otherTeam = myTeam + 1;
                                Map<Integer, String> onClickMap = sortByValue(Constants.picklistMap);
                                String extraValue;
                                extraValue = onClickMap.get(otherTeam);
                                String team = onClickMap.get(myTeam);
                                Constants.picklistMap.put(otherTeam, onClickMap.get(myTeam));
                                Constants.picklistMap.put(myTeam, extraValue);
                                if (myTeam < 65) {
                                    dref.child("picklist").child(myTeam.toString()).setValue(Integer.parseInt(Constants.picklistMap.get(myTeam)));
                                    dref.child("picklist").child(otherTeam.toString()).setValue(Integer.parseInt(Constants.picklistMap.get(otherTeam)));
                                } else {
                                    Toast.makeText(getActivity(), "Nice try.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            } else {
                Log.e("Password is incorrect","Password inputed: "+passwordEditText.getText().toString());
                Toast.makeText(getActivity(), "hacking = bad",
                        Toast.LENGTH_LONG).show();
                passwordEditText.getText().clear();
            }
        }
    });
}
