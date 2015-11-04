package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

public class ManagerMain extends AppCompatActivity {
    View currentCategory;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_manager_main);
        currentCategory = findViewById(R.id.bItemsSold);
        switchReport(currentCategory);

    }

    public void switchReport(View view) {
        currentCategory.setEnabled(true);
        currentCategory = view;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
        view.setEnabled(false);
        switch (view.getId()) {
            case (R.id.bItemsSold)://displaying items by most sold
                query.addDescendingOrder("Frequency");
                break;
            case (R.id.bGratuityReport):
                //  query = ParseQuery.getQuery("Users");
                //  query.whereEqualTo("Job","Server");
                //  query.addDescendingOrder("Name");
                //find total gratuity earned by server here
                break;
            case (R.id.bRevenueReport):
                query.addDescendingOrder("Price");
                break;
            case (R.id.bTop3):
                query.whereNotEqualTo("Type", "Drink");
                query.addDescendingOrder("Frequency");
                query.setLimit(3);
                break;
            case (R.id.bManageIngridients):
                //sends us to the Ingridiants activity
                break;
            case (R.id.bTableAdmin):
                //sends us to the Tables view
                break;
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> menuItems, ParseException e) {
                ReportAdapter adapter = new ReportAdapter(menuItems);
                ListView lvMenu = (ListView) findViewById(R.id.lvMenu);
                lvMenu.setAdapter(adapter);
            }
        });
    }
        // nested class used for the menu adapter
        private class ReportAdapter extends ArrayAdapter<ParseObject> {

            // constructor
            public ReportAdapter(List<ParseObject> objects) { super(ManagerMain.this, 0, objects); }

            // function called whenever the list is created or scrolled
            @Override public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.activity_manager_report, parent, false);
                }

                // get the current item
                ParseObject item = getItem(position);


                // get item name
                TextView tvItemName = (TextView)view.findViewById(R.id.tvItemName);
                String itemName = item.getString("ItemName");
                tvItemName.setText(itemName);



                // get quantity
               TextView tvItemQuanity =(TextView)view.findViewById(R.id.tvItemQuantity);
                String itemQuanity =item.getString("Frequency");
                // return the view
                return view;
            }
        }
    }
