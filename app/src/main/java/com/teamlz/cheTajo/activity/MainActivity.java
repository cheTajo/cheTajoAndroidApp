package com.teamlz.cheTajo.activity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabClickListener;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.adapter.SampleFragmentPagerAdapter;

/*
 * Created by francesco on 02/05/16.
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    public static FloatingActionButton fab_add, fab_location;
    public static BottomBar mBottomBar;
    private boolean startBar = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        assert viewPager != null;
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);

        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_location = (FloatingActionButton) findViewById(R.id.fab_location);
        fab_location.setImageDrawable(new IconicsDrawable(this, "gmd-gps_fixed")
                .sizeDp(24)
                .color(getResources().getColor(R.color.white)));
        fab_location.hide();

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBottomBar = BottomBar.attach(findViewById(R.id.main_coordinator), savedInstanceState);
        mBottomBar.noTopOffset();
        mBottomBar.useFixedMode();
        mBottomBar.setActiveTabColor(ContextCompat.getColor(this, R.color.colorPrimaryExtraDark));
        mBottomBar.setActiveTabColor(getResources().getColor(R.color.red));
        mBottomBar.setItems(
                new BottomBarTab(new IconicsDrawable(this, "gmd-person").sizeDp(24), "Profilo"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-history").sizeDp(24), "Recenti"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-favorite").sizeDp(24), "Preferiti"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-room").sizeDp(24), "Nearby")
        );
        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                if (startBar)
                    SameActionTab(position);
                else startBar = true;
            }

            @Override
            public void onTabReSelected(int position) {
                SameActionTab(position);
            }
        });

    }

    public void SameActionTab(int position){
        switch (position){
            case 0:
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_settings:
                break;

            case R.id.action_search:
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBar = false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switchFab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void switchFab(int position){

        switch (position){

            case 0:
                fab_location.hide();
                fab_add.show();
                break;

            case 1:
                fab_add.hide();
                fab_location.setVisibility(View.VISIBLE);
                fab_location.show();
                break;
        }
    }
}

