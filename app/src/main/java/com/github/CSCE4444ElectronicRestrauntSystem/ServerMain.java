package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

//Main Sever Page
//Todo: This is basically just a copy of the temporary CustomerMain
public class ServerMain extends AppCompatActivity {

    //Table Status Fragment
    public void tableSelect(int tableNum){


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_main);

        //Buttons and clickable text tables
        Button bPayBill = (Button) findViewById(R.id.bPayBill);
        TextView tTable1 = (TextView) findViewById(R.id.tTable1);
        TextView tTable2 = (TextView) findViewById(R.id.tTable2);
        TextView tTable3 = (TextView) findViewById(R.id.tTable3);
        TextView tTable4 = (TextView) findViewById(R.id.tTable4);
        TextView tTable5 = (TextView) findViewById(R.id.tTable5);
        TextView tTable6 = (TextView) findViewById(R.id.tTable6);
        TextView tTable7 = (TextView) findViewById(R.id.tTable7);
        TextView tTable8 = (TextView) findViewById(R.id.tTable8);
        TextView tTable9 = (TextView) findViewById(R.id.tTable9);
        TextView tTable10 = (TextView) findViewById(R.id.tTable10);

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
