package com.redditapp.ui.screens.home.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redditapp.R;
import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.databinding.AccountFragmentBinding;
import com.redditapp.ui.base.BaseFragment;
import com.redditapp.ui.screens.home.DaggerHomeComponent;
import com.redditapp.ui.screens.home.HomeComponent;

/**
 * This fragment is just a shell for the time being.
 */
public class AccountFragment extends BaseFragment<HomeComponent> {

    // Views
    private AccountFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        if (component == null) {
            component = DaggerHomeComponent.builder()
                    .applicationComponent(RedditApplication.getComponent())
                    .activityModule(new ActivityModule(getActivity()))
                    .build();
        }
        component.inject(this);
    }

    protected void setupViews() {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showError(Throwable throwable) {
    }
}
