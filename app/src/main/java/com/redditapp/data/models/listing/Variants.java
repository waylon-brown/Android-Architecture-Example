package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Variants implements RealmModel {

    private Gif gif;

    public Variants() {}

    public Gif getGif() {
        return gif;
    }

    public void setGif(Gif gif) {
        this.gif = gif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variants variants = (Variants) o;

        return gif != null ? gif.equals(variants.gif) : variants.gif == null;

    }

    @Override
    public int hashCode() {
        return gif != null ? gif.hashCode() : 0;
    }
}
