package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Activity;
import android.content.ClipData;
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
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mark on 11/5/2015.
 */

public class MenuAvailability extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_availability);

        findList();
        Thread refreshFeed = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        //Checks for updated Table status every 1 secs
                        findList();
                    }
                }
            }
        };
        refreshFeed.start();
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

    public void findList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
        //query.whereEqualTo("Type", "Placed");
        query.orderByDescending("Type");
        //query.addAscendingOrder("createdAt");
        //query.orderByAscending("createdAt");

        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orderItems, ParseException e) {
                AvailabilityAdapter adapter = new AvailabilityAdapter(orderItems);
                ListView lvMenuAvailability = (ListView)findViewById(R.id.lvMenuAvailability);
                lvMenuAvailability.setAdapter(adapter);
            }
        });
    }

    public class AvailabilityAdapter extends ArrayAdapter<ParseObject> {

        // constructor
        public AvailabilityAdapter(List<ParseObject> objects) { super(MenuAvailability.this, 0, objects); }

        // function called whenever the list is created or scrolled
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_availability_adapter, parent, false);
            }

            // get the current item
            ParseObject item = getItem(position);


            // get name
            TextView tvMenuItem = (TextView)view.findViewById(R.id.tvMenuItem);
            //String begin = "Table Number: ";
            String name = String.valueOf(item.getString("ItemName"));
            //String NewOutput = begin + table;
            //Intent intent = new Intent(this, here);
            //intent.putExtra("ItemName", name);
            //////////////////
            tvMenuItem.setText(name);

            // get availability
            TextView tvAvailability = (TextView)view.findViewById(R.id.tvAvailability);
            String avail = String.valueOf(item.getBoolean("Avalibility"));
            String yes = "Available";
            String no = "Not Available";
            if(avail == "true") {
                tvAvailability.setText(yes);
            }else if(avail == "false"){
                tvAvailability.setText(no);
            }

            // return the view
            return view;
        }
    }

    public void Toggle(View view) {
        //Intent intent = new Intent(this, MenuAvailability.class);
        TextView tvMenuItem = (TextView)view.findViewById(R.id.tvMenuItem);
        final String name2 = tvMenuItem.getText().toString();
        TextView tvAvailability = (TextView)view.findViewById(R.id.tvAvailability);
        final String avail2 = tvAvailability.getText().toString();

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
        query.whereEqualTo("ItemName", name2);

        query.getFirstInBackground(new GetCallback<ParseObject>() {

            public void done(ParseObject item, ParseException e) {
                try {
                    if(avail2.equals("Available")){
                        ParseObject o = query.get(name2);
                        o.put("Avalibility", "false");
                        o.saveInBackground();

                        Toast.makeText(MenuAvailability.this, "Menu item toggled off.", Toast.LENGTH_LONG).show();
                    }

                } catch (ParseException p) {
                }
            }
        });
    }
}
