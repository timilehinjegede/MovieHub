package com.timilehinjegede.vimub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timilehinjegede.vimub.Models.Cast;
import com.timilehinjegede.vimub.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieCastListAdapter extends RecyclerView.Adapter<MovieCastListAdapter.CastListViewHolder> {

    private List<Cast> castList;
    private Context context ;

    public MovieCastListAdapter(List<Cast> castList1, Context context) {
        this.castList = castList1;
        this.context = context;
    }

    @NonNull
    @Override
    public CastListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cast_list_item,
                parent,
                false
        );

        return new CastListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastListViewHolder holder, int position) {

        Cast cast = castList.get(position);

        holder.name.setText(cast.getName());
        holder.character.setText(cast.getCharacter());

        Picasso.with(context)
                .load(cast.getProfile_path())
                .into(holder.circleImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.castListProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        holder.castListProgressBar.setVisibility(View.VISIBLE);
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


    class CastListViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView ;
        TextView name , character ;
        ProgressBar castListProgressBar;

        public CastListViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.castListImageView);
            name = itemView.findViewById(R.id.castListCastName);
            character = itemView.findViewById(R.id.castListCastCharacter);
            castListProgressBar = itemView.findViewById(R.id.castItemProgressBar);
        }
    }
}
