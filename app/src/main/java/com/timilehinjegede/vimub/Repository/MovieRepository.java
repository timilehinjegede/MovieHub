package com.timilehinjegede.vimub.Repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.timilehinjegede.vimub.Models.Cast;
import com.timilehinjegede.vimub.Models.Movie;
import com.timilehinjegede.vimub.Models.Trailer;
import com.timilehinjegede.vimub.Volley.URLs;
import com.timilehinjegede.vimub.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieRepository {

    Context context ;
    private static MovieRepository movieRepository ;
    String url ;
    int count ;
    int pageNumber ;
    public  MovieRepositoryListener<ArrayList<Trailer>> arrayListMovieRepositoryListener ;

    public MovieRepository(Context context) {
        this.context = context;
    }

    public interface MovieRepositoryListener<Any> {

        void onMovieLoading(Any any);

        void onErrorLoading(String any);
    }


    public static synchronized MovieRepository getInstance(Context context){
        if (movieRepository == null){
            movieRepository = new MovieRepository(context);
        }
            return movieRepository;
    }

    public void getMoviesBy(String filter){

        switch (filter) {
            case "Popular":
                url = URLs.POPULAR_MOVIE_URL;
                break;
            case "Upcoming":
                url = URLs.UPCOMING;
                break;
            case "Top Rated":
                url = URLs.TOP_RATED_MOVIE_URL;
                break;
            default:
                url = "";
                break;
        }

//        apiRequest(arrayListMovieRepositoryListener);

    }

    public void getCasts(final MovieRepositoryListener<ArrayList<Cast>> arrayListMovieRepositoryListener1, String movieId){

        url = URLs.BASE_URL_MOVIE+"/"+movieId+"/"+URLs.CASTS ;

        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("api_key",URLs.API_KEY)
                .appendQueryParameter("page","1")
                .build();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Cast> castArrayList = parseCasts(response);

                        arrayListMovieRepositoryListener1.onMovieLoading(castArrayList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        arrayListMovieRepositoryListener1.onErrorLoading("Error");
                    }
                }
        );


        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    public void getTrailers(final  MovieRepositoryListener<ArrayList<Trailer>> arrayListMovieRepositoryListener2, final String movieId){

        url = URLs.BASE_URL_MOVIE+"/"+movieId+"/"+URLs.TRAILERS ;

        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("api_key",URLs.API_KEY)
                .build();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        ArrayList<Trailer> trailerArrayList = parseTrailers(response);

                        ArrayList<Trailer> trailerArrayList = new ArrayList<>();

                        try {
                            JSONArray jsonArray = response.getJSONArray("youtube");

                            for (int i=0; i < jsonArray.length(); i++){

                                JSONObject currentJsonObject = jsonArray.getJSONObject(i);

                                String name = currentJsonObject.getString("name");
                                String source = currentJsonObject.getString("source");
                                String thumbnail = URLs.YOUTUBE_THUMBNAIL+source+"/"+"default.jpg";

                                Trailer trailer = new Trailer(source,name,thumbnail);

                                trailerArrayList.add(trailer);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        arrayListMovieRepositoryListener2.onMovieLoading(trailerArrayList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        arrayListMovieRepositoryListener2.onErrorLoading("Error");
                    }
                }
        );


        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }


    public void apiRequest(final MovieRepositoryListener<ArrayList<Movie>> arrayListMovieRepositoryListener){
        //Appending the API key to the url
        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("api_key",URLs.API_KEY)
                .appendQueryParameter("page","1")
                .build();


        //Checking the URL gotten after appending the api key
        Log.d("apikey",uri.toString());


        //Making a JSONObject request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri.toString(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<Movie> movies = parseMovies(response);

                        arrayListMovieRepositoryListener.onMovieLoading(movies);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        arrayListMovieRepositoryListener.onErrorLoading("Error");
                    }
                }
        );
        //Adding to the request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

//        }

    }

    private ArrayList<Cast> parseCasts(JSONObject jsonObject){
        ArrayList<Cast> casts = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("cast");

            for (int i=0; i < jsonArray.length(); i++){

                JSONObject currentJsonObject = jsonArray.getJSONObject(i);

                int id = currentJsonObject.getInt("id");
                String character = currentJsonObject.getString("character");
                String name = currentJsonObject.getString("name");
                String profile_path = currentJsonObject.getString("profile_path");

                Cast cast = new Cast(id,name,character,profile_path);

                casts.add(cast);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return casts;
    }

    private ArrayList<Trailer> parseTrailers(JSONObject jsonObject) {
        ArrayList<Trailer> trailerArrayList = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("youtube");

            for (int i=0; i < jsonArray.length(); i++){

                JSONObject currentJsonObject = jsonArray.getJSONObject(i);

                String name = currentJsonObject.getString("name");
                String source = currentJsonObject.getString("source");
                String thumbnail = URLs.YOUTUBE_THUMBNAIL;

                Trailer trailer = new Trailer(source,name,thumbnail);

                trailerArrayList.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerArrayList;
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
                String title = currentJsonObject.getString("title");
                int rating = currentJsonObject.getInt("vote_average");
                String poster = currentJsonObject.getString("poster_path");
                String releaseDate = currentJsonObject.getString("release_date");
                String overview  = currentJsonObject.getString("overview");
                String backdrop = currentJsonObject.getString("backdrop_path");

                String mReleaseDate = releaseDate.substring(0,4);
                float fiveStarRating = rating/2 ;

                Movie movie = new Movie(id,title,releaseDate,poster,fiveStarRating,overview,backdrop);
//                Trailer moviePoster = new Trailer(id,poster);

                movies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies ;
    }

}