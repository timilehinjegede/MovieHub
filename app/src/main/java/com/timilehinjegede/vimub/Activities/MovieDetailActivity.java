package com.timilehinjegede.vimub.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timilehinjegede.vimub.Adapters.MovieCastListAdapter;
import com.timilehinjegede.vimub.Adapters.MovieCastsAdapter;
import com.timilehinjegede.vimub.Adapters.MovieTrailerAdapter;
import com.timilehinjegede.vimub.Fragments.ModalBottomSheet;
import com.timilehinjegede.vimub.Models.Cast;
import com.timilehinjegede.vimub.Models.Trailer;
import com.timilehinjegede.vimub.R;
import com.timilehinjegede.vimub.Repository.MovieRepository;
import com.timilehinjegede.vimub.Volley.URLs;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    TextView movieTitle , movieGenre , movieReleaseDate , movieSummary ;
    RatingBar ratingBar ;
    String extrasMovieTitle ,extrasMoviePoster,extrasMovieReleaseDate,extrasMovieOverview,extrasMovieBackDrop;
    ImageView posterImage , backDropImage ,seeAllCasts;
    int extrasMovieId;
    float extrasMovieRating;
    ProgressBar backdropProgressBar ;
    RecyclerView castRecyclerView , trailerRecyclerView, castListRecyclerView ;
    MovieCastsAdapter movieCastsAdapter;
    private ArrayList<Cast> castarrayList ;
    MovieRepository movieRepository;
    String movieId ;
    MovieTrailerAdapter movieTrailerAdapter ;
    private ArrayList<Trailer> trailers;
    BottomSheetBehavior bottomSheetBehavior;
    NestedScrollView bottomSheet;
    MovieCastListAdapter movieCastListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initializeViews();

        Bundle extras = getIntent().getExtras();

        if (extras==null){
            return;
        }else {
            extrasMovieId = extras.getInt("movieid");
             extrasMovieTitle = extras.getString("movietitle");
//             extrasMoviePoster = extras.getString("movieposter");
             extrasMovieRating = extras.getFloat("movierating");
             extrasMovieReleaseDate = extras.getString("moviereleasedate");
             extrasMovieOverview = extras.getString("movieoverview");
             extrasMovieBackDrop = extras.getString("moviebackdrop");

              movieId = String.valueOf(extrasMovieId);
        }
        castarrayList = new ArrayList<>();
        trailers = new ArrayList<>();


        movieCastsAdapter = new MovieCastsAdapter(castarrayList,this);
        movieTrailerAdapter = new MovieTrailerAdapter(trailers, this);
        movieCastListAdapter = new MovieCastListAdapter(castarrayList,this);

        movieRepository = MovieRepository.getInstance(this);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        castRecyclerView.setAdapter(movieCastsAdapter);

        castListRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        castListRecyclerView.setAdapter(movieCastListAdapter);

        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        trailerRecyclerView.setAdapter(movieTrailerAdapter);

        movieTrailerAdapter.setTrailerClickListener(new MovieTrailerAdapter.onTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(trailer.getSource()));

                Log.i("TRAILER", trailer.getSource());

                MovieDetailActivity.this.startActivity(intent);
            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(10);
        bottomSheetBehavior.setHideable(true);

        seeAllCasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });


        movieCastsAdapter.setCastClickListener(new MovieCastsAdapter.onCastClickListener() {
            @Override
            public void onCastClick(Cast cast) {
                ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
                modalBottomSheet.show(getSupportFragmentManager(),"modalbottomsheet");

                String castName = cast.getName();
                String castCharacter = cast.getCharacter();
                String castProfile = cast.getProfile_path();

//                modalBottomSheet.setCastDetails(castName,castCharacter,castProfile);

                Bundle bundle = new Bundle();
                bundle.putString("name",castName);
                bundle.putString("character",castCharacter);
                bundle.putString("profile",castProfile);

                modalBottomSheet.setArguments(bundle);
            }
        });

        loadCast();
        loadTrailers();

        Picasso.with(MovieDetailActivity.this)
                .load(extrasMovieBackDrop)
                .into(backDropImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        backdropProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        backdropProgressBar.setVisibility(View.VISIBLE);
                    }
                });


        movieTitle.setText(extrasMovieTitle);
//        movieGenre.setText(extrasMo);
        movieReleaseDate.setText(extrasMovieReleaseDate);
        ratingBar.setRating(extrasMovieRating);
        movieSummary.setText(extrasMovieOverview);

    }

    private void loadTrailers() {
        movieRepository.getTrailers(new MovieRepository.MovieRepositoryListener<ArrayList<Trailer>>() {
            @Override
            public void onMovieLoading(ArrayList<Trailer> trailers1) {
                trailers = trailers1;
                movieTrailerAdapter.setTrailerList(trailers);
            }

            @Override
            public void onErrorLoading(String any) {

            }
        },movieId);
    }

    private void loadCast() {
        movieRepository.getCasts(new MovieRepository.MovieRepositoryListener<ArrayList<Cast>>() {
            @Override
            public void onMovieLoading(ArrayList<Cast> casts) {
                castarrayList = casts;
                movieCastsAdapter.setCastList(castarrayList);
                movieCastListAdapter.setCastList(castarrayList);
            }

            @Override
            public void onErrorLoading(String any) {

            }
        },movieId);
    }

    private void initializeViews() {
        movieTitle = findViewById(R.id.movieTitle);
//        movieGenre = findViewById(R.id.movieGenre);
        movieReleaseDate = findViewById(R.id.movieReleaseData);
        movieSummary = findViewById(R.id.movieSummary);
        ratingBar = findViewById(R.id.ratingBar);
        posterImage = findViewById(R.id.posterImageView);
        backDropImage = findViewById(R.id.backdropImageView);
        backdropProgressBar = findViewById(R.id.backdropProgressBar);
        castRecyclerView = findViewById(R.id.castsRecyclerView);
        trailerRecyclerView = findViewById(R.id.trailersRecyclerView);
        bottomSheet = findViewById(R.id.bottomSheet);
        castListRecyclerView = findViewById(R.id.castListRecyclerView);
        seeAllCasts = findViewById(R.id.seeAllCasts);
    }

}
