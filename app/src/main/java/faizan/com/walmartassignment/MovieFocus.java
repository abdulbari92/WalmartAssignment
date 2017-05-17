package faizan.com.walmartassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Activity to show the details of a movie
 */

public class MovieFocus extends AppCompatActivity {


    // declaring all the views in the activity
    ImageView imageViewMovieFocusImage;
    TextView textViewTitle, textViewLanguage, textViewReleaseDate, textViewRaters, textViewRating, textViewOverview;
    Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_focus);
        // initailizing all the views
        imageViewMovieFocusImage = (ImageView) findViewById(R.id.imageViewMovieFocusImage);
        textViewTitle = (TextView) findViewById(R.id.textViewMovieFocusTitle);
        textViewLanguage = (TextView) findViewById(R.id.textViewMovieFocusLanguage);
        textViewReleaseDate = (TextView) findViewById(R.id.textViewMovieFocusReleaseDate);
        textViewRaters = (TextView) findViewById(R.id.textViewMovieFocusRaters);
        textViewRating = (TextView) findViewById(R.id.textViewMovieFocusRating);
        textViewOverview = (TextView) findViewById(R.id.textViewMovieFocusOverview);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        // setting back button listener
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finishing the activity
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // getting parcel object from the previous Activity
        Movie movie = getIntent().getParcelableExtra("Movie");
        // setting values for all the views
        Picasso.with(imageViewMovieFocusImage.getContext()).load(movie.getImage()).into(imageViewMovieFocusImage);
        textViewTitle.setText(movie.getTitle());
        textViewLanguage.setText("Language: "+ (movie.getLanguage().isEmpty()?"Unknown" : movie.getLanguage()));
        textViewReleaseDate.setText("Release Date: " + (movie.getReleaseDate().isEmpty()?"Unknown":movie.getReleaseDate()));
        textViewRaters.setText("#Voters: " + String.valueOf(movie.getVoterCount()));
        textViewRating.setText("Rating: " + String.valueOf(movie.getRating()));
        textViewOverview.setText(movie.getOverview().isEmpty()?"Unknown" : movie.getOverview());
    }
}
