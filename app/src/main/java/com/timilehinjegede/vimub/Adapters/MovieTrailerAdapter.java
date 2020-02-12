package com.timilehinjegede.vimub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.timilehinjegede.vimub.Models.Trailer;
import com.timilehinjegede.vimub.R;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailerList;
    private Context context;
    private onTrailerClickListener trailerClickListener;

    public MovieTrailerAdapter(List<Trailer> trailerList, Context context) {
        this.trailerList = trailerList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_movie,
                parent,
                false
        );
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        Trailer trailer = trailerList.get(position);

        Picasso.with(context)
                .load(trailer.getThumbnail())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void setTrailerList(List<Trailer> trailerList){
        this.trailerList = trailerList ;
        notifyDataSetChanged();
    }

    public void setTrailerClickListener(onTrailerClickListener trailerClickListener){
        this.trailerClickListener = trailerClickListener;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView ;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.trailerImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    trailerClickListener.onTrailerClick(trailerList.get(position));
                }
            });
        }
    }

    public interface onTrailerClickListener{
        void onTrailerClick(Trailer trailer);
    }
}
