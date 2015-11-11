package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Games extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        //Grabs all the buttons from the XML
        Button bTicTacToe = (Button) findViewById(R.id.bTicTacToe);
        Button bSnake = (Button) findViewById(R.id.bSnake);
        Button bRewards = (Button) findViewById(R.id.bRewardsGame);
        Button bPong = (Button) findViewById(R.id.bPong);
        Button bBreakout = (Button) findViewById(R.id.bBreakout);

        //When the Tic Tac Toe Button is clicked, this tells it to open the game
        bTicTacToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iTicTacToe = new Intent(Games.this, TicTacToe.class);
                startActivity(iTicTacToe);
            }
        });

        bBreakout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBreakout = new Intent(Games.this, BreakoutGame.class);
                startActivity(iBreakout);
            }
        });

        //Opens snake game when button is clicked
        bSnake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSnake = new Intent(Games.this, Snake.class);
                startActivity(iSnake);
            }
        });

        //Opens pong game when button is clicked
        bPong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPong = new Intent(Games.this, Pong.class);
                startActivity(iPong);
            }
        });

        //Opens rewards club game
        bRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The next line is used to access global variables
                MainApplication application = (MainApplication) getApplication();
                //Since the game can only be played twice per visit, this checks and prevents a user from playing more than they should be allowed to
                if(application.gamePlays == 2)
                {
                    Toast.makeText(getApplicationContext(),
                            "You can't play this game anymore during this visit.", Toast.LENGTH_LONG).show();
                } //otherwise, launch the game
                else
                {
                    Intent iRewardsGame = new Intent(Games.this, RewardsGame.class);
                    startActivity(iRewardsGame);
                }
            }
        });
    }

    // call server event
    public void callServer(View view) {
        Intent intent = new Intent(this, CallServer.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_games, menu);
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
