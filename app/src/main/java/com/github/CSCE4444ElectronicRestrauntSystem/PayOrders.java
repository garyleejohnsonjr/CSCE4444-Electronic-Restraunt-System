package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

public class PayOrders extends AppCompatActivity {

    LinkedList<String> orderIDs = new LinkedList<>();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_orders);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        MainApplication application = (MainApplication)getApplication();
        query.whereEqualTo("TableNumber", application.currentTable);
        query.whereEqualTo("Paid", false);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orders, ParseException e) {
                OrderAdapter adapter = new OrderAdapter(orders);
                ListView lvOrders = (ListView) findViewById(R.id.lvOrders);
                lvOrders.setAdapter(adapter);
            }
        });
    }

    // pay order event
    public void payOrder(View view) {
        Intent intent = new Intent(this, PayOrder.class);
        ListView lvOrders = (ListView) findViewById(R.id.lvOrders);
        int position = lvOrders.getPositionForView(view);
        String orderID = orderIDs.get(position);
        intent.putExtra("OrderNumber", position);
        intent.putExtra("OrderID", orderID);
        startActivity(intent);
    }

    // call server event
    public void callServer(View view) {
        Intent intent = new Intent(this, CallServer.class);
        startActivity(intent);
    }

    // nested class used for the menu adapter
    private class OrderAdapter extends ArrayAdapter<ParseObject> {

        // constructor
        public OrderAdapter(List<ParseObject> objects) { super(PayOrders.this, 0, objects); }

        // function called whenever the list is created or scrolled
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_pay_orders_item, parent, false);
            }

            // get the current item
            ParseObject item = getItem(position);

            // get order id
            TextView tvOrderID = (TextView)view.findViewById(R.id.tvOrderID);
            String orderID = item.getObjectId();
            orderIDs.addLast(orderID);

            tvOrderID.setText("Order #" + (position + 1));

            // return the view
            return view;
        }
    }

}
