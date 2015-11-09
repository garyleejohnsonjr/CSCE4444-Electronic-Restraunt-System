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

import java.util.List;

public class ManagerMain extends AppCompatActivity {
    View currentCategory;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_manager_main);
        currentCategory = findViewById(R.id.bRevenueReport);//sets entry point and inital report displayed on screen. chosen arbiraraly
        RevenueReport(currentCategory);

    }
//shows revenue report on clicking button
    public void RevenueReport(View view) {
        currentCategory.setEnabled(true);
        currentCategory = view;
        currentCategory.setEnabled(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
        query.addDescendingOrder("ItemName");
        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reportItems, ParseException e) {
                RevReportAdaptor adapter = new RevReportAdaptor(reportItems);
                ListView lvReportSpace = (ListView) findViewById(R.id.lvReportSpace);
                lvReportSpace.setAdapter(adapter);
            }
        });
    }
    private class RevReportAdaptor extends ArrayAdapter<ParseObject>{
        public RevReportAdaptor(List<ParseObject> objects) { super(ManagerMain.this, 0, objects); }

        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_manager_reportrev, parent, false);
            }
            //seting item name in field
            ParseObject entry=getItem(position);
            TextView tvItemName=(TextView)view.findViewById(R.id.tvItemName);
            String ItemName=entry.getString("ItemName");
            tvItemName.setText(ItemName);
            //setting item quantity in field
            TextView tvItemQuantity=(TextView)view.findViewById(R.id.tvItemQuantity);
            int ItemQuantity=(int)entry.getNumber("Frequency");
            Double ItemPrice=entry.getNumber("Price").doubleValue();
            Double rev=ItemQuantity*ItemPrice;
            String sIQ=String.valueOf(rev);
            tvItemQuantity.setText(sIQ);

            return view;
        }
    }
    //on click, generates and displays the gratuity report
    public void GratuityReport(View view){
        currentCategory.setEnabled(true);
        currentCategory = view;
        currentCategory.setEnabled(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reportItems, ParseException e) {//completes the parse query and sets adaptor for display
                GratReportAdaptor adapter = new GratReportAdaptor(reportItems);
                ListView lvReportSpace = (ListView) findViewById(R.id.lvReportSpace);
                lvReportSpace.setAdapter(adapter);
            }
        });
    }
    private class GratReportAdaptor extends ArrayAdapter<ParseObject>{
        public GratReportAdaptor(List<ParseObject> objects) { super(ManagerMain.this, 0, objects); }
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_manager_reportgrat, parent, false);
            }
            //seting item name in field
            ParseObject entry=getItem(position);
            TextView tvOrder=(TextView)view.findViewById(R.id.tvOrder);
            Integer ItemName=entry.getInt("TableNumber");
            String ItemNameString = "Table: " + String.valueOf(ItemName);
            tvOrder.setText(ItemNameString);
            //setting item quantity in field
            TextView tvItemQuantity=(TextView)view.findViewById(R.id.tvGratuity);
            Double Gratuity=  entry.getNumber("Gratuity").doubleValue();
            String sG=String.valueOf(Gratuity);
            tvItemQuantity.setText(sG);

            return view;
        }

    }
    public void TaxReport(View view){//on click, generates and displays the tax report
        currentCategory.setEnabled(true);
        currentCategory = view;
        currentCategory.setEnabled(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reportItems, ParseException e) {
                TaxReportAdaptor adapter = new TaxReportAdaptor(reportItems);
                ListView lvReportSpace = (ListView) findViewById(R.id.lvReportSpace);
                lvReportSpace.setAdapter(adapter);
            }
        });
    }
    public class TaxReportAdaptor extends ArrayAdapter<ParseObject>{
        public TaxReportAdaptor(List<ParseObject> objects) { super(ManagerMain.this, 0, objects); }
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_manager_reportadjust, parent, false);
            }
            //seting item name in field
            ParseObject entry=getItem(position);
            TextView tvOrder=(TextView)view.findViewById(R.id.tvOrderNumber);
            String objectID=entry.getObjectId().toString();
            tvOrder.setText("Order: " + objectID);
            //setting item quantity in field
            TextView tvItemQuantity=(TextView)view.findViewById(R.id.tvAdjustNumber);
            Double Gratuity=  entry.getNumber("Tax").doubleValue();
            String sG=String.valueOf(Gratuity);
            tvItemQuantity.setText(sG);

            return view;
        }

    }
    public void AdjustmentReport(View view){//onclick generates and displays the adjustment report
        currentCategory.setEnabled(true);
        currentCategory = view;
        currentCategory.setEnabled(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reportItems, ParseException e) {
                AdjustReportAdaptor adapter = new AdjustReportAdaptor(reportItems);
                ListView lvReportSpace = (ListView) findViewById(R.id.lvReportSpace);
                lvReportSpace.setAdapter(adapter);
            }
        });
    }
    public class AdjustReportAdaptor extends ArrayAdapter<ParseObject>{
        public AdjustReportAdaptor(List<ParseObject> objects) { super(ManagerMain.this, 0, objects); }
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_manager_reportadjust, parent, false);
            }
            //seting item name in field
            ParseObject entry=getItem(position);
            TextView tvOrder=(TextView)view.findViewById(R.id.tvOrderNumber);
            String objectID=entry.getObjectId().toString();
            tvOrder.setText("Order: " + objectID);
            //setting item quantity in field
            TextView tvItemQuantity=(TextView)view.findViewById(R.id.tvAdjustNumber);
            Double Gratuity=  entry.getNumber("Adjustments").doubleValue();
            String sG=String.valueOf(Gratuity);
            tvItemQuantity.setText(sG);
            String comment=entry.getString("AdjustmentComments");
            TextView tvComments =(TextView)view.findViewById(R.id.tvComments);
            tvComments.setText(comment);

            return view;
        }

    }

    public void SalesReport(View view){//on click, generates and displays the Sales Reports
        currentCategory.setEnabled(true);
        currentCategory = view;
        currentCategory.setEnabled(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
        query.addDescendingOrder("ItemName");
        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reportItems, ParseException e) {
                SalesReportAdaptor adapter = new SalesReportAdaptor(reportItems);
                ListView lvReportSpace = (ListView) findViewById(R.id.lvReportSpace);
                lvReportSpace.setAdapter(adapter);
            }
        });
    }
    private class SalesReportAdaptor extends ArrayAdapter<ParseObject>{
        public SalesReportAdaptor(List<ParseObject> objects) { super(ManagerMain.this, 0, objects); }

        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_manager_reportrev, parent, false);
            }
            //seting item name in field
            ParseObject entry=getItem(position);
            TextView tvItemName=(TextView)view.findViewById(R.id.tvItemName);
            String ItemName=entry.getString("ItemName");
            tvItemName.setText(ItemName);
            //setting item quantity in field
            TextView tvItemQuantity=(TextView)view.findViewById(R.id.tvItemQuantity);
            int ItemQuantity=(int)entry.getNumber("Frequency");
            String sIQ=String.valueOf(ItemQuantity);
            tvItemQuantity.setText(sIQ);

            return view;
        }
    }
    public void TableAdmin(View view){//changes to the server view, so the manager can view tables and orders
        Intent i = new Intent(ManagerMain.this, ServerMain.class);
        //example: i.putExtra("user", ParseUser.getCurrentUser().getString("name"));
        startActivity(i);
    }
    public void ManageIngridents(View view) {//changes to the kitchen view, so the manager can view ingrediants and pending orders
        Intent i = new Intent(ManagerMain.this, KitchenMain.class);
        //example: i.putExtra("user", ParseUser.getCurrentUser().getString("name"));
        startActivity(i);
    }
}

