package com.teamlz.cheTajo.object;

import android.content.Context;

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

        HairDresser myHd = new HairDresser("52f0091c-74bb-4552-a4aa-46a582728c20", "Devil Style");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myFirebase.child("hairDressers").child("52f0091c-74bb-4552-a4aa-46a582728c20").setValue(myHd);

        myHd = new HairDresser("79b451ed-fb1c-4282-8178-1d7582274a8f", "Scialla Shampoo");
        myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myFirebase.child("hairDressers").child("79b451ed-fb1c-4282-8178-1d7582274a8f").setValue(myHd);

        myHd = new HairDresser("7b2a5b26-690b-46c6-88d6-470bb74f342e", "Piccina Fashonist");
        myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myFirebase.child("hairDressers").child("7b2a5b26-690b-46c6-88d6-470bb74f342e").setValue(myHd);

        myHd = new HairDresser("Prova01", "Figaro Qua");
        myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myFirebase.child("hairDressers").child("Prova01").setValue(myHd);

        myHd = new HairDresser("Prova02", "Figaro Là");
        myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myFirebase.child("hairDressers").child("Prova02").setValue(myHd);

        myHd = new HairDresser("Prova03", "Figaro Su");
        myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myFirebase.child("hairDressers").child("Prova03").setValue(myHd);

        myHd = new HairDresser("Prova04", "Figaro Giù");
        myHd.addFollower("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addFollower("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addFollower("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myHd.addLike("52f0091c-74bb-4552-a4aa-46a582728c20");
        myHd.addLike("79b451ed-fb1c-4282-8178-1d7582274a8f");
        myHd.addLike("7b2a5b26-690b-46c6-88d6-470bb74f342e");
        myFirebase.child("hairDressers").child("Prova04").setValue(myHd);

        myHd = new HairDresser("Prova05", "Sfigato Di Merda");
        myFirebase.child("hairDressers").child("Prova05").setValue(myHd);
    }

    public static void retrieveHairDressers(Context context){
        Firebase myFirebase = new Firebase(context.getResources().getString(R.string.firebase_url));
        Firebase dresser = myFirebase.child("HairDressers");

        dresser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    HairDresser hairDresser = snapshot.getValue(HairDresser.class);
                    System.out.println(hairDresser.getShopName() + " " + hairDresser.getLikes().toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

}
