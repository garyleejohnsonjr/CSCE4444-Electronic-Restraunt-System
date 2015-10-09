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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Todo: Making note of basic syntax to create Parse Object
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        Example: ParseObject name = new ParseObject("Name");
                 name.put("Field", "Content");
                 name.put("Field2", "Content");
                 name.put("Calories", 320);
                 name.saveInBackground();

        Can also save associated user:
                 ParseUser user = ParseUser.getCurrentUser();
                 name.put("user", user);
        */

        //Create variables for buttons and edit text fields
        Button bSubmitLoginInfo = (Button) findViewById(R.id.bSubmitLoginInfo);
        final EditText etUserName = (EditText) findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        bSubmitLoginInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Todo: Finish logic to confirm login info; current logic only temporary

                //Grab user entered username and password and store as strings
                String uName = etUserName.getText().toString();
                String pswd = etPassword.getText().toString();

                //Verify login credentials
                //Customer
                if(uName.equals("customer") && pswd.equals("customer"))
                {
                    Intent i = new Intent(MainActivity.this, CustomerMain.class);
                    //example: i.putExtra("user", ParseUser.getCurrentUser().getString("name"));
                    startActivity(i);

                    finish();
                }

                else if(uName.equals("manager") && pswd.equals("manager"))
                {
                    //Todo: Manager login
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
    }
}
