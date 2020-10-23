package com.reactnativesdkx


import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager

class BannerViewManager: ViewGroupManager<BannerView>() {

  override fun getName() = "GDBannerView"

  override fun createViewInstance(reactContext: ThemedReactContext): BannerView {
      return BannerView(reactContext)
  }

}
