package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by User on 10/2/2015.
 */
public class MainApplication extends Application{
    public void onCreate() {
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "DHztViOQO3Dwu8Zq6tJAAELT48vgBrEcff3Lr5eH", "h5lDHUCoS3VNMR6alDfE2KT9VFDAv73TJeNvVycu");
    }
}
