package faizan.com.walmartassignment;

import java.util.ArrayList;

/**
 * Created by buste on 5/17/2017.
 */

public class Database {
    public static ArrayList<Movie> getmMoviesList() {
        return mMoviesList;
    }

    private static ArrayList<Movie> mMoviesList = new ArrayList();
}
