package com.github.CSCE4444ElectronicRestrauntSystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

public class CallServer extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_server);

        // build orders query
        ParseQuery<ParseObject> ordersQuery = ParseQuery.getQuery("Order");
        MainApplication application = (MainApplication)getApplication();
        ordersQuery.whereEqualTo("TableNumber", application.currentTable);
        ordersQuery.whereNotEqualTo("Status", "Completed");

        // run query in background
        ordersQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override public void done(final List<ParseObject> orders, ParseException e) {

                // build refillable query
                ParseQuery<ParseObject> itemsQuery = ParseQuery.getQuery("MenuItem");
                itemsQuery.whereEqualTo("Refillable", true);


                itemsQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> items, ParseException e) {
                        LinkedList<String> refillables = new LinkedList<>();
                        for (ParseObject item : items) refillables.add(item.getString("ItemName"));

                        LinkedList<String> refillablesInOrders = new LinkedList<>();
                        for (ParseObject order : orders) {
                            for (Object item : order.getList("ItemsOrdered")) {
                                if (refillables.contains(item))
                                    refillablesInOrders.add((String) item);
                            }
                        }

                        RefillAdapter adapter = new RefillAdapter(refillablesInOrders);
                        ListView lvRefills = (ListView) findViewById(R.id.lvRefills);
                        lvRefills.setAdapter(adapter);
                    }
                });

            }
        });
    }



    private class RefillAdapter extends ArrayAdapter<String> {

        // constructor
        public RefillAdapter(List<String> strings) { super(CallServer.this, 0, strings); }

        // function called whenever the list is created or scrolled
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_call_item, parent, false);
            }

            // get the current item
            String string = getItem(position);

            // get item name
            CheckBox tvItemName = (CheckBox)view.findViewById(R.id.cbItemName);
            tvItemName.setText(string);

            // return the view
            return view;
        }
    }

}
