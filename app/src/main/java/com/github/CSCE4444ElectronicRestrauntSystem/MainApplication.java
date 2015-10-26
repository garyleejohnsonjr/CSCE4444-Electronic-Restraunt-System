package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Application;
import com.parse.Parse;

public class MainApplication extends Application{
    @Override public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "DHztViOQO3Dwu8Zq6tJAAELT48vgBrEcff3Lr5eH", "h5lDHUCoS3VNMR6alDfE2KT9VFDAv73TJeNvVycu");
    }
}
