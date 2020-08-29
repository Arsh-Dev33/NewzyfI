package com.project.newzyfi.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.newzyfi.R;
import com.project.newzyfi.fragment.InfoFragment;
import com.project.newzyfi.fragment.SavedFragment;
import com.project.newzyfi.fragment.SourceFragment;
import com.project.newzyfi.fragment.TrendingFragment;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        callFragment(new TrendingFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.nav_trending:
                fragment = new TrendingFragment();
                break;

            case R.id.nav_source:
                fragment = new SourceFragment();
                break;

            case R.id.nav_info:
                fragment = new InfoFragment();
                break;

            case R.id.nav_saved:
                fragment = new SavedFragment();
                break;


        }
        return callFragment(fragment);
    }

    public boolean callFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout,fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
