package com.reactnativesdkx


import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp

class BannerViewManager: ViewGroupManager<BannerManager>() {

  override fun getName() = "GDBannerView"

  override fun createViewInstance(reactContext: ThemedReactContext): BannerManager {
      return BannerManager(reactContext)
  }

  @ReactProp(name = "adUnit")
  fun setAdUnit(view: BannerManager, adUnit: String) {
    view.setAdUnit(adUnit)
  }

}
