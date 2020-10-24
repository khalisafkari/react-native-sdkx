package com.reactnativesdkx;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.greedygame.core.adview.GGAdview;
import com.greedygame.core.adview.interfaces.AdLoadCallback;
import com.greedygame.core.adview.modals.AdRequestErrors;

public class BannerManager extends ReactViewGroup {

  private static String  TAG = "GGADS";
  private int maxHeight = 300;
  private String unit = "float-4901";

  private final Runnable measureAndLayout = () -> {
    for (int i = 0;i < getChildCount(); i ++) {
      GGAdview child = (GGAdview) getChildAt(i);
      child.measure(
        MeasureSpec.makeMeasureSpec(getMeasuredWidth(),MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(getMeasuredHeight(),MeasureSpec.EXACTLY)
      );
      child.layout(0,0,child.getMeasuredWidth(),child.getMeasuredHeight());
    }
  };

  public BannerManager(Context context) {
    super(context);
  }

  private void attachNewAdView() {
    Resources resources =  getDim().getResources();
    DisplayMetrics displayMetrics = resources.getDisplayMetrics();

    final GGAdview ggAdview = new GGAdview(getContext());
    final GGAdview oldView = (GGAdview) getChildAt(0);

    if (oldView != null) {
      oldView.removeAllViews();
    }


    int dpW = resources.getDisplayMetrics().widthPixels;
    int dpH = dp2px(maxHeight,displayMetrics);
    ggAdview.setLayoutParams(new ViewGroup.LayoutParams(dpW,dpH));
    addView(ggAdview);
    callAd();
  }

  private void callAd() {
      final GGAdview ggAdview = (GGAdview) getChildAt(0);
      ggAdview.setUnitId(unit);
      ggAdview.setAdsMaxHeight(maxHeight);
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
          getEmitter().receiveEvent(getId(),"onAdLoaded", null);
          Log.d("GGADS","Ad Loaded");
        }
      });
  }

  public void setAdUnit(String adUnit) {
      if (unit != null && adUnit != null) {
        unit = adUnit;
      }
      attachNewAdView();
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

//  @Override
//  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//    super.onLayout(changed, left, top, right, bottom);
//    final GGAdview ggAdview = (GGAdview) getChildAt(0);
//    if (ggAdview != null) {
//      ggAdview.removeAllViews();
//    }
//    attachNewAdView();
//  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    post(measureAndLayout);
  }
}
