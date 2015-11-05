package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ManualAdjustments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_adjustments);

        final EditText etAdjustments = (EditText) findViewById(R.id.etAdjustment);
        final TextView etComments = (TextView) findViewById(R.id.etComments);
        Button bAdjustSubmit = (Button) findViewById(R.id.bAdjustSubmit);
        final Intent i = getIntent();

        //Query To Look For Previous adjustment Comments
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("objectId", i.getStringExtra("objectId"));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orders,
                             ParseException e) {
                if (e == null) {
                    for (ParseObject order : orders) {
                        int num = 0;
                       if(!order.getString("AdjustmentComments").isEmpty())
                           etComments.setText(order.getString("AdjustmentComments"));
                        if(i.getDoubleExtra("Adjustments", num) != 0)
                            etAdjustments.setText(Double.toString(i.getDoubleExtra("Adjustments", num)));
                    }
                }
            }
        });

        bAdjustSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
                query.whereEqualTo("objectId", i.getStringExtra("objectId"));
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> orders,
                                     ParseException e) {
                        if (e == null) {
                            for (ParseObject order : orders) {
                                order.put("Adjustments", (order.getDouble("Adjustments") + Double.parseDouble(etAdjustments.getText().toString())));
                                order.put("AdjustmentComments", etComments.getText().toString());
                                order.saveInBackground();
                            }
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
                    getMenuInflater().inflate(R.menu.menu_manual_adjustments, menu);
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
            }
