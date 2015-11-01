package com.github.CSCE4444ElectronicRestrauntSystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SubmitOrder extends AppCompatActivity {
    // TODO: call server button
    // TODO: submit order button
    // TODO: remove item buttons

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_submit_order);
        MainApplication application = (MainApplication)getApplicationContext();
        SubmitAdapter adapter = new SubmitAdapter(application.currentOrder);
        ListView lvSubmitOrder = (ListView)findViewById(R.id.lvSubmitOrder);
        lvSubmitOrder.setAdapter(adapter);

        // total price
        TextView tvTotalPrice = (TextView)findViewById(R.id.tvTotalPrice);
        float totalPrice = 0;
        for (OrderItem item : application.currentOrder) totalPrice += item.price;
        String formattedTotalPrice = String.format("$%.2f", totalPrice);
        tvTotalPrice.setText("Total: " + formattedTotalPrice);
    }

    private class SubmitAdapter extends ArrayAdapter<OrderItem> {
        // constructor
        public SubmitAdapter(List<OrderItem> items) { super(SubmitOrder.this, 0, items); }

        @Override public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_submit_item, parent, false);
            }

            // get the item
            OrderItem item = getItem(position);

            // get item name
            TextView tvItemName = (TextView)view.findViewById(R.id.tvItemName);
            tvItemName.setText(item.name);

            // get request
            TextView tvRequest = (TextView)view.findViewById(R.id.tvRequest);
            tvRequest.setText(item.request);
            LinearLayout layout = (LinearLayout)tvRequest.getParent().getParent();
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            if (item.request.equals("")) params.height = 0;
            else params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layout.setLayoutParams(params);

            // get price
            TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);
            float price = item.price;
            String formattedPrice = String.format("$%.2f", price);
            if (formattedPrice.equals("$0.00")) formattedPrice = "Free";
            tvPrice.setText(formattedPrice);

            return view;
        }
    }

}
