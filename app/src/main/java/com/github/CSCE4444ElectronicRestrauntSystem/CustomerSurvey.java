package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.ParseObject;

public class CustomerSurvey extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_survey);

        //Get the button so that we can define what happens when it is clicked
        Button bSubmit = (Button) findViewById(R.id.bSubmitSurvey);

        //When the button is clicked, we do all this
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create a variable for each RadioButton so that we can check their status (clicked or not)
                RadioButton server1 = (RadioButton) findViewById(R.id.rbServerQuality1);
                RadioButton server2 = (RadioButton) findViewById(R.id.rbServerQuality2);
                RadioButton server3 = (RadioButton) findViewById(R.id.rbServerQuality3);
                RadioButton server4 = (RadioButton) findViewById(R.id.rbServerQuality4);
                RadioButton server5 = (RadioButton) findViewById(R.id.rbServerQuality5);

                RadioButton food1 = (RadioButton) findViewById(R.id.rbFoodQuality1);
                RadioButton food2 = (RadioButton) findViewById(R.id.rbFoodQuality2);
                RadioButton food3 = (RadioButton) findViewById(R.id.rbFoodQuality3);
                RadioButton food4 = (RadioButton) findViewById(R.id.rbFoodQuality4);
                RadioButton food5 = (RadioButton) findViewById(R.id.rbFoodQuality5);

                RadioButton wait1 = (RadioButton) findViewById(R.id.rbWaitTime1);
                RadioButton wait2 = (RadioButton) findViewById(R.id.rbWaitTime2);
                RadioButton wait3 = (RadioButton) findViewById(R.id.rbWaitTime3);
                RadioButton wait4 = (RadioButton) findViewById(R.id.rbWaitTime4);
                RadioButton wait5 = (RadioButton) findViewById(R.id.rbWaitTime5);

                //Get the EditText to see if they entered comments
                EditText etComments = (EditText) findViewById(R.id.etAdditionalComments);

                //These numbers represent how the user rates different aspects of their visit
                int checkServer = 0;
                int checkFood = 0;
                int checkWait = 0;

                //Conditionals to get appropriate value from radio buttons
                //Int corresponds to rating, if button not selected, int stays at zero and will be checked later
                if(server1.isChecked())
                {
                    checkServer = 1;
                }
                else if(server2.isChecked())
                {
                    checkServer = 2;
                }
                else if(server3.isChecked())
                {
                    checkServer = 3;
                }
                else if(server4.isChecked())
                {
                    checkServer = 4;
                }
                else if(server5.isChecked())
                {
                    checkServer = 5;
                }

                if(food1.isChecked())
                {
                    checkFood = 1;
                }
                else if(food2.isChecked())
                {
                    checkFood = 2;
                }
                else if(food3.isChecked())
                {
                    checkFood = 3;
                }
                else if(food4.isChecked())
                {
                    checkFood = 4;
                }
                else if(food5.isChecked())
                {
                    checkFood = 5;
                }

                if(wait1.isChecked())
                {
                    checkWait = 1;
                }
                else if(wait2.isChecked())
                {
                    checkWait = 2;
                }
                else if(wait3.isChecked())
                {
                    checkWait = 3;
                }
                else if(wait4.isChecked())
                {
                    checkWait = 4;
                }
                else if(wait5.isChecked())
                {
                    checkWait = 5;
                }

                //If the user didn't check a button in one of the rows, complain
                //Notify the user that they need to select a value for each question in a toast
                if (checkWait == 0 || checkFood == 0 || checkServer == 0)
                {
                    Toast.makeText(getApplicationContext(),
                            "Error: select a value for each of the questions.", Toast.LENGTH_LONG).show();
                }
                else //Else, make a survey ParseObject and throw what we need into it and submit it
                {
                    //Put the ratings for wait time, food quality, and server
                    ParseObject newSurvey = new ParseObject("Surveys");
                    newSurvey.put("WaitTimeRating", checkWait);
                    newSurvey.put("FoodQualityRating", checkFood);
                    newSurvey.put("ServerRating", checkServer);

                    //Get current text from the edit text field
                    String additionalCommentsStr = etComments.getText().toString();
                    //If it's empty, throw "None" into the "AdditionalComments" field of the ParseObject
                    //Stylistic choice; could be easier to parse through by looking for a string matching "None"
                    //Prevents errors associated with a NULL value/field
                    if(additionalCommentsStr.matches(""))
                    {
                        newSurvey.put("AdditionalComments", "None");
                    }
                    else //Else, throw the additional comments string into the field
                    {
                        newSurvey.put("AdditionalComments", additionalCommentsStr);
                    }

                    //Save to Parse
                    newSurvey.saveInBackground();

                    //Let the user know that it was submitted and thank them
                    Toast.makeText(getApplicationContext(),
                            "Survey submitted. Thank you for your time.", Toast.LENGTH_LONG).show();
                    //Kill the activity
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customer_survey, menu);
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
