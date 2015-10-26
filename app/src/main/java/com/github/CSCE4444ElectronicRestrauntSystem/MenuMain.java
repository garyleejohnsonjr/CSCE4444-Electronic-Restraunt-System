package com.github.CSCE4444ElectronicRestrauntSystem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

// the activity used for the main food menu
public class MenuMain extends AppCompatActivity {
    // Todo: Go to add item activity when item in menu is tapped
    // Todo: Go to edit/submit order activity when button is clicked


    // activity creation event
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        switchCategory(this.findViewById(R.id.bAppetizers));
    }


    // switch category event
    public void switchCategory(View view) {
        // create parse query of menu items
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");

        // enable all the buttons
        this.findViewById(R.id.bAppetizers).setEnabled(true);
        this.findViewById(R.id.bFavorites).setEnabled(true);
        this.findViewById(R.id.bEntrees).setEnabled(true);
        this.findViewById(R.id.bDesserts).setEnabled(true);
        this.findViewById(R.id.bDrinks).setEnabled(true);

        // disable this button
        view.setEnabled(false);

        // modify query based on which category button is used
        switch (view.getId()) {
            case R.id.bAppetizers:
                setTitle("Menu - Appetizers");
                query.whereEqualTo("Type", "Appatizer");
                break;
            case R.id.bFavorites:
                setTitle("Menu - Favorites");
                query.whereNotEqualTo("Type", "Drink");
                query.addDescendingOrder("Frequency");
                query.setLimit(3);
                break;
            case R.id.bEntrees:
                setTitle("Menu - Entrees");
                query.whereEqualTo("Type", "Entree");
                break;
            case R.id.bDesserts:
                setTitle("Menu - Desserts");
                query.whereEqualTo("Type", "Dessert");
                break;
            case R.id.bDrinks:
                setTitle("Menu - Drinks");
                query.whereEqualTo("Type", "Drink");
                break;
        }

        // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override public void done(List<ParseObject> menuItems, ParseException e) {
                MenuAdapter adapter = new MenuAdapter(menuItems);
                ListView lvMenu = (ListView) findViewById(R.id.lvMenu);
                lvMenu.setAdapter(adapter);
            }
        });
    }


    // nested class used for the menu adapter
    private class MenuAdapter extends ArrayAdapter<ParseObject> {

        // constructor
        public MenuAdapter(List<ParseObject> objects) { super(MenuMain.this, 0, objects); }

        // function called whenever the list is created or scrolled
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_menu_item, parent, false);
            }

            // get the current item
            ParseObject item = getItem(position);

            // get pictures from parse in background
            item.getParseFile("Picture").getDataInBackground(new GetDataCallback() {
                private View view;

                private GetDataCallback initialize(View view) {
                    this.view = view;
                    return this;
                }

                @Override public void done(byte[] data, ParseException e) {
                    ImageView ivPicture = (ImageView)view.findViewById(R.id.ivPicture);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ivPicture.setImageBitmap(bitmap);
                }
            }.initialize(view));

            // get item name
            TextView tvItemName = (TextView)view.findViewById(R.id.tvItemName);
            String itemName = item.getString("ItemName");
            tvItemName.setText(itemName);

            // get price
            TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);
            float price = item.getNumber("Price").floatValue();
            String formattedPrice = String.format("$%.2f", price);
            if (formattedPrice.equals("$0.00")) formattedPrice = "Free";
            tvPrice.setText(formattedPrice);

            // get description
            TextView tvDescription = (TextView)view.findViewById(R.id.tvDescription);
            String description = item.getString("Description");
            tvDescription.setText(description);

            // return the view
            return view;
        }
    }
}