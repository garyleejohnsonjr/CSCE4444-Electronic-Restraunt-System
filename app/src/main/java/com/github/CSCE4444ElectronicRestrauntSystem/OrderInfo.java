package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class OrderInfo extends AppCompatActivity {
    int calories;

    // activity creation event
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_order_info);

        // get order status and set to order status textview in acivity_order_info.xml


        // get table number and set to table number textview in acivity_order_info.xml


        // get items ordered and put that in items ordered textview convert from array acivity_order_info.xml


        // get special requests string and set to requests textview also in acivity_order_info.xml
    }

    // set order as in progress
    public void SetInProgress(View view) {

    }

    // set order as ready
    public void SetReady(View view) {

    }

    // exit back to kitchen screen
    public void SetToKitchen(View view) {

    }
}
