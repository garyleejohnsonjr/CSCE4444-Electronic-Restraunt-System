package com.github.CSCE4444ElectronicRestrauntSystem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class AddItem extends AppCompatActivity {
    //TODO: add item button
    //TODO: calories button

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // item name
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
                String formattedPrice = String.format("$%.2f", price);
                if (formattedPrice.equals("$0.00")) formattedPrice = "Free";
                tvPrice.setText(formattedPrice);

                // get description
                TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
                String description = item.getString("Description");
                tvDescription.setText(description);
            }
        });
    }

}
