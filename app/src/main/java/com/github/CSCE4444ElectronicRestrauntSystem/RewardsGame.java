package com.github.CSCE4444ElectronicRestrauntSystem;

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

    //Action for when a cell is clicked. Determines which cell has been clicked and passed that
    // information on the the virtual game board.
    public void cardClick(View v) {
        //Get the id of the clicked object and assign it to a Textview variable
        //TextView cell = (TextView) findViewById(v.getId());
        //Check the content and make sure the cell is empty and that the game isn't over
        //String content = (String) cell.getText();

        Random thisNumber = new Random();
        int roll = thisNumber.nextInt(5) + 1;

        if (roll == 5)
        {
            ImageView cell = (ImageView) findViewById(v.getId());
            cell.setImageResource(R.drawable.ace);
            //TODO: DO SOME REAL SHIT HERE!
        }
        else
        {
            ImageView cell = (ImageView) findViewById(v.getId());
            cell.setImageResource(R.drawable.joker);
            //TODO: DO SOME MO' REAL SHIT HERE!
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
