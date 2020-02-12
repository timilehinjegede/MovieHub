package com.timilehinjegede.vimub.Repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.timilehinjegede.vimub.Models.Movie;
import com.timilehinjegede.vimub.Models.Trailer;
import com.timilehinjegede.vimub.Volley.URLs;
import com.timilehinjegede.vimub.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TVRepository {

    Context context ;
    private static TVRepository tvRepository ;
    String url ;
    int count ;
    int pageNumber ;

    public TVRepository(Context context) {
        this.context = context;
    }

    public interface TVRepositoryListener<Any> {

        void onTvLoading(Any any);

        void onErrorLoading(String any);
    }

    public static synchronized TVRepository getInstance(Context context){
        if (tvRepository == null){
            tvRepository = new TVRepository(context);
        }
        return tvRepository;
    }

    public void getTVBy(String filter) {

        switch (filter) {
            case "Popular":
                url = URLs.POPULAR_TV_URL;
                break;
            case "Top Rated":
                url = URLs.TOP_RATED_TV_URL;
                break;
            default:
                url = "";
                break;
        }

    }

        public void apiRequest(final TVRepositoryListener<ArrayList<Movie>> arrayListTVRepositoryListener){

            //Appending the API key to the url
        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("api_key",URLs.API_KEY)
//                .appendQueryParameter("page","1")
                .build();


        //concatinating directly

        //Checking the URL gotten after appending the api key
        Log.d("apikey",uri.toString());


//        while(pageNumber < count ){

        //Making a JSONObject request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<Movie> movies = parseMovies(response);

                        arrayListTVRepositoryListener.onTvLoading(movies);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        arrayListTVRepositoryListener.onErrorLoading("Error connecting to the API");
                    }
                }
        );
        //Adding to the request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

        }

    //processing the JSON object from the popularMovies method
    private ArrayList<Movie> parseMovies(JSONObject jsonObject){
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            count = jsonObject.getInt("total_pages");

            for (int i = 0 ; i < jsonArray.length() ; i++){

                JSONObject currentJsonObject = jsonArray.getJSONObject(i);

                int id = currentJsonObject.getInt("id");
                String title = currentJsonObject.getString("name");
                int rating = currentJsonObject.getInt("vote_average");
                String poster = currentJsonObject.getString("poster_path");
                String releaseDate = currentJsonObject.getString("first_air_date");
                String overview = currentJsonObject.getString("overview");
                String backdrop = currentJsonObject.getString("backdrop_path");

                String mReleaseDate = releaseDate.substring(0,4);
                float fiveStarRating = rating/2 ;

                Movie movie = new Movie(id,title,mReleaseDate,poster,fiveStarRating,overview,backdrop);
//                Trailer moviePoster = new Trailer(id,poster);

                movies.add(movie);
//                movies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies ;
    }

}
