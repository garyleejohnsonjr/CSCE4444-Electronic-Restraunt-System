package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateRewardUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reward_user);

        Button bCreateRwdInfo= (Button) findViewById(R.id.bCreateRwdsInfo);
        final EditText etRwdName = (EditText) findViewById(R.id.etRewardsName);
        final EditText etPassword = (EditText) findViewById(R.id.etRwdsPassword);
        final EditText etBirthday =(EditText) findViewById(R.id.etBirthday);
        bCreateRwdInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Todo: Finish logic to confirm login info; current logic only temporary

                //Grab user entered username and password and store as strings
                String uName = etRwdName.getText().toString();
                String pswd = etPassword.getText().toString();
                String birthday = etBirthday.getText().toString();

                if(formatDate(birthday) == null)
                    Toast.makeText(getApplicationContext(), "Wrong date Format", Toast.LENGTH_LONG).show();
                else if(uName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter A Username", Toast.LENGTH_LONG).show();
                }
                else if (pswd.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter a Password.", Toast.LENGTH_LONG).show();
                }
                else if(birthday.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter a Birthday", Toast.LENGTH_LONG).show();
                }
                else {
                    //Creates New User
                    ParseObject user = new ParseObject("RewardsMbr");
                    user.put("UserID", uName);
                    user.put("Password", pswd);
                    user.put("Birthday", formatDate(birthday));
                    user.put("Points", 0);
                    user.saveInBackground();

                    Intent i = new Intent(CreateRewardUser.this, RewardsStatus.class);
                    i.putExtra("UserId", uName);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_reward_user, menu);
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

    public static Date formatDate(String birthday) {

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        try {
            Date date = format.parse(birthday);
            return date;
        } catch (ParseException e) {

        }
        return null;
    }

 }
