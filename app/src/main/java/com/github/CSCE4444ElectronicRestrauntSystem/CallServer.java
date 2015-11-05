package com.github.CSCE4444ElectronicRestrauntSystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
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
            @Override
            public void done(final List<ParseObject> orders, ParseException e) {

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


    public void callServer(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tables");
        MainApplication application = (MainApplication)getApplication();
        query.whereEqualTo("Number", application.currentTable);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override public void done (ParseObject table, ParseException e) {
                table.put("Status", "Table Help");

                ListView lvRefills = (ListView)findViewById(R.id.lvRefills);
                RefillAdapter adapter = (RefillAdapter)lvRefills.getAdapter();

                table.put("Refills", adapter.selectedItems);

                EditText etRequests = (EditText)findViewById(R.id.etRequests);
                String requests = etRequests.getText().toString();
                table.put("Requests", requests);

                table.saveInBackground();
                Toast.makeText(getApplicationContext(), "Server called.", Toast.LENGTH_LONG).show();
            }
        });
    }


    private class RefillAdapter extends ArrayAdapter<String> {

        // constructor
        public RefillAdapter(List<String> strings) { super(CallServer.this, 0, strings); }

        public ArrayList<String> selectedItems = new ArrayList<String>();

        // function called whenever the list is created or scrolled
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_call_item, parent, false);
            }

            // get the current item
            String string = getItem(position);

            // get item name
            CheckBox cbItemName = (CheckBox)view;
            cbItemName.setText(string);

            cbItemName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectedItems.add(buttonView.getText().toString());
                    } else {
                        selectedItems.remove(buttonView.getText().toString());
                    }
                }
            });

            // return the view
            return view;
        }
    }

}
