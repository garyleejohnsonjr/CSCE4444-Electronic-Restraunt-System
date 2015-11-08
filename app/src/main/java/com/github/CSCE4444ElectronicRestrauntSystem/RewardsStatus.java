package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class RewardsStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_status);

        Intent i = getIntent();
        final TextView tvPointsTotal = (TextView) findViewById(R.id.tvPointsTotal);

        //Shows Number of point for the member
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RewardsMbr");
        query.whereEqualTo("UserID", i.getStringExtra("UserId"));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> users,
                             ParseException e) {
                for (ParseObject user : users)
                    tvPointsTotal.setText("Total Points: " + user.get("Points"));
            }
        });

        //Querys For Valid Coupons
        ParseQuery<ParseObject> CouponQuery = ParseQuery.getQuery("Coupon");
        CouponQuery.whereEqualTo("User", i.getStringExtra("UserId"));
        CouponQuery.whereEqualTo("Status", "Unused");
        CouponQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> coupons,
                             ParseException e) {
                    CouponAdapter adapter = new CouponAdapter(coupons);
                    ListView lvCoupons = (ListView)findViewById(R.id.lvCoupons);
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
            super(RewardsStatus.this, 0, items);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_coupon_list, parent, false);
            }

            final TextView tvCoupons = (TextView) view.findViewById(R.id.tvCoupons);
            // get the item
            ParseObject item = getItem(position);

            tvCoupons.setText("Coupon Code: " + item.get("Code") + "\nExpiration Date: " + item.getDate("Expiration"));

            return view;

        }
    }
}
