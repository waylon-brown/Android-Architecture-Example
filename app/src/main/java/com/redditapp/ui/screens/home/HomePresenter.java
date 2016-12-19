package com.redditapp.ui.screens.home;

import com.redditapp.base.mvp.BasePresenter;
import com.redditapp.base.navigation.activity.ActivityScreenSwitcher;

import javax.inject.Inject;

public class HomePresenter extends BasePresenter<HomeActivity> {

    private final ActivityScreenSwitcher screenSwitcher;

//    private Subscription request;
//    private Subscription clicks;

    @Inject
    public HomePresenter(ActivityScreenSwitcher screenSwitcher) {
        this.screenSwitcher = screenSwitcher;
    }

    @Override
    protected void onLoad() {
        super.onLoad();
//        getView().showLoading();
//        request = galleryDatabase.loadGallery(section, new EndlessObserver<List<Image>>() {
//            @Override
//            public void onNext(List<Image> images) {
//                if (images.size() == 0) {
//                    getView().showEmpty();
//                } else {
//                    getView().getAdapter().replaceWith(images);
//                    getView().showContent();
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                Timber.e(throwable, "Load gallery error");
//                getView().showError(throwable);
//            }
//        });
//        clicks = getView().observeImageClicks().subscribe(
//                image -> {
//                    Timber.d("Image clicked with id = %s", image.first.id);
//                    ActivityScreen screen = new ImgurImageActivity.Screen(image.first.id);
//                    screen.attachTransitionView(image.second);
//                    screenSwitcher.open(screen);
//                }
//        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        request.unsubscribe();
//        clicks.unsubscribe();
    }

    public void refresh() {
        // TODO: implement refreshing
//        final GalleryView view = getView();
//        if (view != null) {
//            view.setRefreshed();
//        }
    }
}