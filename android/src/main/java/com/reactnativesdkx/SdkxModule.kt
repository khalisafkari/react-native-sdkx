package com.reactnativesdkx

import android.content.Context
import android.util.Log
import com.facebook.react.bridge.*
import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener
import com.greedygame.core.interfaces.PrefetchAdsListener
import com.greedygame.core.interstitial.general.GGInterstitialAd
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener
import com.greedygame.core.models.ThemeData
import com.reactnativesdkx.utils.GreedyGameManager


class SdkxModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var mAdIntertitial: GGInterstitialAd? = null;

    override fun getName(): String {
        return "GDModule"
    }


    @ReactMethod
    fun initialize(options: ReadableMap , promise: Promise) {
      val context: Context = reactApplicationContext
      Log.d("GGADS",options.toString())
      GreedyGameManager().init(
        context,
        options.getString("appId").toString(),
        options.getInt("themes"),
        options.getBoolean("enableCoppa"),
        options.getBoolean("enableCcpa"),
        options.getBoolean("enableGdpr"),
        options.getBoolean("enableDebug")
      )
      promise.resolve(GreedyGameManager().isSdkInitialized())
    }

    @ReactMethod
    fun isinitialize(promise: Promise) {
      promise.resolve(GreedyGameManager().isSdkInitialized())
    }

    @ReactMethod
    fun destroy() {
      GreedyGameManager().destroy();
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
