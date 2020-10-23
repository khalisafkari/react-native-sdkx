package com.reactnativesdkx;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.view.ReactViewGroup;
import com.greedygame.core.adview.GGAdview;
import com.greedygame.core.adview.interfaces.AdLoadCallback;
import com.greedygame.core.adview.modals.AdRequestErrors;

public class BannerAd extends ReactViewGroup {


  private static String TAG = "GGADS";
  private Integer adMaxSize = 300;
  private String adUnit = "float-4901";
  private ReactContext mContext;

  private final Runnable measureAndLayout = new Runnable() {
    @Override
    public void run() {
      for (int i = 0; i < getChildCount(); i++) {
        GGAdview child = (GGAdview) getChildAt(i);
        child.measure(
          MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
          MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY)
        );
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
      }
    }
  };

  public BannerAd(ThemedReactContext context) {
    super(context);
    mContext = context;
  }

  private void attachNewAdView() {
    Resources r = getmContext().getResources();
    DisplayMetrics dm = r.getDisplayMetrics();

    final GGAdview ggAdview = new GGAdview(getContext());
    GGAdview oldView = (GGAdview) getChildAt(0);
    if (oldView != null) {
      oldView.removeAllViews();
    }

    int pxW = r.getDisplayMetrics().widthPixels;
    int pxH = dp2px(adMaxSize,dm);
    ggAdview.setLayoutParams(new ViewGroup.LayoutParams(pxW,pxH));
    addView(ggAdview);
    setupView();
  }

  private void setupView() {
    final GGAdview ggAdview = (GGAdview) getChildAt(0);
    ggAdview.setUnitId(adUnit);
    ggAdview.setAdsMaxHeight(adMaxSize);
    ggAdview.loadAd(new AdLoadCallback() {
      @Override
      public void onReadyForRefresh() {
        Log.d("GGADS","Ad Ready for refresh");
      }
      @Override
      public void onUiiClosed() {
        Log.d("GGADS","Uii closed");
      }
      @Override
      public void onUiiOpened() {
        Log.d("GGADS","Uii Opened");
      }
      @Override
      public void onAdLoadFailed (AdRequestErrors cause) {
        Log.d("GGADS","Ad Load Failed "+cause);
      }
      @Override
      public void onAdLoaded() {
        Log.d("GGADS","Ad Loaded");
        int top = ggAdview.getTop();
        int left = ggAdview.getLeft();
        int height = ggAdview.getHeight();
        int width = ggAdview.getWidth();
        Log.d("GGADS", String.valueOf(top));
        Log.d("GGADS", String.valueOf(left));
        Log.d("GGADS", String.valueOf(height));
        Log.d("GGADS", String.valueOf(width));
      }
    });
  }

  public void setAdUnit(String unit) {
      if (adUnit != null && unit != null) {
        adUnit = unit;
      }
      attachNewAdView();
  }

  private ReactContext getmContext() {
    return (ReactContext) getContext();
  }

  private int dp2px(int dp, DisplayMetrics displayMetrics) {
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,displayMetrics));
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    final GGAdview ggAdview = (GGAdview) getChildAt(0);
    if (ggAdview != null) {
      ggAdview.removeAllViews();
    }
    attachNewAdView();
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    post(measureAndLayout);
  }
}
