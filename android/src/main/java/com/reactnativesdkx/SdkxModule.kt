package com.reactnativesdkx

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener
import com.greedygame.core.interstitial.general.GGInterstitialAd
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.adview.GGAdview

class SdkxModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var appConfig:AppConfig? = null
    private var mAdIntertitial: GGInterstitialAd? = null;

    override fun getName(): String {
        return "Sdkx"
    }


    @ReactMethod
    fun multiply(a: Int, b: Int, promise: Promise) {
      promise.resolve(a * b)
    }

    @ReactMethod
    fun initialize(appId: String) {
      appConfig = AppConfig.Builder(reactApplicationContext).withAppId(appId).build();
      GreedyGameAds.initWith(appConfig!!)
    }

    @ReactMethod
    fun isinitialize(promise: Promise) {
      promise.resolve(GreedyGameAds.isSdkInitialized)
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

  override fun onCatalystInstanceDestroy() {
//    super.onCatalystInstanceDestroy()
    if (mAdIntertitial != null) {
      mAdIntertitial!!.destroy()
    }
  }



}
