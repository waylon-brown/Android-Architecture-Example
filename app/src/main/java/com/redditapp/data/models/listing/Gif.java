package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Gif implements RealmModel {

    private Source source;

    public Gif() {}

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gif gif = (Gif) o;

        return source != null ? source.equals(gif.source) : gif.source == null;

    }

    @Override
    public int hashCode() {
        return source != null ? source.hashCode() : 0;
    }
}
