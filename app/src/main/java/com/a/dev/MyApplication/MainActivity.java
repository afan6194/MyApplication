package com.a.dev.MyApplication;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {

    private MenuItem action_search;
    private FrameLayout fragment_container;
    private MaterialSearchView searchView;
    //private FabSpeedDial fabSpeedDial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        fragment_container = findViewById(R.id.fragment_container);
        searchView = findViewById(R.id.searchView);
        //fabSpeedDial = findViewById(R.id.fabCollapse);

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

        /*fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add:
                        Intent toAddLocation = new Intent(MainActivity.this, AddMapsActivity.class);

                        startActivity(toAddLocation);
                        Toast.makeText(MainActivity.this, "Add Clicked", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_current:
                        Toast.makeText(MainActivity.this, "Current Clicked", Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });*/
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_MapLocation:
                    switchToListMarkerFragmen();
                    action_search.setVisible(true);
                    //fabSpeedDial.setVisibility(View.VISIBLE);
                    return true;
                case R.id.tab_ListLocation:
                    switchToListDataFragment();
                    action_search.setVisible(true);
                    //fabSpeedDial.setVisibility(View.VISIBLE);
                    return true;
                case R.id.tab_Info:
                    switchToInfoFragmen();
                    //fabSpeedDial.setVisibility(View.GONE);
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
