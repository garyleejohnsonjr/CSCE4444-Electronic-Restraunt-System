package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class RewardsGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_game);
    }


    public void cardClick(View v) {

        Random thisNumber = new Random();
        int roll = thisNumber.nextInt(5) + 1;

        if (roll == 5)
        {
            ImageView cell = (ImageView) findViewById(v.getId());
            cell.setImageResource(R.drawable.ace);

            MainApplication application = (MainApplication) getApplication();
            application.gamePlays = 2;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent iRewardsGame = new Intent(RewardsGame.this, RewardsGameWin.class);
                    startActivity(iRewardsGame);
                    finish();
                }
            }, 500);

            //try {
            //    wait(2000);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}

            //Intent iRewardsGame = new Intent(RewardsGame.this, RewardsGameWin.class);
            //startActivity(iRewardsGame);

        }
        else
        {
            ImageView cell = (ImageView) findViewById(v.getId());
            cell.setImageResource(R.drawable.joker);

            MainApplication application = (MainApplication) getApplication();
            application.gamePlays += 1;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent iRewardsGame = new Intent(RewardsGame.this, RewardsGameLose.class);
                    startActivity(iRewardsGame);
                    finish();
                }
            }, 500);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards_game, menu);
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
