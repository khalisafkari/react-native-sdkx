package com.reactnativesdkx

import android.util.Log
import com.facebook.react.bridge.*
import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.interfaces.PrefetchAdsListener
import com.greedygame.core.interstitial.general.GGInterstitialAd
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener
import com.greedygame.core.models.ThemeData


class SdkxModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var appConfig:AppConfig? = null
    private var mAdIntertitial: GGInterstitialAd? = null;
    private var iThemes: Int = 0;
    private var isGDPR: Boolean = true
    private var isCCPA: Boolean = true
    private var isCOPPA: Boolean = true
    private var isDebug: Boolean = false
    private var isMopubAds: Boolean = true
    private var isAdmob: Boolean = true
    private var isFacebookAds: Boolean = true

    override fun getName(): String {
        return "Sdkx"
    }


    @ReactMethod
    fun initialize(appId: String) {
      val themes: ThemeData = if (iThemes == 0) ThemeData.LIGHT else ThemeData.DARK
      appConfig = AppConfig.Builder(reactApplicationContext)
        .withAppId(appId)
        .setTheme(themes)
        .enableCoppa(isCOPPA)
        .enableCcpa(isCOPPA)
        .enableGdpa(isGDPR)
        .enableDebug(isDebug)
        .enableMopubAds(isMopubAds)
        .enableAdmobAds(isAdmob)
        .enableFacebookAds(isFacebookAds)
        .build();
      GreedyGameAds.initWith(appConfig!!)
    }

    @ReactMethod
    fun isinitialize(promise: Promise) {
      promise.resolve(GreedyGameAds.isSdkInitialized)
    }

    @ReactMethod
    fun destroy() {
      GreedyGameAds.destroy()
    }

    @ReactMethod
    fun setThemes(type: Int) {
      iThemes = if (type == 0) 0 else 1
    }

    @ReactMethod
    fun setCCPA(status: Boolean) {
      isCCPA = status
    }

    @ReactMethod
    fun setCOPPA(status: Boolean) {
      isCOPPA = status
    }

    @ReactMethod
    fun setGDPR(status: Boolean) {
      isGDPR = status
    }

    @ReactMethod
    fun setDebug(status: Boolean) {
        isDebug = status
    }

    @ReactMethod
    fun MopubAds(status: Boolean) {
      isMopubAds = status
    }

    @ReactMethod
    fun setAdmob(status: Boolean) {
      isAdmob = status
    }

    @ReactMethod
    fun setFacebookAds(status: Boolean) {
      isFacebookAds = status
    }

    @ReactMethod
    fun loadAdIntertitial(unitAd: String, promise: Promise) {
        mAdIntertitial = GGInterstitialAd(reactApplicationContext,unitAd);
        mAdIntertitial!!.loadAd(object: GGInterstitialEventsListener{
          override fun onAdLoaded() {
            promise.resolve(true)
          }
          override fun onAdLoadFailed(cause: AdRequestErrors) {
            Log.d("GGADS","Ad Load Failed $cause")
          }
          override fun onAdOpened() {
            Log.d("GGADS","Ad Opened")
          }
          override fun onAdClosed() {
            Log.d("GGADS","Ad Closed")
          }
          override fun onAdLeftApplication() {
            Log.d("GGADS","Ad Left Application")
          }
        })
    }

    @ReactMethod
    fun showIntertitialAd() {
        if(mAdIntertitial!!.isAdLoaded) {
          mAdIntertitial!!.show()
        }
    }



    @ReactMethod
    fun prefetchAds(unitAd: String, callback: Callback) {
      GreedyGameAds.prefetchAds(object: PrefetchAdsListener {
        override fun onAdPrefetchFailed(unitId: String, cause: AdRequestErrors) {
          callback.invoke(cause)
        }
        override fun onAdPrefetched(unitId: String) {
          callback.invoke(unitId)
        }
        override fun onPrefetchComplete() {
          callback.invoke("onPrefetchComplete")
        }
      },unitAd)
    }




    override fun onCatalystInstanceDestroy() {
      if (mAdIntertitial != null) {
        mAdIntertitial!!.destroy()
      }
    }



}
