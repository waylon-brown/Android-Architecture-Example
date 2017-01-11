 package com.redditapp.screens.home;

 import com.redditapp.api.RedditService;
 import com.redditapp.base.mvp.BasePresenter;
 import com.redditapp.dagger.modules.NetworkModule;
 import com.redditapp.models.AccessTokenRequest;
 import com.redditapp.models.AccessTokenResponse;

 import java.util.UUID;

 import javax.inject.Inject;
 import javax.inject.Named;

 import io.reactivex.Observer;
 import io.reactivex.android.schedulers.AndroidSchedulers;
 import io.reactivex.disposables.Disposable;
 import io.reactivex.schedulers.Schedulers;
 import retrofit2.Retrofit;
 import timber.log.Timber;

 public class HomePresenter extends BasePresenter<HomeActivity> {

    private Retrofit retrofit;

    @Inject
    public HomePresenter(@Named(NetworkModule.AUTHENTICATED_HTTP_CLIENT) Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    protected void onLoad() {
        retrofit.create(RedditService.class)
                .getNoUserAccessToken(AccessTokenRequest.create(UUID.randomUUID().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<AccessTokenResponse>() {

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        //TODO: handle
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AccessTokenResponse response) {
                        getView().showContent(response);
                    }
                });



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