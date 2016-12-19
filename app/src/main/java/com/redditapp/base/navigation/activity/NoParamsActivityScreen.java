package com.redditapp.base.navigation.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class NoParamsActivityScreen extends ActivityScreen {
    @Override
    protected final void configureIntent(@NonNull Intent intent) {
        // empty implementation
    }
}