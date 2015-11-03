package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

//Main Kitchen Page
//Todo: This is basically just a copy of the temporary CustomerMain
public class KitchenMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);
        findList();

        //Button bPayBill = (Button) findViewById(R.id.bPayBill);

        //bPayBill.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
            //    Intent iPayBill = new Intent(KitchenMain.this, PayBill.class);
            //    startActivity(iPayBill);
            //}
        //});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kitchen_main, menu);
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
    public void findList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("Status","Placed");

    // run the query in the background, then create and set the adapter
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orderItems, ParseException e) {
                KitchenAdapter adapter = new KitchenAdapter(orderItems);
                ListView lvNewOrderScroll = (ListView)findViewById(R.id.lvNewOrderScroll);
                lvNewOrderScroll.setAdapter(adapter);
            }
        });
    }

    private class KitchenAdapter extends ArrayAdapter<ParseObject> {

        // constructor
        public KitchenAdapter(List<ParseObject> objects) { super(KitchenMain.this, 0, objects); }

        // function called whenever the list is created or scrolled
        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_menu_item, parent, false);
            }

            // get the current item
            ParseObject item = getItem(position);

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

