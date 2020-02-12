package com.timilehinjegede.vimub.Fragments.Movies;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.timilehinjegede.vimub.Activities.MovieDetailActivity;
import com.timilehinjegede.vimub.Adapters.MovieAdapter;
import com.timilehinjegede.vimub.Models.Movie;
import com.timilehinjegede.vimub.R;
import com.timilehinjegede.vimub.Repository.MovieRepository;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMoviesFragment extends Fragment{

    RecyclerView recyclerView ;
    private ArrayList<Movie> movieArrayList ;
    public MovieRepository movieRepository ;
    MovieAdapter movieAdapter ;
    ProgressBar progressBar ;
    GridLayoutManager gridLayoutManager;

    public static final int PAGE_START = 1 ;
    public boolean isLoading = false ;
    private boolean isLastPage = false ;
    private int TOTAL_PAGES = 500 ;
    private int currentPage = PAGE_START ;

    public PopularMoviesFragment() {
         // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        recyclerView = view.findViewById(R.id.popularRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        movieArrayList = new ArrayList<>();

        movieAdapter = new MovieAdapter(movieArrayList,getContext());

        movieAdapter.setMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClicked(Movie movie) {

            Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
            intent.putExtra("movieid",movie.getId());
            intent.putExtra("movietitle",movie.getTitle());
            intent.putExtra("movieposter",movie.getPoster());
            intent.putExtra("movierating",movie.getRating());
            intent.putExtra("moviereleasedate",movie.getRelease_date());
            intent.putExtra("movieoverview",movie.getOverview());
            intent.putExtra("moviebackdrop",movie.getBackdrop());
            startActivity(intent);
            }
        });



        movieRepository = MovieRepository.getInstance(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(movieAdapter);

        loadPopularMovies();

        return view;
    }

    private void loadPopularMovies() {
//        movieRepository.getMoviesBy(new MovieRepository.MovieRepositoryListener<ArrayList<Trailer>>() {
//            @Override
//            public void onMovieLoading(ArrayList<Trailer> movies) {
//                movieArrayList = movies;
//                movieAdapter.setMovieList(movieArrayList);
//            }
//
//            @Override
//            public void onErrorLoading(String movies) {
//
//            }
//        },"Popular");

        movieRepository.getMoviesBy("Popular");
        movieRepository.apiRequest(new MovieRepository.MovieRepositoryListener<ArrayList<Movie>>() {
            @Override
            public void onMovieLoading(ArrayList<Movie> movieArrayList1) {
                movieArrayList = movieArrayList1;
                movieAdapter.setMovieList(movieArrayList);
            }

            @Override
            public void onErrorLoading(String any) {

            }
        });

    }
}
