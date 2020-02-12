package com.timilehinjegede.vimub.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timilehinjegede.vimub.R;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    TextView castName, castCharacter;
    ImageView close, castImageView;
    ProgressBar castDetailProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cast_detail, container, false);

        close = view.findViewById(R.id.close);
        castImageView = view.findViewById(R.id.castImageView);
        castName = view.findViewById(R.id.castName);
        castCharacter = view.findViewById(R.id.castCharacter);
        castDetailProgressBar = view.findViewById(R.id.castDetailProgressBar);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        String character = bundle.getString("character");
        String profile = bundle.getString("profile");

        castName.setText("Cast Name: " + name);
        castCharacter.setText("Character: " + character);


        Picasso.with(getContext())
                .load(profile)
                .into(castImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        castDetailProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        castDetailProgressBar.setVisibility(View.VISIBLE);
                    }
                });

        return view;
    }

//    public void setCastDetails(String name, String character, String profilePath) {
//        castName.setText(name);
//        castCharacter.setText(character);
//
//        Picasso.with(getContext())
//                .load(profilePath)
//                .into(castImageView);
//    }
}
