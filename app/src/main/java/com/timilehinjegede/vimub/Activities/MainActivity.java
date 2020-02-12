package com.timilehinjegede.vimub.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.timilehinjegede.vimub.Fragments.MoviesFragment;
import com.timilehinjegede.vimub.Fragments.TvSeriesFragment;
import com.timilehinjegede.vimub.R;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout ;
    BottomNavigationView bottomNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new MoviesFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.movies:
                        menuItem.setIcon(R.drawable.icon_movie);
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new MoviesFragment()).commit();
                        break;
                    case R.id.series:
                        menuItem.setIcon(R.drawable.icon_series);
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new TvSeriesFragment()).commit();
                        break;
                }
                return false;
            }
        });
    }
}
