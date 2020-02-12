package com.timilehinjegede.vimub.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.timilehinjegede.vimub.Fragments.Movies.PopularMoviesFragment;
import com.timilehinjegede.vimub.Fragments.Movies.TopRatedMoviesFragment;
import com.timilehinjegede.vimub.Fragments.Movies.UpcomingMoviesFragment;

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {

    private Context context ;
    private String tabTitle[] = new String[]{"Popular","Top Rated","Upcoming"};

    public MoviesPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       if(position == 0){
           return new PopularMoviesFragment();
       }
       else if(position == 1){
           return new TopRatedMoviesFragment();
       }
       else if(position ==2){
           return new UpcomingMoviesFragment();
       }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }


}
