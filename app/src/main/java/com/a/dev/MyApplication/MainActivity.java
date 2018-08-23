package com.a.dev.MyApplication;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MenuItem action_search;
    private FrameLayout fragment_container;
    private MaterialSearchView searchView;
    private FloatingActionButton fabExpand;
    private FloatingActionButton fabCollaps;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        fragment_container = findViewById(R.id.fragment_container);
        searchView = findViewById(R.id.searchView);

        fabExpand = findViewById(R.id.fabExpand);
        fabCollaps = findViewById(R.id.fabCollaps);
        fabAdd = findViewById(R.id.fabAdd);
        fabRefresh = findViewById(R.id.fabRefresh);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchToListMarkerFragmen();

        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                searchView.setVisibility(View.GONE);
            }
        });
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        fabExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAdd.setVisibility(View.VISIBLE);
                fabRefresh.setVisibility(View.VISIBLE);
                fabExpand.animate().rotationBy(180);
                fabAdd.animate().translationY(-110);
                fabRefresh.animate().translationY(-220);
                Log.d("Fab", "close");
                fabExpand.setVisibility(View.GONE);
                fabCollaps.setVisibility(View.VISIBLE);
            }
        });

        fabCollaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabExpand.animate().rotationBy(-180);
                fabAdd.animate().translationY(0);
                fabRefresh.animate().translationY(0);

                Log.d("Fab", "open");
                fabCollaps.setVisibility(View.GONE);
                fabExpand.setVisibility(View.VISIBLE);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fab", "Fab 1");
            }
        });
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fab", "Fab 2");
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_MapLocation:
                    switchToListMarkerFragmen();
                    fabExpand.setVisibility(View.VISIBLE);
                    action_search.setVisible(true);
                    return true;
                case R.id.tab_ListLocation:
                    switchToListDataFragment();
                    fabExpand.setVisibility(View.VISIBLE);
                    action_search.setVisible(true);
                    return true;
                case R.id.tab_Info:
                    switchToInfoFragmen();
                    fabAdd.setAnimation(null);
                    fabRefresh.setAnimation(null);
                    fabExpand.setVisibility(View.GONE);
                    fabCollaps.setVisibility(View.GONE);
                    fabAdd.setVisibility(View.INVISIBLE);
                    fabRefresh.setVisibility(View.INVISIBLE);

                    action_search.setVisible(false);
                    if (searchView.isSearchOpen()) {
                        searchView.closeSearch();
                    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        action_search = menu.findItem(R.id.action_search);
        searchView.setMenuItem(action_search);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

}
