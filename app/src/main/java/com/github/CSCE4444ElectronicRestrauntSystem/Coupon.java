package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Coupon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);


        //Grabs the object ID, which is the coupon code, from the intent
        Intent intent = getIntent();
        String objectID = intent.getExtras().getString("objectID");

        //Sets the object ID into the text field
        TextView tvCouponCode = (TextView) findViewById(R.id.tvCouponCode2);
        tvCouponCode.setText(objectID);

        //Finds the exit button
        Button bExit = (Button) findViewById(R.id.bExitCouponScreen);
        //Kills the activity
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent iGames = new Intent(Coupon.this, Games.class);
                //startActivity(iGames);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coupon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
