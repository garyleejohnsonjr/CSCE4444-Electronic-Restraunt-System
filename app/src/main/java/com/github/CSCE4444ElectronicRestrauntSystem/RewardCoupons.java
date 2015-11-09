package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class RewardCoupons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_coupons);


        //Queries For Valid Coupons
        ParseQuery<ParseObject> CouponQuery = ParseQuery.getQuery("Coupon");
        CouponQuery.whereEqualTo("User", getIntent().getStringExtra("UserId"));
        CouponQuery.whereEqualTo("Status", "Unused");
        CouponQuery.orderByAscending("Expiration");
        CouponQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> coupons,
                             ParseException e) {
                CouponAdapter adapter = new CouponAdapter(coupons);
                ListView lvCoupons = (ListView) findViewById(R.id.lvCoupons);
                lvCoupons.setAdapter(adapter);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards_status, menu);
        return true;
    }
    public void SubmitCoupon(View view) {

        // get adapter
        ListView lvCoupons = (ListView) findViewById(R.id.lvCoupons);

        // get item to remove
        final int position = lvCoupons.getPositionForView(view);
        final Intent i = getIntent();

        ParseQuery<ParseObject> CouponQuery = ParseQuery.getQuery("Coupon");
        CouponQuery.whereEqualTo("User", getIntent().getStringExtra("UserId"));
        CouponQuery.whereEqualTo("Status", "Unused");
        CouponQuery.orderByAscending("Expiration");
        CouponQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> coupons,
                             ParseException e) {
                int x = 0;
                for (ParseObject coupon : coupons) {
                    if (x == position) {
                        coupon.put("Status", "Used");
                        coupon.saveInBackground();
                    }
                    x++;
                }
            }
        });


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("objectId", i.getStringExtra("objectId"));
        query.getFirstInBackground(new GetCallback<ParseObject>(){
            public void done(ParseObject order,
                             ParseException e) {

                    Double newPrice = order.getDouble("Adjustments") + 10;
                    order.put("Coupon", true);
                    order.put("Adjustments",newPrice);
                    order.saveInBackground();
            }
        });
        finish();
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

private class CouponAdapter extends ArrayAdapter<ParseObject> {
    // constructor
    public CouponAdapter(List<ParseObject> items) {
        super(RewardCoupons.this, 0, items);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.activity_usable_coupons, parent, false);
        }

        final TextView tvCoupons = (TextView) view.findViewById(R.id.tvCoupons);
        // get the item
        ParseObject item = getItem(position);

        tvCoupons.setText("Coupon Code: " + item.get("Code") + "\nExpiration Date: " + item.getDate("Expiration"));

        return view;

    }
}
}
