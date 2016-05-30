package com.teamlz.cheTajo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mikepenz.iconics.IconicsDrawable;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabClickListener;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.fragment.HomeFragment;
import com.teamlz.cheTajo.fragment.UserProfileFragment;

/*
 * Created by francesco on 02/05/16.
 */
@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    private BottomBar mBottomBar;

    private Fragment homeFragment, userProfileFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userProfileFragment = UserProfileFragment.newInstance();
        homeFragment = HomeFragment.newInstance();

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.activity_main_frame, homeFragment);
        trans.add(R.id.activity_main_frame, userProfileFragment);
        trans.hide(userProfileFragment);
        trans.commit();

        View coordinator = findViewById(R.id.activity_main_coordinator);
        assert (coordinator != null);

        mBottomBar = BottomBar.attach(coordinator, savedInstanceState);
        mBottomBar.noTopOffset();
        mBottomBar.useFixedMode();
        mBottomBar.setActiveTabColor(getResources().getColor(R.color.colorRed));

        mBottomBar.setItems(
                new BottomBarTab(new IconicsDrawable(this, "gmd-home").sizeDp(24), "Home"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-favorite").sizeDp(24), "Preferiti"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-history").sizeDp(24), "Recenti"),
                new BottomBarTab(new IconicsDrawable(this, "gmd-person").sizeDp(24), "Profilo")
        );

        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                switch (position){
                    case 3:
                        trans.hide(homeFragment);
                        trans.show(userProfileFragment);
                        trans.commit();
                        break;
                    default:
                        trans.hide(userProfileFragment);
                        trans.show(homeFragment);
                        trans.commit();
                        break;
                }
            }

            @Override
            public void onTabReSelected(int position) {}
        });
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