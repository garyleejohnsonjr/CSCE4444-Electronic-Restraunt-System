package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class CustomerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        //setting Counter
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> counter, ParseException e) {
                int count = counter.size();
                TextView tvServedCounter= (TextView) findViewById(R.id.tvServedCounter);
                String Formatted=String.valueOf(count)+ " Customers Served!";
                tvServedCounter.setText(Formatted);
            }
        });


        Button bPayBill = (Button) findViewById(R.id.bPayBill);
        Button bViewMenu = (Button) findViewById(R.id.bCustomerViewMenu);
        Button bGames = (Button) findViewById(R.id.bGames);
        Button bRewardsClub = (Button) findViewById(R.id.bRewardsClub);
        Button bCallServer = (Button) findViewById(R.id.bCallServer);
        Button bShareGeneral = (Button) findViewById(R.id.bShare);

        bShareGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iShareTwitter = new Intent(CustomerMain.this, ShareGeneral.class);
                startActivity(iShareTwitter);
            }
        });

        bPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPayBill = new Intent(CustomerMain.this, PayOrders.class);
                startActivity(iPayBill);
            }
        });

        bViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMenu = new Intent(CustomerMain.this, MenuMain.class);
                startActivity(iMenu);
            }
        });

        bGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGames = new Intent(CustomerMain.this, Games.class);
                startActivity(iGames);
            }
        });

        bRewardsClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRewards = new Intent(CustomerMain.this, RewardsLogin.class);
                startActivity(iRewards);
            }
        });

        bCallServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCallServer = new Intent(CustomerMain.this, CallServer.class);
                startActivity(iCallServer);
            }
        });
    }
}
