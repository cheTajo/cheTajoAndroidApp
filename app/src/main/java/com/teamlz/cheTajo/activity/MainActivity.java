package com.teamlz.cheTajo.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikepenz.iconics.IconicsDrawable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabClickListener;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.fragment.HomeFragment;
import com.teamlz.cheTajo.fragment.UserProfileFragment;
import com.teamlz.cheTajo.object.User;

/*
 * Created by francesco on 02/05/16.
 */

public class MainActivity extends AppCompatActivity {
    public static User myUser;

    private Fragment homeFragment, userProfileFragment;
    private BottomBar mBottomBar;

    private Firebase myFirebase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the user id from the intent
        String id = getIntent().getStringExtra("id");

        myFirebase = new Firebase(getResources().getString(R.string.firebase_url));

        Firebase userFirebase = myFirebase.child("users").child(id);
        userFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String firstName = snapshot.child("firstName").getValue().toString();
                String lastName = snapshot.child("lastName").getValue().toString();
                String email = snapshot.child("email").getValue().toString();

                myUser = new User(email, firstName, lastName);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        userProfileFragment = UserProfileFragment.newInstance();

        homeFragment = HomeFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_frame, homeFragment).commit();

        View coordinator = findViewById(R.id.activity_main_coordinator);
        assert (coordinator != null);
        mBottomBar = BottomBar.attach(coordinator, savedInstanceState);
        mBottomBar.noTopOffset();
        mBottomBar.useFixedMode();
        mBottomBar.setActiveTabColor(ContextCompat.getColor(this, R.color.colorPrimaryExtraDark));
        mBottomBar.setActiveTabColor(getResources().getColor(R.color.colorRed));
        mBottomBar.setItems(
                new BottomBarTab(new IconicsDrawable(this, "gmd-home").sizeDp(24), "Home"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-history").sizeDp(24), "Recenti"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-favorite").sizeDp(24), "Preferiti"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-person").sizeDp(24), "Profilo")
        );

        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {

                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.activity_main_frame, homeFragment).commit();
                        break;

                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.activity_main_frame, userProfileFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabReSelected(int position) {}
        });
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
    public void onBackPressed() {
        if (mBottomBar.getCurrentTabPosition() != 0) mBottomBar.selectTabAtPosition(0, true);
        else super.onBackPressed();
    }
}