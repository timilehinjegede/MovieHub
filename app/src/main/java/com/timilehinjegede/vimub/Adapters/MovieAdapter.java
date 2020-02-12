package com.timilehinjegede.vimub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timilehinjegede.vimub.Models.Movie;

import com.timilehinjegede.vimub.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public static final int ITEM = 0 ;
    public static final int LOADING = 1 ;

    private boolean isLoadingAdded = false ;

    private List<Movie> movieList ;
    private Context context ;
    private OnMovieClickListener movieClickListener ;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_movie,
                parent,
                false
        );

         return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        Picasso.with(context)
                .load(movie.getPoster())
//                .error(R.drawable.icon_error)
                .into(holder.posterImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }
                });

        //use glide instead of picasso
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public void setMovieClickListener(OnMovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImageView ;
        ProgressBar progressBar;
        TextView titleTextView , releaseDateTextView ;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.posterImageView);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    movieClickListener.onMovieClicked(movieList.get(position));
                }
            });
        }
    }

    public interface OnMovieClickListener{
        void onMovieClicked(Movie movie);
    }
}
