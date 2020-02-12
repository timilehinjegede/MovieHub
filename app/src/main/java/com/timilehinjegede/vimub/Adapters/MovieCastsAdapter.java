package com.timilehinjegede.vimub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timilehinjegede.vimub.Models.Cast;
import com.timilehinjegede.vimub.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieCastsAdapter extends RecyclerView.Adapter<MovieCastsAdapter.CastViewHolder> {

    private List<Cast> castList;
    private Context context ;
    private onCastClickListener castClickListener;

    public MovieCastsAdapter(List<Cast> castList1, Context context) {
        this.castList = castList1;
        this.context = context;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.casts_movie,
                parent,
                false
        );

        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastViewHolder holder, int position) {

        Cast cast = castList.get(position);

        Picasso.with(context)
                .load(cast.getProfile_path())
                .into(holder.circleImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public void setCastList(List<Cast> castList){
        this.castList =castList;
        notifyDataSetChanged();
    }

    public void setCastClickListener(onCastClickListener castClickListener){
        this.castClickListener = castClickListener;
    }

    class CastViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView ;
        ProgressBar progressBar;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.castImageView);
            progressBar = itemView.findViewById(R.id.castProgressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    castClickListener.onCastClick(castList.get(position));
                }
            });
        }
    }

    public interface onCastClickListener{
        void onCastClick(Cast cast);
    }

}
