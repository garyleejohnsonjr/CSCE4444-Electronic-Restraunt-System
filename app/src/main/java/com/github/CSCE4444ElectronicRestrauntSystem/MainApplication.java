package com.github.CSCE4444ElectronicRestrauntSystem;

import android.app.Application;
import com.parse.Parse;

import java.util.LinkedList;
import java.util.List;

public class MainApplication extends Application{
    public CurrentOrder currentOrder= new CurrentOrder();

    public class CurrentOrder {
        LinkedList<Item> items = new LinkedList<>();

        class Item {
            String name;
            String request;

            Item (String name, String request) {
                this.name = name;
                this.request = request;
            }
        }

        public void addItem(String name, String request) {
            items.addLast(new Item(name, request));
        }
    }

    @Override public void onCreate() {
        super.onCreate();

        // parse stuff
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "DHztViOQO3Dwu8Zq6tJAAELT48vgBrEcff3Lr5eH", "h5lDHUCoS3VNMR6alDfE2KT9VFDAv73TJeNvVycu");
    }
}
