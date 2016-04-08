package com.alpha.popularmoviesi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridViewAdapter gridViewAdapter;
    private ArrayList<MovieItem> mGridData;
    private SharedPreferences movieSharedPreferences;
    private int sortSharedPref;

    public MainActivityFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        sortSharedPref = Integer.parseInt(movieSharedPreferences.getString("sorting_pref", "0"));
        new GetPopularMoviesData().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridview = (GridView) rootview.findViewById(R.id.movies_gridview);
        movieSharedPreferences = PreferenceManager.getDefaultSharedPreferences(rootview.getContext());
        sortSharedPref = Integer.parseInt(movieSharedPreferences.getString("sorting_pref", "0"));

        mGridData = new ArrayList<>();
        gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, mGridData);
        gridview.setAdapter(gridViewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                Intent intent = new Intent(getActivity(), MovieDetails.class);
                Bundle extras = new Bundle();
                extras.putParcelableArrayList("gridData", mGridData);
                extras.putInt("position", position);
                intent.putExtra("movieData", extras);
                startActivity(intent);
            }
        });

        return rootview;
    }

    public class GetPopularMoviesData extends AsyncTask<Void, Integer, String> {

        final ProgressDialog dialog = new ProgressDialog(getActivity());

        String movieJsonStr = null;

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... v) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            try {

                StringBuilder uri = new StringBuilder();
                uri.append(MainActivity.BaseURL);
                if (sortSharedPref == 1) {
                    uri.append("popular");
                } else if (sortSharedPref == 2) {
                    uri.append("top_rated");
                }
                uri.append("?api_key=").append(MainActivity.API_KEY);
                URL url = new URL(uri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("Main Activity Fragment", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Main Activity Fragment", "Error closing stream", e);
                    }
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String results) {

            gridViewAdapter.clear();
            try {
                if(movieJsonStr!=null)
                getMovieDataFromJson(movieJsonStr);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            gridViewAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }

        private void getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MOVIE_RESULTS = "results";
            final String MOVIE_IMAGE = "poster_path";
            final String MOVIE_BACK_DROP = "backdrop_path";
            final String MOVIE_TITLE = "title";
            final String MOVIE_OVERVIEW = "overview";
            final String MOVIE_RELEASE_DATE = "release_date";
            final String MOVIE_POPULARITY = "popularity";
            final String MOVIE_VOTES = "vote_average";


            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MOVIE_RESULTS);

            mGridData.clear();

            for (int i = 0; i < movieArray.length(); i++) {

                JSONObject movieDetails = movieArray.getJSONObject(i);
                MovieItem item = new MovieItem();
                item.setImageView(MainActivity.ImageURL + movieDetails.getString(MOVIE_IMAGE));
                item.setBackdroppath(MainActivity.ImageURL + movieDetails.getString(MOVIE_BACK_DROP));
                item.setOverview(movieDetails.getString(MOVIE_OVERVIEW));
                item.setPopularity(movieDetails.getString(MOVIE_POPULARITY));
                item.setReleaseDate(movieDetails.getString(MOVIE_RELEASE_DATE));
                item.setTitle(movieDetails.getString(MOVIE_TITLE));
                item.setVote_average(movieDetails.getString(MOVIE_VOTES));

                mGridData.add(item);
            }
        }
    }
}

