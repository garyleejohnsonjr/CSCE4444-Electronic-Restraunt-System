package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


//Main Sever Page
//Todo: This is basically just a copy of the temporary CustomerMain
public class ServerMain extends AppCompatActivity {

    //Table Status Fragment
    public void tableSelect(int tableNum){


    }
    void changeColor(final TextView tTable, int tableNum){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tables");
        query.whereEqualTo("Number", tableNum);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> tables,
                             ParseException e) {
                //Todo: Submit nothing error
                if (e == null) {
                    for (ParseObject table : tables) {
                        //Puts Status into database
                        if(table.getString("Status").equals("Table Ordered")) {
                            //Blue-Ordered
                            tTable.clearAnimation();
                            tTable.setTextColor(0xFF0008FF);
                        }
                        if(table.getString("Status").equals("Table Paid")) {
                            //Black-Table Paid and
                            tTable.clearAnimation();
                            tTable.setTextColor(0xFF010101);
                        }
                        //Todo: May need to change color. Just for testing purposes
                        if(table.getString("Status").equals("Table Help")){
                            //Blinks Pink if needs help
                            tTable.setTextColor(0xFFFF00E9);
                            Animation anim = new AlphaAnimation(0.0f, 1.0f);
                            anim.setDuration(50); //Blinking speed
                            anim.setStartOffset(20);
                            anim.setRepeatMode(Animation.REVERSE);
                            anim.setRepeatCount(Animation.INFINITE);
                            tTable.startAnimation(anim);
                        }

                        if(table.getString("Status").equals("Table Unoccupied")) {
                            //White - Unoccupied
                            tTable.clearAnimation();
                            tTable.setTextColor(0xFFFFFFFF);
                        }
                        if(table.getString("Status").equals("Table Eating")) {
                            //Red-Table Eating
                            tTable.clearAnimation();
                            tTable.setTextColor(0xFFFF0B00);
                        }
                        if(table.getString("Status").equals("Table Occupied")) {
                            //Green-Table Occupied
                            tTable.clearAnimation();
                            tTable.setTextColor(0xFF00FF00);
                        }
                    }
                }

                else {
                    //Failed Query Log
                    Log.d("Tables", "Error: " + e.getMessage());
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_main);

        //Buttons and clickable text tables
        Button bPayBill = (Button) findViewById(R.id.bPayBill);
        final TextView tTable1 = (TextView) findViewById(R.id.tTable1);
        final TextView tTable2 = (TextView) findViewById(R.id.tTable2);
        final TextView tTable3 = (TextView) findViewById(R.id.tTable3);
        final TextView tTable4 = (TextView) findViewById(R.id.tTable4);
        final TextView tTable5 = (TextView) findViewById(R.id.tTable5);
        final TextView tTable6 = (TextView) findViewById(R.id.tTable6);
        final TextView tTable7 = (TextView) findViewById(R.id.tTable7);
        final TextView tTable8 = (TextView) findViewById(R.id.tTable8);
        final TextView tTable9 = (TextView) findViewById(R.id.tTable9);
        final TextView tTable10 = (TextView) findViewById(R.id.tTable10);

        //Sets Default Table colors
        changeColor(tTable1, 1);
        changeColor(tTable2, 2);
        changeColor(tTable3, 3);
        changeColor(tTable4, 4);
        changeColor(tTable5, 5);
        changeColor(tTable6, 6);
        changeColor(tTable7, 7);
        changeColor(tTable8, 8);
        changeColor(tTable9, 9);
        changeColor(tTable10, 10);

        Thread refreshFeed = new Thread(){
            public void run(){
                while(true){
                    try{
                        sleep(10000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    finally {
                        //Checks for updated Table status every 10 secs
                        changeColor(tTable1, 1);
                        changeColor(tTable2, 2);
                        changeColor(tTable3, 3);
                        changeColor(tTable4, 4);
                        changeColor(tTable5, 5);
                        changeColor(tTable6, 6);
                        changeColor(tTable7, 7);
                        changeColor(tTable8, 8);
                        changeColor(tTable9, 9);
                        changeColor(tTable10, 10);
                    }
                }
            }
        };
        refreshFeed.start();


        //Each ables onClick function
        tTable1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 1);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 2);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 3);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 4);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 5);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 6);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 7);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 8);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 9);
                startActivity(iTableStatus);
                finish();
            }
        });
        tTable10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerMain.this, TableStatus.class);
                iTableStatus.putExtra("Number", 10);
                startActivity(iTableStatus);
                finish();
            }
        });


        bPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPayBill = new Intent(ServerMain.this, PayBill.class);
                startActivity(iPayBill);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_main, menu);
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
