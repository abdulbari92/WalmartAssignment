package faizan.com.walmartassignment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    // Material SearchView for searchable Tootlbar
    MaterialSearchView searchView;
    // recycler view to show the list of movies
    private RecyclerView mRecyclerView;
    // LinearLayoutManager for recycler view
    private LinearLayoutManager mLinearLayoutManager;
    // Adapter to set model Object list into recyclerview
    private RecyclerAdapter mAdapter;
    // ArrayList to store movielist
    ArrayList<Movie> mMoviesList = Database.getmMoviesList();
    // URL to store webservice address
    String URL;
    // pagecounter to track number of pages user already read
    int pageCounter = 1;
    // to store search term
    String searchTerm;
    // requestqueue for volley
    RequestQueue requestQueue;
    // progress dialog to show while performing network opperations using volley
    ProgressDialog progressDialog;
    // helper for scroll view listener when list is empty
    int i = 0;

    /**
     * @param savedInstanceState
     * initializing all views in onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting the layout for the activity
        setContentView(R.layout.activity_main);

        // initializing the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setting the toolbar as ActionBar
        setSupportActionBar(toolbar);

        // initializing the recycler view that will show the list of movies
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        // initializing the LayoutManager for the recycler view
        mLinearLayoutManager = new LinearLayoutManager(this);
        //setting the LayoutManager to the recycler view
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // addOnScrollListener to listen to scroll activity
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // getting totalItemCount in the recycler view
                int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
                // condition to check if last item is reached
                if (totalItemCount == getLastVisibleItemPosition()+1) {
                    // if list is empty don't try to reload
                    if(!mMoviesList.isEmpty()) {
                        URL = "https://api.themoviedb.org/3/search/movie?api_key=" + getResources().getString(R.string.api_key) +
                                "&language=en-US&query=" + searchTerm + "&page=" + ++pageCounter + "&include_adult=false";
                        requestMovies();
                    }
                    // list is empty show the message
                    else{
                        if(i%3==0)
                            Toast.makeText(getBaseContext(),"You will have to provide a search term first",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // initilizaing the search view toolbar to take user input
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        //
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //take search term entered inside the search bar of searchview
                searchTerm = query;
                // clear list since new item is searched for
                mMoviesList.clear();
                // adding the webserver address with user provided parameters
                URL = "https://api.themoviedb.org/3/search/movie?api_key="+getResources().getString(R.string.api_key)+
                        "&language=en-US&query="+ query +"&page="+ pageCounter +"&include_adult=false";
                requestMovies();
                // setting te list into the adapter
                mAdapter = new RecyclerAdapter(mMoviesList);
                // setting the adapter to the recycler view
                mRecyclerView.setAdapter(mAdapter);
                return false;
            }
            // Not required for this assignment
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * method to request movies from the webservice
     */
    private void requestMovies() {
        // to get the requestQueue instance
        requestQueue = VolleySingleton.getInstance().getmRequestQueue();
        // Initializng the ProgressDialog
        progressDialog = new ProgressDialog(this);
        // Setting the message for the Progress Dialog
        progressDialog.setMessage("Loading..");
        // Making the ProgressBar Uncancelable
        progressDialog.setCancelable(false);
        // start showing the ProgressDialog
        progressDialog.show();

        //Initializing a JsonObjectRequest, with URL as a parameter
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null,
                // Handling the Response Listener
                new Response.Listener<JSONObject>() {
                    /**
                     *
                     * @param response = the response that we get from the WebService
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Gettingthe Json array from te jSOn Object
                            JSONArray jsonArray = response.getJSONArray("results");
                            // putting all the data in the Json Array into the ArrayList that will hold the whole list
                            for(int i = 0; i<jsonArray.length();++i){
                                Movie movie = new Movie();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                movie.setTitle(jsonObject.getString("title"));
                                movie.setImage("http://image.tmdb.org/t/p/w185/" + jsonObject.getString("poster_path"));
                                movie.setOverview(jsonObject.getString("overview"));
                                movie.setReleaseDate(jsonObject.getString("release_date"));
                                movie.setLanguage(jsonObject.getString("original_language"));
                                movie.setVoterCount(jsonObject.getInt("vote_count"));
                                movie.setRating(jsonObject.getDouble("vote_average"));
                                mMoviesList.add(movie);
                                // Notify the Adapter that te dataset has been changed
                                mAdapter.notifyDataSetChanged();
                                progressDialog.hide();
                            }
                            // After the job is done, Dismissing the Progress Dialog
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            // printing stack trace for developers
                            e.printStackTrace();
                            // Telling User there is an error
                            Toast.makeText(getApplicationContext(),"Error: "+ e,
                                    Toast.LENGTH_LONG).show();
                            // Hiding Progress Dialog
                            progressDialog.hide();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Showing the user there is an error
                        Toast.makeText(getApplicationContext(),"Error: "+ error,
                                Toast.LENGTH_LONG).show();
                        // Dismissing the progree dialog
                        progressDialog.hide();
                        //Log for developer
                        Log.e("VolleyError", String.valueOf(error));
                    }
                }
        );
        // Adding the Json Object request to the request for it execute
        requestQueue.add(jsonObjectRequest);
    }

    /**
     *
     * @return = last visible item in recycler view
     */
    private int getLastVisibleItemPosition() {
        return mLinearLayoutManager.findLastVisibleItemPosition();
    }

    /**
     *
     * @param menu = to inflate required style for the menu
     * @return = true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflating the menu
        getMenuInflater().inflate(R.menu.options_menu, menu);
        // for the desired background
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

}
