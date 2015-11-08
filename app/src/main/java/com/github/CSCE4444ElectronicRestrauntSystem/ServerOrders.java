package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ServerOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
    }

    //Refreshes on Resume
    protected void onResume() {
        super.onResume();
        setLayout();
    }

    //Sets Content Layout
    public void setLayout(){
        setContentView(R.layout.activity_server_orders);

        //Gets Table number
        final Intent i = getIntent();

        final ListView lvOrderList = (ListView)findViewById(R.id.lvOrderList);
        final int tableNum = 0;

        //New Order button
        Button bNewOrder = (Button)findViewById(R.id.bNewOrder);

        //Button takes you to menu
        bNewOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent iTableStatus = new Intent(ServerOrders.this, MenuMain.class);
                MainApplication application = (MainApplication)getApplicationContext();
                application.currentTable = i.getIntExtra("Number", tableNum);
                iTableStatus.putExtra("Server", true);
                startActivity(iTableStatus);
            }
        });

        //Adapter to get Order Numbers
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("TableNumber", i.getIntExtra("Number", tableNum));
        query.whereNotEqualTo("Status", "Paid");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> tables,
                             ParseException e) {
                //Todo: Submit nothing error
                if (e == null) {
                    OrderAdapter adapter = new OrderAdapter(tables);
                    lvOrderList.setAdapter(adapter);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_orders, menu);
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

    // Click Order event
    public void ServerTableOrders(View view) {

        ListView lvOrderList = (ListView) findViewById(R.id.lvOrderList);

        // get item to remove
        final int position = lvOrderList.getPositionForView(view);
        final int tableNum = 0;
        final Intent i = getIntent();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.whereEqualTo("TableNumber", i.getIntExtra("Number", tableNum));
        query.whereNotEqualTo("Status", "Paid");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> Orders,
                             ParseException e) {
                //Todo: Submit nothing error
                if (e == null) {
                    Intent iTableStatus = new Intent(ServerOrders.this, ServerTableOrder.class);
                    iTableStatus.putExtra("Number", i.getIntExtra("Number", tableNum));
                    iTableStatus.putExtra("Order", position);
                    startActivity(iTableStatus);
                }
            }
        });
    }

    private class OrderAdapter extends ArrayAdapter<ParseObject>  {
        // constructor
        public OrderAdapter(List<ParseObject> items) { super(ServerOrders.this, 0, items); }

        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_order_list, parent, false);
            }

            //Creates Order Number
            int orderNum = position + 1;

            // Saves Order Number on Button
            Button bOrder = (Button)view.findViewById(R.id.bOrder);
            bOrder.setText("Order Number: " + orderNum);

            return view;
        }
    }
}
