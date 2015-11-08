package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class ShareGeneral extends AppCompatActivity {

    //Both of these are used for Facebook
    CallbackManager callbackManager;
    ShareDialog shareDialogue = new ShareDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_general);

        //Initialize Facebook stuff
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //Grab the buttons
        ImageButton ibFacebook = (ImageButton) findViewById(R.id.ibFacebookShare);
        ImageButton ibTwitter = (ImageButton) findViewById(R.id.ibTwitterShare);
        Button bPayBill = (Button) findViewById(R.id.bPayBill);


        //Extra padding to ensure that no user is logged in
        LoginManager.getInstance().logOut();

        //Go to the pay bill screen
        bPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iPayBill = new Intent(ShareGeneral.this, PayOrder.class);
                startActivity(iPayBill);
            }
        });

        //Make a share dialogue - This is how we share to Facebook
        shareDialogue.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                //Success
                //Let the user know they succeeded and then log them out
                Toast.makeText(getApplicationContext(),
                        "Shared to Facebook!", Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onCancel() {
                //Canceled
                //Let the user know it was canceled and then log them out
                Toast.makeText(getApplicationContext(),
                        "Share operation was canceled!", Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException e) {
                //Error'd
                //Let them know there was an error and then log them out
                Toast.makeText(getApplicationContext(),
                        "Error: could not post to Facebook! Try again later.", Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logOut();
            }

        });

        //Go to Twitter stuff when button is clicked
        ibTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iShareTwitter = new Intent(ShareGeneral.this, ShareTwitter.class);
                startActivity(iShareTwitter);
            }
        });

        //Make sure we can display a share dialogue and open it up if so
        ibFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    //Throw everything we need into a builder
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Really Awesome Burgers")
                            .setContentDescription(
                                    "Check-in from Really Awesome Burgers")
                            .build();
                    //launch it ---- this is what actually shows the Facebook share screen
                    shareDialogue.show(linkContent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_general, menu);
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

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
