package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class ShareFacebook extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.FBlogin_button);
        loginButton.setReadPermissions("user_friends");


        shareDialogue = new ShareDialog(this);
        shareDialogue.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                //Success
                Toast.makeText(getApplicationContext(),
                        "Shared to Facebook!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                //Canceled
                Toast.makeText(getApplicationContext(),
                        "Canceled to Facebook!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                //Error'd
                Toast.makeText(getApplicationContext(),
                        "Error'd to Facebook!", Toast.LENGTH_LONG).show();
            }
        });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Really Awesome Burgers")
                    .setContentDescription(
                            "Check-in from Really Awesome Burgers")
                    .build();

            shareDialogue.show(linkContent);
        }

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                //Success
                Toast.makeText(getApplicationContext(),
                        "Shared to Facebook!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                // App code

                //Success
                Toast.makeText(getApplicationContext(),
                        "Canceled to Facebook!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                //Success
                Toast.makeText(getApplicationContext(),
                        "Error'd to Facebook!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_facebook, menu);
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
