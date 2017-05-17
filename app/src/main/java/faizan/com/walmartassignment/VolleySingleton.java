package faizan.com.walmartassignment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by buste on 5/17/2017.
 */

public class VolleySingleton {
    // declaring and initialing Static Class Object
    private static VolleySingleton volleySingleton = null;

    // declaring message queue
    RequestQueue mRequestQueue;

    // constructor to intilize volley instance
    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    // constructor for creating volley instance
    public static VolleySingleton getInstance(){

        // if null get instance
        if (volleySingleton == null) {
            volleySingleton = new VolleySingleton();
        }

        // return instance
        return volleySingleton;
    }

    // getter for requestqueue
    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }


}