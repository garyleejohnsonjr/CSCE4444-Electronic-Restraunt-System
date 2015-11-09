package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class RewardsLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_login);

        Button bSubmitRwdInfo = (Button) findViewById(R.id.bCreateRwdInfo);
        Button bCreateRwdUser = (Button) findViewById(R.id.bCreateRwdUser);
        final EditText etRwdName = (EditText) findViewById(R.id.etRwdName);
        final EditText etRwdPassword = (EditText) findViewById(R.id.etRwdPassword);

        //Hides Join Button if they are looking for coupons
        if(getIntent().hasExtra("Paying")){
            bCreateRwdUser.setVisibility(View.INVISIBLE);
        }

        bSubmitRwdInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Grab user entered username and password and store as strings
                final String uName = etRwdName.getText().toString();
                final String pswd = etRwdPassword.getText().toString();
                final Intent intent= getIntent();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("RewardsMbr");

                query.whereEqualTo("UserID", uName);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> users,
                                     ParseException e) {
                        //Username Not Found
                        if (users.size() == 0) {
                            Toast.makeText(getApplicationContext(), "Username invalid.", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        } else if (e == null) {
                            for (ParseObject username : users) {
                                //Log to check queries
                                Log.d("Password", username.getString("Password"));

                                //Correct Login
                                if (username.getString("Password").equals(pswd)) {

                                    //Paying Customer Login
                                    if(intent.hasExtra("Paying")){
                                        Intent i = new Intent(RewardsLogin.this, RewardCoupons.class);
                                        i.putExtra("UserId", uName);
                                        i.putExtra("Paying", true);
                                        i.putExtra("objectId", intent.getStringExtra("objectId"));
                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        Intent i = new Intent(RewardsLogin.this, RewardsStatus.class);
                                        //example: i.putExtra("user", ParseUser.getCurrentUser().getString("name"));
                                        i.putExtra("UserId", uName);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                                //Incorrect Password Resets Page
                                else {
                                    //toast/alert message I prefer alerts but this works for now
                                    Toast.makeText(getApplicationContext(), "Password invalid.", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                            }

                        } else {
                            //Failed Query Log
                            Log.d("Name", "Error: " + e.getMessage());
                        }
                    }
            });
                //Verify login credentials
                //Customer


            }
        });

        //Goes to Create Reward User Page.
        bCreateRwdUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(RewardsLogin.this, CreateRewardUser.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards_login, menu);
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
