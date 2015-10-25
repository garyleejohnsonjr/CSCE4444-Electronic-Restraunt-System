package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.parse.ParseObject;

//User Creation Page
public class CreateUser extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
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

        Button bCreateUserInfo= (Button) findViewById(R.id.bCreateUserInfo);
        final EditText etUserName = (EditText) findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        bCreateUserInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Todo: Finish logic to confirm login info; current logic only temporary

                //Grab user entered username and password and store as strings
                String uName = etUserName.getText().toString();
                String pswd = etPassword.getText().toString();
                
                //Gets Checkbox Info
                String userType = "";
                final CheckBox customerBox = (CheckBox) findViewById(R.id.customerBox);
                final CheckBox kitchenBox = (CheckBox) findViewById(R.id.kitchenBox);
                final CheckBox serverBox = (CheckBox) findViewById(R.id.serverBox);

                //Gets type of user
                if (customerBox.isChecked())
                    userType = "Customer";
                else if (kitchenBox.isChecked())
                    userType = "Kitchen";
                else if (serverBox.isChecked())
                    userType = "Server";
                //Todo: Need to add an else condition if multiple/no boxes are checked

                //Creates New User
                //Todo: General Error handling
                ParseObject user = new ParseObject("Users");
                user.put("Name", uName);
                user.put("Password", pswd);
                user.put("Job", userType);
                user.saveInBackground();

                //Todo: Sends user to main page for testing. Needs to go to individual homepage
                Intent i = new Intent(CreateUser.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}