package com.a.dev.fixingthemotobikes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout fragment_container;
    private FloatingActionButton fabCollaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_container = findViewById(R.id.fragment_container);
        fabCollaps = findViewById(R.id.fabCollaps);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchToListMarkerFragmen();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_MapLocation:
                    switchToListMarkerFragmen();
                    fabCollaps.setVisibility(View.VISIBLE);
                    return true;
                case R.id.tab_ListLocation:
                    switchToListDataFragment();
                    fabCollaps.setVisibility(View.VISIBLE);
                    return true;
                case R.id.tab_Info:
                    switchToInfoFragmen();
                    fabCollaps.setVisibility(View.GONE);
                    return true;
                default:
                    switchToListMarkerFragmen();
                    break;
            }
            return false;
        }
    };
    public void switchToListMarkerFragmen() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new ListMarkerFragment()).commit();
        getSupportActionBar().setTitle(getResources().getString(R.string.tabViewMap));
    }

    public void switchToListDataFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new ListDataFragment()).commit();
        getSupportActionBar().setTitle(getResources().getString(R.string.tabListLocation));
    }

    public void switchToInfoFragmen() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();
        getSupportActionBar().setTitle(getResources().getString(R.string.tabInfo));
    }
}
