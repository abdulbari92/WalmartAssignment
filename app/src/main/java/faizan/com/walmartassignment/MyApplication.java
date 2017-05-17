package faizan.com.walmartassignment;

import android.app.Application;
import android.content.Context;

/**
 * Created by buste on 3/30/2017.
 */

public class MyApplication extends Application {
    //creating an instance for volley
    public static MyApplication myInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        // instantiating instance
        myInstance = this;
    }

    /**
     *
     * @return = Application Context
     */
    public static Context getAppContext() {
        return myInstance.getApplicationContext();
    }

    /**
     *
     * @return = Application instance
     */
    public static MyApplication getMyInstance() {
        return myInstance;
    }
}