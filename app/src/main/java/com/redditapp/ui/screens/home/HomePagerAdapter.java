package com.redditapp.ui.screens.home;

import com.redditapp.ui.screens.home.account.AccountFragment;
import com.redditapp.ui.screens.home.postlist.PostListFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


public class HomePagerAdapter extends FragmentPagerAdapter {
    
    private static final int NUMBER_OF_PAGES = FragmentTypes.values().length;
    
    enum FragmentTypes {
        POST_LIST,
        ACCOUNT,
        MESSAGES,
        SAVED
    }

    public HomePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    
    @Override
    public Fragment getItem(int position) {
        FragmentTypes fragment = FragmentTypes.values()[position];
        switch(fragment) {
            case POST_LIST:
                return new PostListFragment();
            case ACCOUNT:
            case MESSAGES:
            case SAVED:
                return new AccountFragment();
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FragmentManager fragmentManager = ((Fragment) object).getFragmentManager();
        fragmentManager.beginTransaction()
                .remove((Fragment) object)
                .commit();
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }
}
