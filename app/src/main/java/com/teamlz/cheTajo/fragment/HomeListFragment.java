package com.teamlz.cheTajo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.activity.MainActivity;
import com.teamlz.cheTajo.adapter.HairDresserAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by francesco on 02/05/16.
 */
public class HomeListFragment extends Fragment {

    RecyclerView mRecyclerView;
    HairDresserAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    public HomeListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_homelist, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> examples = new ArrayList<>();
        examples.add("Francesco Starna");
        examples.add("Lorenzo Sciarra");
        examples.add("Angelo Marvulli");
        examples.add("Giulia Rinaldi");
        examples.add("Dottor Cooper");
        examples.add("Mia Nonna");
        examples.add("Il pezzo");
        examples.add("La Madonna Sciarra");
        examples.add("Angelo Trentulli");
        examples.add("giuggy94");
        examples.add("Dottor Sheldon");
        examples.add("Mia cugina");

        mAdapter = new HairDresserAdapter(examples);

        mRecyclerView.setAdapter(new SlideInLeftAnimationAdapter(mAdapter));

        MainActivity.fab_add.attachToRecyclerView(mRecyclerView);
        return view;
    }

    public static Fragment newIstance(){
        return new HomeListFragment();
    }
}
