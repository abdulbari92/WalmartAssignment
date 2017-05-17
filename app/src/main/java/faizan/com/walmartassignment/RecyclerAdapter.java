package faizan.com.walmartassignment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by buste on 5/17/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MovieHolder> {

    private ArrayList<Movie> mMovies;

    public RecyclerAdapter(ArrayList<Movie> movies) {
        mMovies = movies;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImage;
        private TextView mTitle;
        private TextView mLanguage;
        private TextView mReleaseDate;
        private TextView mPeopleRated;
        private TextView mRating;
        private Movie mMovie;

        public MovieHolder(View v) {
            super(v);

            mImage = (ImageView) v.findViewById(R.id.imageViewMovie);
            mTitle = (TextView) v.findViewById(R.id.textViewTitle);
            mLanguage = (TextView) v.findViewById(R.id.textViewLanguage);
            mReleaseDate = (TextView) v.findViewById(R.id.textViewReleaseDate);
            mPeopleRated = (TextView) v.findViewById(R.id.textViewPeopleRated);
            mRating = (TextView) v.findViewById(R.id.textViewRating);
            v.setOnClickListener(this);
        }

        public void bindMovie(Movie movie) {
            mMovie = movie;
            if(movie.getImage().compareTo("http://image.tmdb.org/t/p/w185/null")==0){
                mImage.setImageResource(R.drawable.noimagefound);
            }else {
                Picasso.with(mImage.getContext()).load(movie.getImage()).into(mImage);
            }
            mTitle.setText(movie.getTitle());
            mLanguage.setText("Language: " + (movie.getLanguage().isEmpty()? "Unknown" : movie.getLanguage()));
            mReleaseDate.setText("Release Date: " +( movie.getReleaseDate().isEmpty()? "Unknown" : movie.getReleaseDate()));
            mPeopleRated.setText("#Voters: " + String.valueOf(movie.getVoterCount()));
            mRating.setText("Rating: " + String.valueOf(movie.getRating()));
        }

        @Override
        public void onClick(View v) {
            Intent movieFocusIntent  = new Intent(itemView.getContext(),MovieFocus.class);
            movieFocusIntent.putExtra("Movie",mMovie);
            v.getContext().startActivity(movieFocusIntent);
        }
    }

    @Override
    public RecyclerAdapter.MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie, parent, false);
        return new MovieHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MovieHolder holder, int position) {
        Movie itemMovie = mMovies.get(position);
        holder.bindMovie(itemMovie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

}