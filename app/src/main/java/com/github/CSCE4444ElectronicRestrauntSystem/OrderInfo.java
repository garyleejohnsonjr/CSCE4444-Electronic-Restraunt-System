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
    @Override public void onCreate(Bundle savedInstanceState) {     //create the order info page and set the views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_order_info);

        String objectId = getIntent().getStringExtra("objectId");   //query parse for your items info
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("objectId", objectId);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject item, ParseException e) {
                // get order status and set to order status textview in acivity_order_info.xml
                TextView tvOrderStatus = (TextView) findViewById(R.id.tvOrderStatus);
                String TabStat = String.valueOf(item.getString("Status"));  //format info the way we need it
                String begin4 = "Table Status: ";
                String NewOutput4 = begin4 + TabStat;
                tvOrderStatus.setText(NewOutput4);

                // get table number and set to table number textview in acivity_order_info.xml
                TextView tvTableNumber = (TextView) findViewById(R.id.tvTableNumber);
                String TabNum = String.valueOf(item.getInt("TableNumber"));
                String begin3 = "Table Number: ";                           //grab table number and format
                String NewOutput3 = begin3 + TabNum;
                tvTableNumber.setText(NewOutput3);

                // get items ordered and put that in items ordered textview convert from array acivity_order_info.xml
                TextView tvItemsOrdered = (TextView) findViewById(R.id.tvItemsOrdered);
                String Items = String.valueOf(item.getJSONArray("ItemsOrdered"));
                String begin = "Items Ordered: \n";                         //grab items ordered and format
                String NewOutput = begin + Items;
                tvItemsOrdered.setText(NewOutput);


                // get special requests string and set to requests textview also in acivity_order_info.xml
                TextView tvRequests = (TextView) findViewById(R.id.tvSpecialRequests);
                String Items2 = String.valueOf(item.getJSONArray("Requests"));
                String begin2 = "\n Special Requests: \n";                  //grab requests and format
                String end = "\n";
                String NewOutput2 = begin2 + Items2 + end;
                tvRequests.setText(NewOutput2);

                if(TabStat.equals("Placed")){
                    Button bMoveIP = (Button) findViewById(R.id.bMoveIP);   //set buttons to not enabled unless told otherwise
                    bMoveIP.setEnabled(true);
                } else if(TabStat.equals("In Progress")) {
                    Button bMoveRdy = (Button) findViewById(R.id.bMoveRdy);
                    bMoveRdy.setEnabled(true);
                } else if(TabStat.equals("Ready")) {
                    Button bMoveIP = (Button) findViewById(R.id.bMoveIP);
                    bMoveIP.setEnabled(true);
                }
            }
        });
    }

    // set order as in progress
    public void SetInProgress(View view) {
        final String objectId = getIntent().getStringExtra("objectId");
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Order"); //button sets in progress available and allows
        query.whereEqualTo("objectId", objectId);                           //the user to set items to in progress

        query.getFirstInBackground(new GetCallback<ParseObject>() {

            public void done(ParseObject item, ParseException e) {
                try {
                    ParseObject o = query.get(objectId);
                    o.put("Status", "In Progress");
                    o.saveInBackground();                                    //send to parse

                    Toast.makeText(OrderInfo.this, "Status has been set to 'In Progress'", Toast.LENGTH_LONG).show();
                } catch (ParseException p) {
                }
            }
        });
        Intent intent = new Intent(this, KitchenMain.class);
        startActivity(intent);
    }


    // set order as ready
    public void SetReady(View view) {
        final String objectId = getIntent().getStringExtra("objectId");
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Order"); //same as in progress but for ready setting
        query.whereEqualTo("objectId", objectId);

        query.getFirstInBackground(new GetCallback<ParseObject>() {

            public void done(ParseObject item, ParseException e) {
                try {
                    ParseObject o = query.get(objectId);
                    o.put("Status", "Ready");
                    o.saveInBackground();

                    Toast.makeText(OrderInfo.this, "Status has been set to 'Ready'", Toast.LENGTH_LONG).show();
                } catch (ParseException p) {
                }
            }
        });
        Intent intent = new Intent(this, KitchenMain.class);
        startActivity(intent);
    }

    // exit back to kitchen screen
    public void SetToKitchen(View view) {
        Intent intent = new Intent(this, KitchenMain.class);
        startActivity(intent);
    }
}
