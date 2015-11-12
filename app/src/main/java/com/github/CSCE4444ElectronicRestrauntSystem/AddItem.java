package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class AddItem extends AppCompatActivity {
    int calories;

    // activity creation event
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Sets Call Server Button to invisible if its a server
        Button bCallServer = (Button)findViewById(R.id.bCallServer);
        Intent i = getIntent();
        if(i.getBooleanExtra("Server", false))
            bCallServer.setVisibility(View.INVISIBLE);

        // get item name
        String ItemName = getIntent().getStringExtra("ItemName");
        setTitle("Add Item - " + ItemName);
        ((TextView)findViewById(R.id.tvItemName)).setText(ItemName);

        // build query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
        query.whereEqualTo("ItemName", ItemName);

        // run query in background
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override public void done(ParseObject item, ParseException e) {

                // get pictures from parse in background
                item.getParseFile("Picture").getDataInBackground(new GetDataCallback() {
                    @Override public void done(byte[] data, ParseException e) {
                        ImageView ivPicture = (ImageView)findViewById(R.id.ivPicture);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        ivPicture.setImageBitmap(bitmap);
                    }
                });

                // get category
                LinearLayout llKidsOptions = (LinearLayout)findViewById(R.id.llKidsOptions);
                String type = item.getString("Type");
                if (type.equals("KidsMenu")) {
                    llKidsOptions.setVisibility(View.VISIBLE);

                    // build drink query
                    ParseQuery<ParseObject> drinksQuery = ParseQuery.getQuery("MenuItem");
                    drinksQuery.whereEqualTo("Type", "Drink");
                    drinksQuery.whereEqualTo("Kids", true);
                    drinksQuery.whereEqualTo("Avalibility", true);

                    // run drink query
                    drinksQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            RadioGroup rgDrinks = (RadioGroup) findViewById(R.id.rgDrinks);
                            for (ParseObject item : list) {
                                RadioButton rb = new RadioButton(AddItem.this);
                                String itemName = item.getString("ItemName");
                                rb.setText(itemName);
                                rgDrinks.addView(rb);
                            }
                            rgDrinks.check(rgDrinks.getChildAt(0).getId());
                        }
                    });

                    // build sides query
                    ParseQuery<ParseObject> sidesQuery = ParseQuery.getQuery("MenuItem");
                    sidesQuery.whereEqualTo("Type", "Appatizer");
                    sidesQuery.whereEqualTo("Kids", true);
                    sidesQuery.whereEqualTo("Avalibility", true);

                    // run sides query
                    sidesQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            RadioGroup rgSides = (RadioGroup)findViewById(R.id.rgSides);
                            for (ParseObject item : list) {
                                RadioButton rb = new RadioButton(AddItem.this);
                                String itemName = item.getString("ItemName");
                                rb.setText(itemName);
                                rgSides.addView(rb);
                            }
                            rgSides.check(rgSides.getChildAt(0).getId());
                        }
                    });
                }

                // get price
                TextView tvPrice = (TextView)findViewById(R.id.tvPrice);
                float price = item.getNumber("Price").floatValue();
                getIntent().putExtra("Price", price);
                String formattedPrice = String.format("$%.2f", price);
                if (formattedPrice.equals("$0.00")) formattedPrice = "Free";
                tvPrice.setText(formattedPrice);

                // get description
                TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
                String description = item.getString("Description");
                tvDescription.setText(description);

                // get calories
                Button bCalories = (Button)findViewById(R.id.bCalories);
                calories = item.getNumber("Calories").intValue();
                bCalories.setEnabled(true);

                // enable add item button
                Button bAddItem = (Button)findViewById(R.id.bAddItem);
                bAddItem.setEnabled(true);
            }
        });
    }

    // call server event
    public void callServer(View view) {
        Intent intent = new Intent(this, CallServer.class);
        startActivity(intent);
    }

    // check calories button event
    public void checkCalories(View view) {
        Toast.makeText(getApplicationContext(), "Calories: " + calories, Toast.LENGTH_SHORT).show();
    }

    // add item button event
    public void addItem(View view) {
        EditText etRequest = (EditText)findViewById(R.id.etRequest);
        String request = "";

        // add kids item options to request string
        LinearLayout llKidsOptions = (LinearLayout)findViewById(R.id.llKidsOptions);
        if (llKidsOptions.getVisibility() == View.VISIBLE) {
            // drink
            RadioGroup rgDrinks = (RadioGroup) findViewById(R.id.rgDrinks);
            int rbDrinkID = rgDrinks.getCheckedRadioButtonId();
            RadioButton rbDrink = (RadioButton)findViewById(rbDrinkID);
            String drinkName = rbDrink.getText().toString();
            request += "Drink: " + drinkName + ", ";

            // side
            RadioGroup rgSides = (RadioGroup) findViewById(R.id.rgSides);
            int rbSideID = rgSides.getCheckedRadioButtonId();
            RadioButton rbSide = (RadioButton)findViewById(rbSideID);
            String sideName = rbSide.getText().toString();
            request += "Side: " + sideName + ", ";
        }

        request += etRequest.getText().toString();
        getIntent().putExtra("Request", request);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }
}
