package com.teamlz.cheTajo.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.LogInOrSignUpActivity;

@SuppressWarnings("deprecation")
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public static FloatingActionButton fab_location;
    private AppBarLayout appBar;
    private Fragment homeListFragment, mapsFragment;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeListFragment = HomeListFragment.newInstance();
        mapsFragment = MapsFragment.newInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        appBar = (AppBarLayout) view.findViewById(R.id.home_app_bar);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_home_pager);

        assert viewPager != null;
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return homeListFragment;

                    default:
                        return mapsFragment;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Bacheca";

                    default:
                        return "Mappa";
                }
            }
        });

        // Give TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.fragment_home_tab);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);

        fab_location = (FloatingActionButton) view.findViewById(R.id.fab_location);
        fab_location.setImageDrawable(new IconicsDrawable(this.getActivity(), "gmd-gps_fixed")
                .sizeDp(24)
                .color(getResources().getColor(R.color.white)));
        fab_location.hide();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
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

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_log_out:
                Intent i = new Intent(getActivity(), LogInOrSignUpActivity.class);
                getActivity().finish();
                startActivity(i);
                break;

            case R.id.action_search:
                break;
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                fab_location.hide();
                break;

            case 1:
                if (!fab_location.isVisible()) fab_location.setVisibility(View.VISIBLE);
                fab_location.show();
                appBar.setExpanded(true, true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}