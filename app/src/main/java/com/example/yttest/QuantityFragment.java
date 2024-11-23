package com.example.yttest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class QuantityFragment extends DialogFragment {

    public interface QuantityDialogListener {
        void onQuantityConfirmed(String quantity);
    }

    private EditText editQuantity;
    private QuantityDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for the dialog
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.quantity_dialogue, null);

        // Initialize views
        editQuantity = view.findViewById(R.id.editQuantity);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);

        // Set click listener for the confirm button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the listener with the entered quantity
                if (listener != null && !editQuantity.getText().toString().isEmpty()) {

                   try{

                       // Create or get the SharedPreferences object
                       SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Pref", getContext().MODE_PRIVATE);

                       SharedPreferences.Editor editor = sharedPreferences.edit();





                       Double quantity = Double.parseDouble(String.valueOf(editQuantity.getText().toString().charAt(0)));

                       editor.putInt("quantityhehe", Integer.parseInt(String.valueOf(editQuantity.getText().toString().charAt(0))));

                       // Apply the changes asynchronously


                       // Commit the changes
                       editor.apply();

                       listener.onQuantityConfirmed(editQuantity.getText().toString());
                       dismiss();
                   }catch(NumberFormatException e){

                   }
                }
                // Dismiss the dialog

            }
        });

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        return builder.create();
    }

    public void setQuantityDialogListener(QuantityDialogListener listener) {
        this.listener = listener;
    }
}
