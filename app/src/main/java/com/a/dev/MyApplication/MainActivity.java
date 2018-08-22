package com.a.dev.MyApplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragment_container;
    private FloatingActionButton fabExpand;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabRefresh;

    private Boolean isFabExpand = false;
    private Animation fab_expand;
    private Animation fab_collaps;
    private Animation rotate_forward;
    private Animation rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        fragment_container = findViewById(R.id.fragment_container);
        fabExpand = findViewById(R.id.fabExpand);
        fabAdd = findViewById(R.id.fabAdd);
        fabRefresh = findViewById(R.id.fabRefresh);

        fab_expand = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_expand);
        fab_collaps = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_collaps);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchToListMarkerFragmen();

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.fabExpand:
                            animateFAB();
                        break;
                    case R.id.fabAdd:

                        Log.d("Fab", "Fab 1");
                        break;
                    case R.id.fabRefresh:

                        Log.d("Fab", "Fab 2");
                        break;
                
                }
            }
        };

        fabExpand.setOnClickListener(onClick);
        fabAdd.setOnClickListener(onClick);
        fabRefresh.setOnClickListener(onClick);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_MapLocation:
                    switchToListMarkerFragmen();
                    fabExpand.setVisibility(View.VISIBLE);
                    return true;
                case R.id.tab_ListLocation:
                    switchToListDataFragment();
                    fabExpand.setVisibility(View.VISIBLE);
                    return true;
                case R.id.tab_Info:
                    switchToInfoFragmen();
                    fabExpand.setVisibility(View.GONE);
                    fabAdd.setVisibility(View.GONE);
                    fabRefresh.setVisibility(View.GONE);
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

    public void animateFAB() {
        if (isFabExpand) {

            fabExpand.startAnimation(rotate_backward);
            fabAdd.startAnimation(fab_collaps);
            fabRefresh.startAnimation(fab_collaps);
            fabAdd.setClickable(false);
            fabRefresh.setClickable(false);
            isFabExpand = false;
            Log.d("Fab", "close");

        } else {

            fabExpand.startAnimation(rotate_forward);
            fabAdd.startAnimation(fab_expand);
            fabRefresh.startAnimation(fab_expand);
            fabAdd.setClickable(true);
            fabRefresh.setClickable(true);
            isFabExpand = true;
            Log.d("Fab", "open");

        }
    }
}
