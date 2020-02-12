package com.timilehinjegede.vimub.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.timilehinjegede.vimub.Fragments.Movies.PopularMoviesFragment;
import com.timilehinjegede.vimub.Fragments.TVSeries.PopularTVFragment;
import com.timilehinjegede.vimub.Fragments.TVSeries.TopRatedTVFragment;

public class SeriesPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String tabitem[] = new String[]{"Popular","Top Rated"};

    public SeriesPagerAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context = context ;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PopularTVFragment();

            case 1:
                return new TopRatedTVFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabitem[position];
    }
}
