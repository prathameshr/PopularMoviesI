package com.alpha.popularmoviesi;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MovieItem data and setter & getter methods
 */
public class MovieItem implements Parcelable {

    private String imageView;
    private String backdroppath;
    private String title;
    private String overview;
    private String releaseDate;
    private String popularity;
    private String vote_average;


    public MovieItem() {

    }

    private MovieItem(Parcel parcel) {
        imageView = parcel.readString();
        backdroppath = parcel.readString();
        title = parcel.readString();
        overview = parcel.readString();
        releaseDate = parcel.readString();
        popularity = parcel.readString();
        vote_average = parcel.readString();
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(imageView);
        out.writeString(backdroppath);
        out.writeString(title);
        out.writeString(overview);
        out.writeString(releaseDate);
        out.writeString(popularity);
        out.writeString(vote_average);
    }


    public static final Parcelable.Creator<MovieItem> CREATOR
            = new Parcelable.Creator<MovieItem>() {
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

}
