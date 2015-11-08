package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class PayCredit extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_credit);

        // order number
        int orderNumber = getIntent().getExtras().getInt("OrderNumber");
        setTitle("Pay Credit - Order #" + (orderNumber + 1));

        // order amount
        TextView tvTotal = (TextView)findViewById(R.id.tvTotal);
        float total = getIntent().getExtras().getFloat("Total");
        String formattedTotal = String.format("$%.2f", total);
        tvTotal.setText(formattedTotal);


    }

    // call server event
    public void callServer(View view) {
        Intent intent = new Intent(this, CallServer.class);
        startActivity(intent);
    }

}
