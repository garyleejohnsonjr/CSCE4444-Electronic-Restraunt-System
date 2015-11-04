package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class CustomerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        Button bPayBill = (Button) findViewById(R.id.bPayBill);
        Button bViewMenu = (Button) findViewById(R.id.bCustomerViewMenu);
        Button bGames = (Button) findViewById(R.id.bGames);
        Button bRewardsClub = (Button) findViewById(R.id.bRewardsClub);
        Button bCallServer = (Button) findViewById(R.id.bCallServer);
        Button bShareTwitter = (Button) findViewById(R.id.bShare);

        bShareTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iShareTwitter = new Intent(CustomerMain.this, ShareTwitter.class);
                startActivity(iShareTwitter);
            }
        });

        bPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPayBill = new Intent(CustomerMain.this, PayBill.class);
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
