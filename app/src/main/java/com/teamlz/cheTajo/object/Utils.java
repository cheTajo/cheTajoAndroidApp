package com.teamlz.cheTajo.object;

import android.content.Context;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.teamlz.cheTajo.R;

/**
 * Created by francesco on 05/05/16.
 */
public class Utils {

    public static String DRESSERS = "HairDressers";
    public static String USERS = "Users";
    public static User USERTHIS = new User("example.gmail.com", "example", "starna");

    public static void addHairDresser(Context context){
        Firebase myFirebase = new Firebase(context.getResources().getString(R.string.firebase_url));
        Firebase dresser = myFirebase.child("HairDressers");

        //add casual dressers
        dresser.child("Francesco Starna").setValue(new HairDresser("Fs","Francesco", "Starna"));
        dresser.child("Giulia Rinaldi").setValue(new HairDresser("GR","Giulia", "Rinaldi"));
        dresser.child("Luciana Silo").setValue(new HairDresser("LS","Luciana", "Silo"));
        dresser.child("Angelo Marvulli").setValue(new HairDresser("AM","Angelo", "Marvulli"));
        dresser.child("Lorenzo Sciarra").setValue(new HairDresser("LS","Lorenzo", "Sciarra"));
        dresser.child("Anna Del Corso").setValue(new HairDresser("ADC","Anna", "Del Corso"));
        dresser.child("Mannaggia Starnino").setValue(new HairDresser("MS","Mannaggia", "Starnino"));
        dresser.child("Daniele Nardi").setValue(new HairDresser("DN","Daniele", "Nardi"));
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
