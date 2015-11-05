package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
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

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class RewardsGameWin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_game_win);

        Button bSubmitRewardsWin = (Button) findViewById(R.id.bSubmitRewardsWinner);
        final EditText etUserName = (EditText) findViewById(R.id.etRewardsWinUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etRewardsWinPassword);

        bSubmitRewardsWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("RewardsMbr");
                query.whereEqualTo("UserID", etUserName.getText().toString());
                query.whereEqualTo("Password", etPassword.getText().toString());

                query.findInBackground(new FindCallback<ParseObject>(){
                    public void done(List<ParseObject> rewardsUser, ParseException e) {
                        if (e == null) {
                            if(rewardsUser.isEmpty())
                            {
                                Toast.makeText(getApplicationContext(),
                                        "Error: Your username or password was incorrect.", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                ParseObject user = rewardsUser.get(0);
                                Toast.makeText(getApplicationContext(),
                                        user.get("UserID").toString() + " validated.", Toast.LENGTH_LONG).show();

                                Time time = new Time();
                                time.setToNow();
                                Log.d("TIME TEST", Long.toString(time.toMillis(false)));
                                //Toast.makeText(getApplicationContext(),
                                //        Long.toString(time.toMillis(false)), Toast.LENGTH_LONG).show();
                                ParseObject newCoupon = new ParseObject("Coupon");
                                newCoupon.put("Code", time.toMillis(false));
                                newCoupon.saveInBackground();
                                try {
                                    newCoupon.refresh();
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                                Date date = newCoupon.getCreatedAt();
                                int month = date.getMonth();
                                date.setMonth(month + 3);
                                newCoupon.put("Expiration", date);
                                newCoupon.saveInBackground();
                                String objectID = newCoupon.getObjectId();

                                Intent i = new Intent(RewardsGameWin.this, Coupon.class);
                                i.putExtra("objectID", objectID);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                });

                MainApplication application = (MainApplication) getApplication();
                if(application.gamePlays == 2)
                {
                    Toast.makeText(getApplicationContext(),
                            "You can't play this game anymore during this visit.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent iRewardsGame = new Intent(RewardsGameWin.this, RewardsGame.class);
                    startActivity(iRewardsGame);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards_game_win, menu);
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
