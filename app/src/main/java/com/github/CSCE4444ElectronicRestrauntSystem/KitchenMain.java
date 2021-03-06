package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//Main Kitchen Page
//Todo: This is basically just a copy of the temporary CustomerMain
public class KitchenMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);

        Button AvailCheck = (Button) findViewById(R.id.bAvailability);
        AvailCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(KitchenMain.this, MenuAvailability.class);
                startActivity(newIntent);
            }
        });


        findList();
        findList2();
        findList3();
        Thread refreshFeed = new Thread(){
            public void run(){
                while(true){
                    try{
                        sleep(7000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    finally {
                        //Checks for updated Table status every 1 secs
                        findList();
                        findList2();
                        findList3();
                    }
                }
            }
        };
        refreshFeed.start();

        //Button bPayBill = (Button) findViewById(R.id.bPayBill);

        //bPayBill.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
            //    Intent iPayBill = new Intent(KitchenMain.this, PayOrders.class);
            //    startActivity(iPayBill);
            //}
        //});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kitchen_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void findList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");// query for info needed
        query.whereEqualTo("Status","Placed");
        //query.orderByDescending("Status");
        query.addDescendingOrder("createdAt");
        //query.orderByAscending("createdAt");

    // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orderItems, ParseException e) {
                KitchenAdapter adapter = new KitchenAdapter(orderItems);
                ListView lvNewOrderScroll = (ListView)findViewById(R.id.lvNewOrderScroll);  //set adapter

                lvNewOrderScroll.setAdapter(adapter);
            }
        });
    }

    public void findList2(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("Status","In Progress");
        //query.orderByDescending("Status");
        query.addDescendingOrder("createdAt");      //find each list for listviews
        //query.orderByAscending("createdAt");

        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orderItems, ParseException e) {
                KitchenAdapter adapter = new KitchenAdapter(orderItems);
                ListView lvInProgressOrderScroll = (ListView) findViewById(R.id.lvInProgressOrderScroll);
                lvInProgressOrderScroll.setAdapter(adapter);
            }
        });
    }

    public void findList3(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("Status","Ready");
        //query.orderByDescending("Status");
        query.addDescendingOrder("createdAt");
        //query.orderByAscending("createdAt");

        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orderItems, ParseException e) {
                KitchenAdapter adapter = new KitchenAdapter(orderItems);
                ListView lvReadyOrderScroll = (ListView) findViewById(R.id.lvReadyOrderScroll);
                lvReadyOrderScroll.setAdapter(adapter);
            }
        });
    }

    //This is where we need to pass order ID so that when you click an order if gets the right data
    public void orderInfo(View view) {
        Intent intent = new Intent(this, OrderInfo.class);
        //TextView tvOrderName = (TextView)view.findViewById(R.id.tvOrderStatus);
        //String orderName = tvOrderName.getText().toString();
        //intent.putExtra("OrderName", orderName);
        TextView tvobjectId = (TextView)view.findViewById(R.id.tvobjectId); //move to orderInfo page for changing status
        String ObjectId = tvobjectId.getText().toString();
        intent.putExtra("objectId", ObjectId);
        startActivity(intent);
    }

    public void MenuAvailability(View view) {
        Intent intent = new Intent(this, MenuAvailability.class);
        startActivity(intent);
    }


    private class KitchenAdapter extends ArrayAdapter<ParseObject> {// adapter for info in the listview in a way that is more intuitive

        // constructor
        public KitchenAdapter(List<ParseObject> objects) { super(KitchenMain.this, 0, objects); }

        // function called whenever the list is created or scrolled
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_kitchen_order, parent, false);
            }

            // get the current item
            ParseObject item = getItem(position);

            // get Order Time
            TextView tvOrderTime = (TextView)view.findViewById(R.id.tvOrderTime);
            //Date value = item.getCreatedAt();
            Date date = item.getCreatedAt();
            //String createdAt = String.valueOf(item.getDate("createdAt"));
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            String reportDate = df.format(date);                            //grabs date from parse and places in listview
            tvOrderTime.setText(reportDate);

            // get Table Number
            TextView tvTableNumber = (TextView)view.findViewById(R.id.tvTableNumber);
            String begin = "Table Number: ";
            String table = String.valueOf(item.getInt("TableNumber"));      //grabs table number from parse and places in listview
            String NewOutput = begin + table;
            tvTableNumber.setText(NewOutput);

            // get Items Ordered
            TextView tvItemsOrdered = (TextView)view.findViewById(R.id.tvItemsOrdered);
            String Items = String.valueOf(item.getJSONArray("ItemsOrdered"));
            String begin2 = "Items Ordered: \n";                            //grabs items ordered from parse and places in listview
            String NewOutput2 = begin2 + Items;
            tvItemsOrdered.setText(NewOutput2);

            // get Requests
            TextView tvRequests = (TextView)view.findViewById(R.id.tvRequests);
            String Items2 = String.valueOf(item.getJSONArray("Requests"));
            String begin3 = "\n Special Requests: \n";                      //grabs requests from parse and places in listview
            String NewOutput3 = begin3 + Items2;
            tvRequests.setText(NewOutput3);

            // get object ID for when it is clicked and we need this items info
            TextView tvObjectId = (TextView)view.findViewById(R.id.tvobjectId);
            //String Item = String.valueOf(item.getString("id"));
            String Item = item.getObjectId();                               //grabs orderID to be sent to orderInfo activity
            tvObjectId.setText(Item);                                       //in order to reference the spot in listview

            // return the view
            return view;
        }
    }
}

