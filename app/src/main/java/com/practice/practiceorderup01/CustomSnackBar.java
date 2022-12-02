package com.practice.practiceorderup01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {

    public static void ShowSnackBar(Context context, View view, String message, int imageId){

        try{
            //create the snackbar to manipulate
            Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);

            //retrieve the view from snackbar
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

            //access the stock textview in the snackbar and make it invisible
            TextView textView = (TextView) layout.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setVisibility(View.INVISIBLE);

            //create a new View inflator for the XML provided, and apply image id to the imageview in the xml
            View snackBarView = LayoutInflater.from(context).inflate(R.layout.snackbar_custom, null);
            ImageView image = snackBarView.findViewById(R.id.snackImage01);
            image.setImageResource(imageId);

            //set the message
            TextView snackMessage = snackBarView.findViewById(R.id.snackMessage);
            snackMessage.setText(message);

            //current setting for the click button is to dismiss the snackbar
            Button button = snackBarView.findViewById(R.id.snackButton);
            button.setVisibility(View.GONE);

            //Set the padding of the snackbar, add the view, and execute
            layout.setPadding(1, 2, 1, 2);
            layout.addView(snackBarView, 0);
            snackbar.setDuration(2000);
            snackbar.show();

        } catch(Exception ex){
            //Should I add something here
        }
    }

}
