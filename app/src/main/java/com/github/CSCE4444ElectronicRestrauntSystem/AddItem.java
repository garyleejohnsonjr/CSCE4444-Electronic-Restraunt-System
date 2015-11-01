package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class AddItem extends AppCompatActivity {
    int calories;

    // activity creation event
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

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
        String request = etRequest.getText().toString();
        getIntent().putExtra("Request", request);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }
}
