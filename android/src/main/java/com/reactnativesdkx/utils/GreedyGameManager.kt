package com.reactnativesdkx.utils

import android.content.Context
import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener
import com.greedygame.core.models.ThemeData

class GreedyGameManager {
  fun isSdkInitialized(): Boolean {
    return GreedyGameAds.isSdkInitialized
  }

  fun destroy() {
   if (isSdkInitialized()) {
     GreedyGameAds.destroy()
   }
  }


  fun init(
    context: Context,
    appId: String,
    themes: Int = 1,
    enableCoppa: Boolean = true,
    enableCcpa: Boolean = true,
    enableGdpa: Boolean = true,
    enableDebug: Boolean = false,
    listener: GreedyGameAdsEventsListener? = null
  ) {

    if (isSdkInitialized()) {
      return
    }

    val appConfig = AppConfig.Builder(context)
      .setTheme(if (themes == 1) ThemeData.DARK else ThemeData.LIGHT )
      .enableCoppa(enableCoppa)
      .enableCcpa(enableCcpa)
      .enableGdpa(enableGdpa)
      .enableDebug(enableDebug)
      .withAppId(appId)
      .build()
    GreedyGameAds.initWith(appConfig,listener)
  }
}
