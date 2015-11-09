package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PayCredit extends AppCompatActivity {

    String receipt = "";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_credit);

        // order number
        int orderNumber = getIntent().getExtras().getInt("OrderNumber");
        setTitle("Pay Credit - Order #" + (orderNumber + 1));

        // order amount
        TextView tvTotal = (TextView)findViewById(R.id.tvTotal);
        float total = getIntent().getExtras().getFloat("Total");
        String formattedTotal = String.format("$%.2f", total);
        tvTotal.setText(formattedTotal);
    }

    // call server event
    public void callServer(View view) {
        Intent intent = new Intent(this, CallServer.class);
        startActivity(intent);
    }

    // submit button event
    public void submit(View view) {
        EditText etCardName = (EditText) findViewById(R.id.etCardName);
        EditText etCardNumber = (EditText) findViewById(R.id.etCardNumber);
        EditText etCardZip = (EditText) findViewById(R.id.etCardZip);
        if (etCardName.length() == 0) {
            Toast.makeText(getApplicationContext(), "Name must not be blank.", Toast.LENGTH_LONG).show();
        } else if (etCardNumber.length() != 16) {
            Toast.makeText(getApplicationContext(), "Card number must have 16 numbers.", Toast.LENGTH_LONG).show();
        } else if (etCardZip.length() != 5) {
            Toast.makeText(getApplicationContext(), "ZIP code must have 5 numbers.", Toast.LENGTH_LONG).show();
        } else {

            // get order ID
            String orderID = getIntent().getExtras().getString("OrderID");

            // build query
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
            query.whereEqualTo("objectId", orderID);

            // run query
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject order, ParseException e) {
                    // set card name
                    EditText etCardName = (EditText) findViewById(R.id.etCardName);
                    String cardName = etCardName.getText().toString();
                    order.put("CardName", cardName);
                    receipt += "Cardholder's Name: " + cardName + "\n";

                    // set card number
                    EditText etCardNumber = (EditText) findViewById(R.id.etCardNumber);
                    long cardNumber = Long.valueOf(etCardNumber.getText().toString());
                    order.put("CardNumber", cardNumber);
                    receipt += "CardNumber: xxxx-xxxx-xxxx-" + String.valueOf(cardNumber).substring(12) + "\n";

                    // order total
                    TextView tvTotal = (TextView)findViewById(R.id.tvTotal);
                    String total = tvTotal.getText().toString();
                    receipt += "Order Total: " + total + "\n";

                    // set card zip
                    EditText etCardZip = (EditText) findViewById(R.id.etCardZip);
                    int cardZip = Integer.valueOf(etCardZip.getText().toString());
                    order.put("CardZip", cardZip);

                    // set gratuity
                    EditText etGratuity = (EditText) findViewById(R.id.etGratuity);
                    float gratuity = 0.0f;
                    if (!etGratuity.getText().toString().equals("")) {
                        gratuity = Float.valueOf(etGratuity.getText().toString());
                    }
                    order.put("Gratuity", gratuity);
                    receipt += "Gratuity: #" + gratuity + "\n";

                    // set paid
                    order.put("Paid", true);

                    // save the order
                    try {
                        order.save();
                    } catch (ParseException e2) {
                        // do nothing
                    }

                    // toast
                    Toast.makeText(getApplicationContext(), "Payment Accepted", Toast.LENGTH_LONG).show();

                    // receipt
                    new AlertDialog.Builder(PayCredit.this)
                            .setTitle("Receipt")
                            .setMessage("Would you like to print or email a receipt?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("message/rfc822");
                                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Receipt from Really Awesome Burgers");
                                    intent.putExtra(Intent.EXTRA_TEXT, receipt);
                                    startActivity(Intent.createChooser(intent, "Select Email Client:"));
                                    survey();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    survey();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });
        }
    }

    public void survey() {
        new AlertDialog.Builder(PayCredit.this)
                .setTitle("Survey")
                .setMessage("Would you like to take a brief survey about your experience dining with us?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PayCredit.this, CustomerSurvey.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
