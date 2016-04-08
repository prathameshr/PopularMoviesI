package com.alpha.popularmoviesi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {


    public MovieDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_movie_details, container, false);

        ArrayList<MovieItem> arrayList;
        DecimalFormat df = new DecimalFormat("##.#");
        Bundle extras = getActivity().getIntent().getBundleExtra("movieData");
        int position = extras.getInt("position");
        arrayList = extras.getParcelableArrayList("gridData");

        ImageView movie_image = (ImageView) rootview.findViewById(R.id.movie_image);
        ImageView movie_poster = (ImageView) rootview.findViewById(R.id.movie_poster);
        TextView movie_title = (TextView) rootview.findViewById(R.id.movie_title);
        TextView movie_release_date = (TextView) rootview.findViewById(R.id.movie_release_date);
        TextView movie_overview = (TextView) rootview.findViewById(R.id.movie_overview);
        TextView movie_votes = (TextView) rootview.findViewById(R.id.movie_votes);
        TextView movie_popularity = (TextView) rootview.findViewById(R.id.movie_popularity);

        if (arrayList.get(position) != null) {
            Picasso.with(getActivity()).load(arrayList.get(position).getBackdroppath()).into(movie_image);
            Picasso.with(getActivity()).load(arrayList.get(position).getImageView()).into(movie_poster);
            movie_poster.setContentDescription(arrayList.get(position).getTitle());
            movie_image.setContentDescription(arrayList.get(position).getTitle());
            movie_title.setText(arrayList.get(position).getTitle());
            movie_release_date.setText(arrayList.get(position).getReleaseDate());
            movie_overview.setText(arrayList.get(position).getOverview());
            movie_votes.setText(df.format(Double.parseDouble(arrayList.get(position).getVote_average())) + "/10");
            movie_popularity.setText(df.format(Double.parseDouble(arrayList.get(position).getPopularity())));
        }

        return rootview;
    }


}
