package com.timilehinjegede.vimub.Fragments.Movies;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timilehinjegede.vimub.Activities.MovieDetailActivity;
import com.timilehinjegede.vimub.Adapters.MovieAdapter;
import com.timilehinjegede.vimub.Models.Movie;
import com.timilehinjegede.vimub.R;
import com.timilehinjegede.vimub.Repository.MovieRepository;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedMoviesFragment extends Fragment {

    RecyclerView topRatedRecyclerView ;
    MovieRepository movieRepository;
    ArrayList<Movie> movieArrayList;
    MovieAdapter movieAdapter;

    public TopRatedMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated_movies, container, false);

        topRatedRecyclerView = view.findViewById(R.id.topRatedRecyclerView);
        movieArrayList = new ArrayList<>();

        movieRepository = MovieRepository.getInstance(getContext());


        movieAdapter = new MovieAdapter(movieArrayList,getContext());

        movieAdapter.setMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClicked(Movie movie) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
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

        topRatedRecyclerView.setLayoutManager(
                new GridLayoutManager(
                        getContext(),
                        2
                )
        );

        topRatedRecyclerView.setAdapter(movieAdapter);

        loadTopRatedMovies();

        return view;
    }

    public void loadTopRatedMovies(){
//
        movieRepository.getMoviesBy("Top Rated");
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
