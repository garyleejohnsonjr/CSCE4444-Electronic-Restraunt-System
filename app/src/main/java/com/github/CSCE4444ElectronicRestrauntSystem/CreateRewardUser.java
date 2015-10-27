package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.parse.ParseObject;

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

                //Creates New User
                //Todo: General Error handling
                ParseObject user = new ParseObject("RewardsMbr");
                user.put("UserID", uName);
                user.put("Password", pswd);
                //Todo:Format Birthday Correctly
                //user.put("Birthday", birthday);
                user.put("Points", 0);
                user.saveInBackground();

                //Todo: Sends user to main page for testing. Needs to go to individual homepage
                Intent i = new Intent(CreateRewardUser.this, RewardsStatus.class);
                startActivity(i);

                finish();
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
}
