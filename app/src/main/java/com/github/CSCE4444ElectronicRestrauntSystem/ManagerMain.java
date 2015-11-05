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
        currentCategory = findViewById(R.id.bRevenueReport);
        RevenueReport(currentCategory);

    }
//shows revenue report on clicking button
    public void RevenueReport(View view) {
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
            ParseObject entry=getItem(position);
            TextView tvItemName=(TextView)findViewById(R.id.tvItemName);
            String ItemName=entry.getString("ItemName");
            tvItemName.setText(ItemName);
            TextView tvItemQuantity=(TextView)findViewById(R.id.tvItemQuantity);
            String ItemQuantity=entry.getString("Frequency");
            tvItemQuantity.setText(ItemQuantity);

            return view;
        }
    }
    public void GratuityReport(View view){
        currentCategory = view;
        currentCategory.setEnabled(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Orders");
        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reportItems, ParseException e) {
                GratReportAdaptor adapter = new GratReportAdaptor(reportItems);
                ListView lvReportSpace = (ListView) findViewById(R.id.lvReportSpace);
                lvReportSpace.setAdapter(adapter);
            }
        });
    }
    public class GratReportAdaptor extends ArrayAdapter<ParseObject>{
        public GratReportAdaptor(List<ParseObject> objects) { super(ManagerMain.this, 0, objects); }
    }
    public void TaxReport(View view){

    }
    public void AdjustmentReport(View view){

    }
    public void SalesReport(View view){

    }
}

