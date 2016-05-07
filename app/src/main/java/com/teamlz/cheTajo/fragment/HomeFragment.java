package com.teamlz.cheTajo.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.adapter.SampleFragmentPagerAdapter;

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public static FloatingActionButton fab_add, fab_location;
    private Toolbar toolbar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.activity_main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_home_pager);
        assert viewPager != null;
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager()));

        // Give TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.fragment_home_tab);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(this);

        fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);
        fab_location = (FloatingActionButton) view.findViewById(R.id.fab_location);
        fab_location.setImageDrawable(new IconicsDrawable(this.getActivity(), "gmd-gps_fixed")
                .sizeDp(24)
                .color(getResources().getColor(R.color.white)));
        fab_location.hide();

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;

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