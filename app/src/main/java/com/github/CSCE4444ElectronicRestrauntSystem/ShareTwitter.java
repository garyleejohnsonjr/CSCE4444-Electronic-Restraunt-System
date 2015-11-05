package com.github.CSCE4444ElectronicRestrauntSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.fabric.sdk.android.Fabric;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class ShareTwitter extends AppCompatActivity {

    //Simple method used to log out and remove session so next user can't access previous user's account
    public void logMeOut(){
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
    }

    //TwitterLoginButton is from the Twitter API
    private TwitterLoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_twitter);

        //EditText field where user can enter custom Tweet messages
        final EditText etTweetText = (EditText) findViewById(R.id.etTweetText);

        //API keys SHOULD be obfuscated in the real world
        TwitterAuthConfig authConfig = new TwitterAuthConfig("LbxFz1170RdCnXUYlKqHuymII", "9SYdM5F5muue1AhQuFIcP8yoJzPeZxE2KOdlKPKB49gpWVNSJK");
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());

        //Set the button and tell it what to do when clicked
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // with your app's user model
                //String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


                //At this point, given that we're in the success method, the user is logged in
                //Using this session, the text is grabbed and inserted into a Tweet
                //This Tweet is automatically posted
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                StatusesService statusesService = twitterApiClient.getStatusesService();
                statusesService.update(etTweetText.getText().toString(),
                        null, null, null, null, null, null, null, new Callback<Tweet>() {
                            @Override
                            public void success(Result<Tweet> result) {
                                //Success
                                Toast.makeText(getApplicationContext(),
                                        "Shared to Twitter!", Toast.LENGTH_LONG).show();
                            }

                            public void failure(TwitterException exception) {
                                //Failed
                                Toast.makeText(getApplicationContext(),
                                        "Whoops! Something went wrong. Try again later.", Toast.LENGTH_LONG).show();
                            }
                        });
                //This function logs the user out so that one cannot accidentally leave their account logged in
                logMeOut();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        //TweetComposer.Builder builder = new TweetComposer.Builder(this)
        //        .text("I'm currently at Really Awesome Burgers! Check out their hype food!");
        //builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_twitter, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
