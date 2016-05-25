package com.teamlz.cheTajo.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by francesco on 05/05/16.
 */
@SuppressWarnings("unused")
public class User implements Serializable{
    private List<String> followed;
    private List<String> likes;

    public User(){}

    public User(List<String> f) {
        followed = f;
    }

    public List<String> getFollowed() {
        if (followed == null) followed = new ArrayList<>();
        return followed;
    }

    public void addFollowed (String id) {
        if (followed == null) followed = new ArrayList<>();
        followed.add(id);
    }

    public void removeFollowed (String id) {
        followed.remove(id);
    }

    public List<String> getLikes() {
        return likes;
    }

    public void addLike (String id) {
        if (likes == null) likes = new ArrayList<>();
        likes.add(id);
    }

    public void removeLike (String id) {
        if (likes == null) likes = new ArrayList<>();
        likes.remove(id);
    }
}
