package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ServerTableOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_table_order);

        /*final Intent i = getIntent();
        int tableNum = 0;

        //gets table number and finds in database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tables");
        query.whereEqualTo("Number", i.getIntExtra("Number", tableNum));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> tables,
                                     com.parse.ParseException e) {
                //Todo: Submit nothing error
                if (e == null) {
                    for (ParseObject table : tables) {
                        Log.d("Tables", "True");
                    }
                } else {
                    //Failed Query Log
                    Log.d("Tables", "Error: " + e.getMessage());
                }
            }
        });

*/
        final Intent i = getIntent();
        final int tableNum = 0;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("TableNumber", i.getIntExtra("Number", tableNum));
        query.whereNotEqualTo("Status", "Paid");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orders,
                             com.parse.ParseException e) {
                //Todo: Submit nothing error
                if (e == null) {
                    int x = 0;
                    for (ParseObject order: orders) {
                        if( x == i.getIntExtra("Order", tableNum)) {
                            TableAdapter adapter = new TableAdapter(order.getList("ItemsOrdered"));
                            ListView lvTableOrder = (ListView) findViewById(R.id.lvTableOrder);
                            lvTableOrder.setAdapter(adapter);
                        }
                        x++;
                    }
                }
             else {
                //Failed Query Log
                Log.d("Tables", "Error: " + e.getMessage());
            }
        }
    });
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_table_order, menu);

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

    private class TableAdapter extends ArrayAdapter<Object> {
        // constructor
        public TableAdapter(List<Object> objects) { super(ServerTableOrder.this, 0, objects); }

        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_submit_item, parent, false);
            }

            // get the item

           String item= getItem(position).toString();
            // get item name
            TextView tvItemName = (TextView)view.findViewById(R.id.tvItemName);
            tvItemName.setText(item);


            // get price

            final TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
            query.whereEqualTo("ItemName", item);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> menuitems,
                                 com.parse.ParseException e) {
                    //Todo: Submit nothing error
                    if (e == null) {
                        for (ParseObject menuitem : menuitems) {
                            Double price = menuitem.getDouble("Price");
                            String formattedPrice = String.format("$%.2f", price);
                            if (formattedPrice.equals("$0.00")) formattedPrice = "Free";
                            tvPrice.setText(formattedPrice);
                        }
                    }
                }
            });
            return view;

        }
    }
}
