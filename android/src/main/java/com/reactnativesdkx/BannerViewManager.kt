package com.reactnativesdkx


import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp

class BannerViewManager: ViewGroupManager<BannerAd>() {

  override fun getName() = "GDBannerView"

  override fun createViewInstance(reactContext: ThemedReactContext): BannerAd {
      return BannerAd(reactContext)
  }

  @ReactProp(name = "adUnit")
  fun setAdUnit(view: BannerAd, adUnit: String) {
    view.setAdUnit(adUnit)
  }

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> {
    return MapBuilder.of(
      "onReadyForRefresh",MapBuilder.of("registrationName","onReadyForRefresh"),
      "onUiiClosed",MapBuilder.of("registrationName","onUiiClosed"),
      "onUiiOpened",MapBuilder.of("registrationName","onUiiOpened"),
      "onAdLoadFailed",MapBuilder.of("registrationName","onAdLoadFailed"),
      "onAdLoaded",MapBuilder.of("registrationName","onAdLoaded")
    )
  }

}
