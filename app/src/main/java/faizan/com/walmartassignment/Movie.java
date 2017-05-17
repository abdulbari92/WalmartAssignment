package faizan.com.walmartassignment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by buste on 5/17/2017.
 */

public class Movie  implements Parcelable{
    private String title;
    private String image;
    private String overview;
    private String releaseDate;
    private String language;
    private int voterCount;
    private double rating;

    public Movie(){

    }
    // All getters and setters for the attributes of Movies
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getVoterCount() {
        return voterCount;
    }

    public void setVoterCount(int voterCount) {
        this.voterCount = voterCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    // overridden method for parceble interface
    @Override
    public int describeContents() {
        return 0;
    }

//    overridden method to set object as parceble
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(language);
        dest.writeInt(voterCount);
        dest.writeDouble(rating);
    }

//    contructor for parceble
    private Movie(Parcel in){
        this.title = in.readString();
        this.image = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.language = in.readString();
        this.voterCount = in.readInt();
        this.rating = in.readDouble();
    }

    // creator mthod for parceble
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
