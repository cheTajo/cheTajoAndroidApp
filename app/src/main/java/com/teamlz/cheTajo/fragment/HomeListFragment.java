package com.teamlz.cheTajo.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.teamlz.cheTajo.object.Utils;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/*
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_homelist, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final List<HairDresser> dressersList = new ArrayList<>();

        mAdapter = new HairDresserAdapter(dressersList);

        Firebase dressersBase = new Firebase(getString(R.string.firebase_url));
        dressersBase = dressersBase.child(Utils.DRESSERS);

        dressersBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    HairDresser hd = snapshot.getValue(HairDresser.class);
                    dressersList.add(hd);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println(firebaseError);
            }
        });

        mAdapter.setHasStableIds(true);

        mRecyclerView.setAdapter(new SlideInLeftAnimationAdapter(mAdapter));

        HomeFragment.fab_add.attachToRecyclerView(mRecyclerView);
        return view;
    }

    public static Fragment newIstance(){
        return new HomeListFragment();
    }

}

