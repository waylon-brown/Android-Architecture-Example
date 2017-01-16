 package com.redditapp.screens.home;

 import com.redditapp.api.RedditService;
import com.redditapp.base.mvp.BasePresenter;
import com.redditapp.dagger.modules.BasicAuthNetworkModule;
import com.redditapp.dagger.modules.OauthNetworkModule;
import com.redditapp.models.AccessTokenResponse;
 import com.redditapp.util.StringUtils;

 import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import timber.log.Timber;

 public class HomePresenter extends BasePresenter<HomeActivity> {

     private Retrofit basicAuthRetrofit;
     private Retrofit oauthRetrofit;

    @Inject
    public HomePresenter(@Named(BasicAuthNetworkModule.BASIC_AUTH_HTTP_CLIENT) Retrofit basicAuthRetrofit,
                         @Named(OauthNetworkModule.OAUTH_HTTP_CLIENT) Retrofit oauthRetrofit) {
        this.basicAuthRetrofit = basicAuthRetrofit;
        this.oauthRetrofit = oauthRetrofit;
    }

    @Override
    protected void onLoad() {

        // First get access token, then get main feed
        DisposableSingleObserver observer = getUserAccessTokenObservable()
                .flatMap(response -> getRedditFrontPageObservable(response.accessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                   @Override
                   public void onSuccess(String value) {
						getView().showContent(value);
                   }

                   @Override
                   public void onError(Throwable e) {
						Timber.e(e);
                   }
                });
        disposables.add(observer);



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

     /**
      * Return cached token or retrieve a new one if needed
	  *
	  * TODO: cache
      */
    private Single<AccessTokenResponse> getUserAccessTokenObservable() {
        return basicAuthRetrofit.create(RedditService.class)
                .getNoUserAccessToken(RedditService.GRANT_TYPE, UUID.randomUUID().toString());
    }

    private Single<String> getRedditFrontPageObservable(String accessToken) {
		return oauthRetrofit.create(RedditService.class)
                .getFrontPageListing(StringUtils.getBearerToken(accessToken));
//        return Single.just("Reddit front page JSON from access token: " + accessToken);
    }
}