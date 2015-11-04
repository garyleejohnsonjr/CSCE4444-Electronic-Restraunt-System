package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Application;
import com.parse.Parse;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.LinkedList;

public class MainApplication extends Application{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "OiHw7cS2y0yoP730H7gu1H0Af";
    private static final String TWITTER_SECRET = "vuBOyEM4m08ZHVXUNmav2jJ8wYPGctdtic5IlIV7JeyiATZxsW";

    public LinkedList<OrderItem> currentOrder = new LinkedList<>();

    @Override public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        // parse stuff
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "DHztViOQO3Dwu8Zq6tJAAELT48vgBrEcff3Lr5eH", "h5lDHUCoS3VNMR6alDfE2KT9VFDAv73TJeNvVycu");
    }
}
