package com.teamlz.cheTajo.object;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.teamlz.cheTajo.R;

/*
 * Created by francesco on 05/05/16.
 */
public class Utils {

    public static void addHairDresser(Context context){
        Firebase myFirebase = new Firebase(context.getResources().getString(R.string.firebase_url));
        Firebase dresser = myFirebase.child("HairDressers");

        //add casual dressers
        dresser.child("Francesco Starna").setValue(new HairDresser("francesco@coglione.it","Francesco", "Starna", "Fs"));
        dresser.child("Giulia Rinaldi").setValue(new HairDresser("giulia@cogliona.it","Giulia", "Rinaldi", "GR"));
        dresser.child("Luciana Silo").setValue(new HairDresser("luciana@cogliona.it","Luciana", "Silo", "LS"));
        dresser.child("Angelo Marvulli").setValue(new HairDresser("angelo@coglione.it","Angelo", "Marvulli", "AM"));
        dresser.child("Lorenzo Sciarra").setValue(new HairDresser("lorenzo@coglione.it","Lorenzo", "Sciarra", "LS"));
        dresser.child("Anna Del Corso").setValue(new HairDresser("anna@cogliona.it","Anna", "Del Corso", "ADC"));
        dresser.child("Mannaggia Starnino").setValue(new HairDresser("mannaggia@coglione.it","Mannaggia", "Starnino", "MS"));
        dresser.child("Daniele Nardi").setValue(new HairDresser("daniele@coglione.it","Daniele", "Nardi", "DN"));
    }

    public static void retrieveHairDressers(Context context){
        Firebase myFirebase = new Firebase(context.getResources().getString(R.string.firebase_url));
        Firebase dresser = myFirebase.child("HairDressers");

        dresser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    HairDresser hairDresser = snapshot.getValue(HairDresser.class);
                    System.out.println(hairDresser.getFirstName() + " " + hairDresser.getLikes().toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

}
