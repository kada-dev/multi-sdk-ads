package com.kadadevelopers.ads.sdk.format;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kadadevelopers.ads.sdk.util.OnShowAdCompleteListener;
import com.wortise.ads.AdError;
import com.wortise.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenAdWortise {

    private static final String LOG_TAG = "AppOpenAd";
    private AppOpenAd wortiseAppOpenAd = null;
    private boolean isLoadingAd = false;
    public boolean isShowingAd = false;
    private long loadTime = 0;

    public AppOpenAdWortise() {
    }

    public void loadAd(Context context, String wortiseAppOpenId) {
        if (isLoadingAd || isAdAvailable()) {
            return;
        }
        isLoadingAd = true;
        wortiseAppOpenAd = new AppOpenAd(context, wortiseAppOpenId);
        wortiseAppOpenAd.setListener(wortiseAppOpenAdListener);
        wortiseAppOpenAd.loadAd();
    }

    public boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isAdAvailable() {
        return wortiseAppOpenAd != null && wortiseAppOpenAd.isAvailable() && wasLoadTimeLessThanNHoursAgo(4);
    }

    public void showAdIfAvailable(@NonNull final Activity activity, String appOpenAdUnitId) {
        showAdIfAvailable(activity, appOpenAdUnitId, () -> {
        });
    }

    public void showAdIfAvailable(@NonNull final Activity activity, String wortiseAppOpenAdUnitId, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (isShowingAd) {
            Log.d(LOG_TAG, "The app open ad is already showing.");
            return;
        }

        if (!isAdAvailable()) {
            Log.d(LOG_TAG, "The app open ad is not ready yet.");
            onShowAdCompleteListener.onShowAdComplete();
            loadAd(activity, wortiseAppOpenAdUnitId);
            return;
        }

        Log.d(LOG_TAG, "Will show ad.");
        wortiseAppOpenAd.setListener(new AppOpenAd.Listener() {
            @Override
            public void onAppOpenImpression(@NonNull AppOpenAd appOpenAd) {

            }

            @Override
            public void onAppOpenFailedToShow(@NonNull AppOpenAd appOpenAd, @NonNull AdError adError) {

            }

            @Override
            public void onAppOpenFailedToLoad(@NonNull AppOpenAd appOpenAd, @NonNull AdError adError) {
                isLoadingAd = false;
                wortiseAppOpenAd = null;
                isShowingAd = false;
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity, wortiseAppOpenAdUnitId);
            }

            @Override
            public void onAppOpenClicked(@NonNull AppOpenAd appOpenAd) {

            }

            @Override
            public void onAppOpenDismissed(@NonNull AppOpenAd appOpenAd) {
                wortiseAppOpenAd = null;
                isShowingAd = false;
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity, wortiseAppOpenAdUnitId);
                Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
            }

            @Override
            public void onAppOpenLoaded(@NonNull AppOpenAd appOpenAd) {
                isLoadingAd = false;
                loadTime = (new Date()).getTime();
                Log.d(LOG_TAG, "onAdLoaded.");
            }

            @Override
            public void onAppOpenShown(@NonNull AppOpenAd appOpenAd) {

            }
        });

        isShowingAd = true;
        wortiseAppOpenAd.showAd(activity);
    }

    AppOpenAd.Listener wortiseAppOpenAdListener = new AppOpenAd.Listener() {
        @Override
        public void onAppOpenImpression(@NonNull AppOpenAd appOpenAd) {

        }

        @Override
        public void onAppOpenFailedToShow(@NonNull AppOpenAd appOpenAd, @NonNull AdError adError) {

        }

        @Override
        public void onAppOpenFailedToLoad(@NonNull AppOpenAd appOpenAd, @NonNull AdError adError) {
            isLoadingAd = false;
            wortiseAppOpenAd.loadAd();
        }

        @Override
        public void onAppOpenClicked(@NonNull AppOpenAd appOpenAd) {

        }

        @Override
        public void onAppOpenDismissed(@NonNull AppOpenAd appOpenAd) {
            wortiseAppOpenAd.loadAd();
        }

        @Override
        public void onAppOpenLoaded(@NonNull AppOpenAd appOpenAd) {
            isLoadingAd = false;
            loadTime = (new Date()).getTime();
        }

        @Override
        public void onAppOpenShown(@NonNull AppOpenAd appOpenAd) {

        }
    };

}