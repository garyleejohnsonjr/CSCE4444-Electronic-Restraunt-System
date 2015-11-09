package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.Ref;
import java.util.List;

public class TableStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_status);

        Button bStatusSubmit = (Button) findViewById(R.id.bStatusSubmit);
        final Intent i = getIntent();
        final TextView tvRequestField = (TextView) findViewById(R.id.tvRequestField);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tables");
        query.whereEqualTo("Number", i.getIntExtra("Number", 0));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> tables,
                             ParseException e) {
                //Todo: Submit nothing error
                if (e == null) {
                    for (ParseObject table : tables) {
                        //Sets text for Request Field
                        if (table.containsKey("Requests"))
                            tvRequestField.setText(table.getString("Requests"));
                        if(table.containsKey("Refills")){
                            RefillAdapter adapter = new RefillAdapter(table.getList("Refills"));
                            ListView lvRefills = (ListView) findViewById(R.id.lvRefills);
                            lvRefills.setAdapter(adapter);
                        }
                    }
                }
            }
        });

        //Submits Table status
        //Todo: Add cancel Button
        bStatusSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Finds selected button
                final RadioGroup status = (RadioGroup) findViewById(R.id.rbTables);
                int selectedStatus = status.getCheckedRadioButtonId();
                int tableNum = 0;
                final RadioButton newStatus = (RadioButton) findViewById(selectedStatus);

                //gets table number and finds in database
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Tables");
                query.whereEqualTo("Number", i.getIntExtra("Number", tableNum));
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> tables,
                                     ParseException e) {
                        if (e == null) {
                            for (ParseObject table : tables) {

                                if(!newStatus.getText().equals("Table Help") && table.getString("Status").equals("Table Help")){
                                    //Removes Requests and Refills if they have Helped Customer
                                    if(table.containsKey("Refills"))
                                        table.remove("Refills");
                                    if(table.containsKey("Requests"))
                                        table.remove("Requests");
                                }
                                //Puts Status into database
                                table.put("Status", newStatus.getText());
                                table.saveInBackground();
                            }
                        } else {
                            //Failed Query Log
                            Log.d("Tables", "Error: " + e.getMessage());
                        }
                    }
                });
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table_status, menu);
        return true;
    }

     @Override
     public boolean onOptionsItemSelected (MenuItem item){
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
    private class RefillAdapter extends ArrayAdapter<Object> {
        // constructor
        public RefillAdapter(List<Object> objects) {
            super(TableStatus.this, 0, objects);
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_refill_status, parent, false);
            }
            // get the item
            String item = getItem(position).toString();
            // get item name and puts on page
            TextView tvRefillChoice = (TextView) view.findViewById(R.id.tvRefillChoice);
            tvRefillChoice.setText(item);

            return view;
        }
    }

}