package com.reactnativesdkx;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;


import com.facebook.react.bridge.ReactContext;
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
      Log.d(TAG,"getMeasuredWidth \n" + child.getMeasuredWidth());
      Log.d(TAG,"getMeasuredWidth \n" + child.getMeasuredHeight());
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
    Log.d(TAG,"widthPixels \n" + String.valueOf(resources.getDisplayMetrics().widthPixels));
    Log.d(TAG,"heightPixels \n" + String.valueOf(resources.getDisplayMetrics().heightPixels));
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
          Log.d("GGADS", "top \b" + String.valueOf(top));
          Log.d("GGADS", "left \b"  + String.valueOf(left));
          Log.d("GGADS", "height \b" + String.valueOf(height));
          Log.d("GGADS", "width \b" + String.valueOf(width));
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

  private int dp2px(int dp, DisplayMetrics dm) {
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,dm));
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
