package com.teamlz.cheTajo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.adapter.HairDresserAdapter;
import com.teamlz.cheTajo.object.HairDresser;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/*
 * Created by francesco on 02/05/16.
 */
public class HomeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HairDresserAdapter mAdapter;
    private List<HairDresser> hairDresserList;

    public HomeListFragment(){}

    public static HomeListFragment newInstance(){
        return new HomeListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hairDresserList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_homelist, container, false);

        //Utils.retrieveHairDressers(getActivity());
        //Utils.addHairDresser(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new HairDresserAdapter(hairDresserList);

        Firebase dressersBase = new Firebase(getString(R.string.firebase_url));
        dressersBase = dressersBase.child("HairDressers");

        dressersBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    HairDresser hd = snapshot.getValue(HairDresser.class);
                    hairDresserList.add(hd);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mAdapter.setHasStableIds(true);

        mRecyclerView.setAdapter(new SlideInLeftAnimationAdapter(mAdapter));

        HomeFragment.fab_add.attachToRecyclerView(mRecyclerView);
        return view;
    }
}