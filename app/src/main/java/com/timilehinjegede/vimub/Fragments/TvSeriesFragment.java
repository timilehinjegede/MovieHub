package com.timilehinjegede.vimub.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.timilehinjegede.vimub.Adapters.SeriesPagerAdapter;
import com.timilehinjegede.vimub.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvSeriesFragment extends Fragment {

    TabLayout mTabLayout ;
    ViewPager mViewPager ;

    public TvSeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_series, container, false);
//
        mTabLayout = view.findViewById(R.id.mtabLayout);
        mViewPager = view.findViewById(R.id.mviewPager);

        SeriesPagerAdapter seriesPagerAdapter = new SeriesPagerAdapter(
                getActivity().getSupportFragmentManager(),
                getContext()
        );

        mViewPager.setAdapter(seriesPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

}
