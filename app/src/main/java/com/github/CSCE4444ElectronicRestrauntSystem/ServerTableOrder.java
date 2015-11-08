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
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ServerTableOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_server_table_order);
        final Intent i = getIntent();
        final int tableNum = 0;


        //Formats the Order Adapter
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
                    for (ParseObject order : orders) {
                        if (x == i.getIntExtra("Order", tableNum)) {
                            TableAdapter adapter = new TableAdapter(order.getList("ItemsOrdered"));
                            ListView lvTableOrder = (ListView) findViewById(R.id.lvTableOrder);
                            lvTableOrder.setAdapter(adapter);

                            //Gets Total Price
                            final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
                            final TextView tvAdjustments = (TextView) findViewById(R.id.tvAdjustments);
                            final ParseObject update = order;

                            //Gets total and adjustments
                            String formattedAdjustments = String.format("$%.2f", order.getDouble("Adjustments"));
                            String formattedTotalPrice = String.format("$%.2f", order.getDouble("Total") - order.getDouble("Adjustments"));
                            tvAdjustments.setText("Adjustments: -" + formattedAdjustments);
                            tvTotalPrice.setText("Total: " + formattedTotalPrice);

                            Button bSelfAdjust = (Button) findViewById(R.id.bSelfAdjust);
                            Button bPayBill = (Button) findViewById(R.id.bPay);

                            final ParseObject id = order;

                            //Launches New activity with object id
                            bSelfAdjust.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent iAdjustments = new Intent(ServerTableOrder.this, ManualAdjustments.class);
                                    iAdjustments.putExtra("objectId", id.getObjectId());
                                    startActivity(iAdjustments);
                                }

                            });

                            //Sends to Pay Bill Page
                            bPayBill.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent iPay = new Intent(ServerTableOrder.this, PayOrder.class);
                                    iPay.putExtra("OrderID", id.getObjectId());
                                    iPay.putExtra("Server", "true");
                                    startActivity(iPay);
                                    finish();
                                }

                            });
                        }
                        x++;
                    }
                } else {
                    //Failed Query Log
                    Log.d("Tables", "Error: " + e.getMessage());
                }
            }
        });

}
    @Override
    public void onResume(){
        super.onResume();

        setContentView(R.layout.activity_server_table_order);
        final Intent i = getIntent();
        final int tableNum = 0;


        //Formats the Order Adapter
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
                    for (ParseObject order : orders) {
                        if (x == i.getIntExtra("Order", tableNum)) {
                            TableAdapter adapter = new TableAdapter(order.getList("ItemsOrdered"));
                            ListView lvTableOrder = (ListView) findViewById(R.id.lvTableOrder);
                            lvTableOrder.setAdapter(adapter);

                            //Gets Total Price
                            final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
                            final TextView tvAdjustments = (TextView) findViewById(R.id.tvAdjustments);
                            final ParseObject update = order;

                            //Gets total and adjustments
                            String formattedAdjustments = String.format("$%.2f", order.getDouble("Adjustments"));
                            String formattedTotalPrice = String.format("$%.2f", order.getDouble("Total") - order.getDouble("Adjustments"));
                            tvAdjustments.setText("Adjustments: -" + formattedAdjustments);
                            tvTotalPrice.setText("Total: " + formattedTotalPrice);

                            Button bSelfAdjust = (Button) findViewById(R.id.bSelfAdjust);
                            Button bPayBill = (Button) findViewById(R.id.bPay);

                            final ParseObject id = order;

                            //Launches New activity with object id
                            bSelfAdjust.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent iAdjustments = new Intent(ServerTableOrder.this, ManualAdjustments.class);
                                    iAdjustments.putExtra("objectId", id.getObjectId());
                                    startActivity(iAdjustments);
                                }

                            });

                            //Sends to Pay Bill Page
                            bPayBill.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent iPay = new Intent(ServerTableOrder.this, PayOrder.class);
                                    iPay.putExtra("OrderID", id.getObjectId());
                                    iPay.putExtra("Server", "true");
                                    startActivity(iPay);
                                    finish();
                                }

                            });

                        }
                        x++;
                    }
                } else {
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
    // remove item event
    public void compItem(View view) {

        ListView lvTableOrder = (ListView) findViewById(R.id.lvTableOrder);

        // get item to remove
        final int position = lvTableOrder.getPositionForView(view);
        final Intent i = getIntent();
        final int tableNum = 0;

        //Formats the Order Adapter
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
                    for (ParseObject order : orders) {
                        if (x ==  i.getIntExtra("Order", tableNum)) {
                                final ParseObject price = order;

                                ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
                                List<String> items = order.getList("ItemsOrdered");

                                int y = 0;
                                for(String item: items){
                                    if (y == position)
                                        query.whereEqualTo("ItemName",item);
                                    y++;
                                }
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> menuitems,
                                                     com.parse.ParseException e) {
                                        for(ParseObject menuitem : menuitems){

                                            //adds Menu item Adjust to intent
                                            Intent i = new Intent(ServerTableOrder.this, ManualAdjustments.class);
                                            i.putExtra("objectId", price.getObjectId());
                                            i.putExtra("Adjustments", menuitem.getDouble("Price"));
                                            startActivity(i);
                                        }
                                    }

                                });
                        }
                        x++;
                    }
                }
            }
        });
    }

    private class TableAdapter extends ArrayAdapter<Object> {
        // constructor
        public TableAdapter(List<Object> objects) {
            super(ServerTableOrder.this, 0, objects);
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_comp_item, parent, false);
            }
            // get the item
            String item = getItem(position).toString();
            // get item name and puts on page
            TextView tvItemName = (TextView) view.findViewById(R.id.tvItemName);
            tvItemName.setText(item);

            //Gets requests and puts on page
            final TextView tvRequest = (TextView) view.findViewById(R.id.tvRequest);
            final Intent i = getIntent();
            final int tableNum = 0;

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
            query.whereEqualTo("TableNumber", i.getIntExtra("Number", tableNum));
            query.whereNotEqualTo("Status", "Paid");
            query.orderByAscending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> orders,
                                 com.parse.ParseException e) {
                    if (e == null) {
                        int x = 0;
                        for (ParseObject order : orders) {
                            if (x == i.getIntExtra("Order", tableNum)) {
                                List<String> requests = order.getList("Requests");
                                int y = 0;
                                for(String request:requests ) {
                                    if (y == position)
                                        tvRequest.setText(request);
                                    y++;
                                }
                            }
                            x++;
                        }
                    }
                }
            });

            // Get price and puts on page
            final TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);

            ParseQuery<ParseObject> Newquery = ParseQuery.getQuery("MenuItem");
            Newquery.whereEqualTo("ItemName", item);
            Newquery.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> menuitems,
                                 com.parse.ParseException e) {
                    //Todo: Submit nothing error
                    if (e == null) {
                        for (ParseObject menuitem : menuitems) {
                            Double price = menuitem.getDouble("Price");
                            String formattedPrice = String.format("$%.2f", price);
                            if (formattedPrice.equals("$0.00"))
                                formattedPrice = "Free";
                            tvPrice.setText(formattedPrice);

                        }
                    }
                }
            });
            return view;
        }
    }

}
