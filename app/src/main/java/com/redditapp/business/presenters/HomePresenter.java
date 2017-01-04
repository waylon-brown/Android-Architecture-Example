 package com.redditapp.business.presenters;

 import com.redditapp.base.mvp.BasePresenter;
 import com.redditapp.ui.HomeActivity;

 import javax.inject.Inject;

public class HomePresenter extends BasePresenter<HomeActivity> {

//    @Inject
//    public HomePresenter(ActivityScreenSwitcher screenSwitcher) {
//        this.screenSwitcher = screenSwitcher;
//    }

    @Inject
    public HomePresenter() {}

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
}