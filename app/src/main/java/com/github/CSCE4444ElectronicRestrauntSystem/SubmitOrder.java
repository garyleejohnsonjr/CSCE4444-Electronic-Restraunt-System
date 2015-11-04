package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

public class SubmitOrder extends AppCompatActivity {
    // TODO: submit order button

    float totalPrice = 0;

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_submit_order);
        MainApplication application = (MainApplication)getApplicationContext();
        SubmitAdapter adapter = new SubmitAdapter(application.currentOrder);
        ListView lvSubmitOrder = (ListView)findViewById(R.id.lvSubmitOrder);
        lvSubmitOrder.setAdapter(adapter);

        // total price
        TextView tvTotalPrice = (TextView)findViewById(R.id.tvTotalPrice);
        totalPrice = 0;
        for (OrderItem item : application.currentOrder) totalPrice += item.price;
        String formattedTotalPrice = String.format("$%.2f", totalPrice);
        tvTotalPrice.setText("Total: " + formattedTotalPrice);
    }

    // remove item event
    public void removeItem(View view) {
        // get adapter
        ListView lvSubmitOrder = (ListView) findViewById(R.id.lvSubmitOrder);
        SubmitAdapter adapter = (SubmitAdapter)lvSubmitOrder.getAdapter();

        // get item to remove
        int position = lvSubmitOrder.getPositionForView(view);
        OrderItem currentItem = adapter.getItem(position);

        // remove item
        adapter.remove(currentItem);

        // update total price
        totalPrice -= currentItem.price;
        TextView tvTotalPrice = (TextView)findViewById(R.id.tvTotalPrice);
        String formattedTotalPrice = String.format("$%.2f", totalPrice);
        tvTotalPrice.setText("Total: " + formattedTotalPrice);
    }

    // call server event
    public void callServer(View view) {
        Intent intent = new Intent(this, CallServer.class);
        startActivity(intent);
    }

    // submit order event
    public void submitOrder(View view) {
        // make sure there's at least one item in the order
        MainApplication application = (MainApplication)getApplicationContext();
        if (application.currentOrder.size() > 0) {


            new AlertDialog.Builder(this)
                .setTitle("Order Confirmation")
                .setMessage("Are you sure you want to submit this order? Once the order is submitted it cannot be changed.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // make a new parse object
                        ParseObject order = new ParseObject("Order");

                        // make a list of items ordered and requests
                        LinkedList<String> itemsOrdered = new LinkedList<>();
                        LinkedList<String> requests = new LinkedList<>();
                        MainApplication application = (MainApplication)getApplicationContext();
                        for (OrderItem item : application.currentOrder) {
                            itemsOrdered.addLast(item.name);
                            requests.addLast(item.request);
                        }

                        // build the order
                        order.put("ItemsOrdered", itemsOrdered);
                        order.put("Requests", requests);
                        order.put("Status", "Placed");
                        order.put("TableNumber", 1);
                        order.put("Total", totalPrice);

                        // save the order to the database
                        order.saveInBackground();

                        final  ParseObject x  = order;

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tables");
                        query.whereEqualTo("Number", 1);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> tables, ParseException e) {
                                //Todo: Submit nothing error
                                if (e == null) {
                                    for (ParseObject table : tables) {
                                        table.put("CurrentOrder", x);
                                        Log.d("Tables", "Submit " + x.getString("Status"));
                                        table.saveInBackground();
                                    }
                                } else {
                                    //Failed Query Log
                                    Log.d("Tables", "Error: " + e.getMessage());
                                }
                            }
                        });

                        // clear the current order
                        application.currentOrder.clear();

                        // display toast
                        String toast = "Order submitted.";
                        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();

                        // return to the menu
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Order empty!", Toast.LENGTH_LONG).show();
        }
    }

    private class SubmitAdapter extends ArrayAdapter<OrderItem> {
        // constructor
        public SubmitAdapter(List<OrderItem> items) { super(SubmitOrder.this, 0, items); }

        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_submit_item, parent, false);
            }

            // get the item
            OrderItem item = getItem(position);

            // get item name
            TextView tvItemName = (TextView)view.findViewById(R.id.tvItemName);
            tvItemName.setText(item.name);

            // get request
            TextView tvRequest = (TextView)view.findViewById(R.id.tvRequest);
            tvRequest.setText(item.request);
            LinearLayout layout = (LinearLayout)tvRequest.getParent().getParent();
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            if (item.request.equals("")) params.height = 0;
            else params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layout.setLayoutParams(params);

            // get price
            TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);
            float price = item.price;
            String formattedPrice = String.format("$%.2f", price);
            if (formattedPrice.equals("$0.00")) formattedPrice = "Free";
            tvPrice.setText(formattedPrice);

            return view;
        }
    }

}
