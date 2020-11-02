package com.reactnativesdkx;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.greedygame.core.adview.GGAdview;
import com.greedygame.core.adview.interfaces.AdLoadCallback;
import com.greedygame.core.adview.modals.AdRequestErrors;
import com.reactnativesdkx.utils.GreedyGameManager;

public class BannerAd extends LinearLayout {

  private static String  TAG = "GGADS";
  private int maxHeight = 250;
  private String unit = "float-4901";

  private final Runnable measureAndLayout = () -> {
    for (int i = 0;i < getChildCount(); i ++) {
      View child = getChildAt(i);
      child.measure(
        MeasureSpec.makeMeasureSpec(getMeasuredWidth(),MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(getMeasuredHeight(),MeasureSpec.EXACTLY)
      );
      child.layout(0,0,child.getMeasuredWidth(),child.getMeasuredHeight());
    }
  };

  public BannerAd(Context context) {
    super(context);
  }

  public void setAdUnit(String adUnit) {
    if (unit != null && adUnit != null) {
      unit = adUnit;
    }
    attachNewAdView();
  }

  private void attachNewAdView() {
    Resources resources =  getDim().getResources();
    DisplayMetrics displayMetrics = resources.getDisplayMetrics();

    final GGAdview ggAdview = new GGAdview(getContext());
    final GGAdview oldView = (GGAdview) getChildAt(0);

    if (oldView != null) {
      oldView.removeAllViews();
    }

//    int dpW = resources.getDisplayMetrics().widthPixels;
//    int dpH = dp2px(maxHeight,displayMetrics);
//    Log.d(TAG, "dpW" + String.valueOf(dpW));
//    Log.d(TAG, "dpH" + String.valueOf(dpH));
//    ggAdview.setLayoutParams(new ViewGroup.LayoutParams(dpW,dpH));
    addView(ggAdview);
    GreedyGameManager greedyGameManager = new GreedyGameManager();
    if (greedyGameManager.isSdkInitialized()) {
      callAdView();
    } else {
      Toast.makeText(getContext(),"appId not set",1000).show();
    }
  }

  private void callAdView() {
    final GGAdview ggAdview = (GGAdview) getChildAt(0);
    ggAdview.setUnitId(unit);
    ggAdview.setAdsMaxHeight(250);
    ggAdview.loadAd(new AdLoadCallback() {
      @Override
      public void onReadyForRefresh() {
        getEmitter().receiveEvent(getId(),"onReadyForRefresh", null);
        Log.d("GGADS","Ad Ready for refresh");
      }
      @Override
      public void onUiiClosed() {
        getEmitter().receiveEvent(getId(),"onUiiClosed", null);
        Log.d("GGADS","Uii closed");
      }
      @Override
      public void onUiiOpened() {
        getEmitter().receiveEvent(getId(),"onUiiOpened", null);
        Log.d("GGADS","Uii Opened");
      }
      @Override
      public void onAdLoadFailed (AdRequestErrors cause) {
        WritableMap params = Arguments.createMap();
        params.putString("error",cause.toString());
        getEmitter().receiveEvent(getId(),"onAdLoadFailed", params);
        Log.d("GGADS","Ad Load Failed "+cause);
      }
      @Override
      public void onAdLoaded() {
        WritableMap map = Arguments.createMap();
        map.putInt("height", ggAdview.getWidth());
        map.putInt("width", ggAdview.getHeight());
        getEmitter().receiveEvent(getId(),"onAdLoaded", map);
        Log.d("GGADS","Ad Loaded");
      }
    });
  }



  private ReactContext getDim() {
    return (ReactContext) getContext();
  }

  private RCTEventEmitter getEmitter() {
    return getDim().getJSModule(RCTEventEmitter.class);
  }

  private int dp2px(int dp, DisplayMetrics dm) {
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,dm));
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    post(measureAndLayout);
  }
}
