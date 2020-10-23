package com.reactnativesdkx;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.react.bridge.ReactContext;
import com.greedygame.core.adview.GGAdview;
import com.greedygame.core.adview.interfaces.AdLoadCallback;
import com.greedygame.core.adview.modals.AdRequestErrors;

public class BannerView extends LinearLayout {

  private ReactContext mReactContext;

  private final Runnable measureAndLayout = () -> {
    measure(
      MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
      MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
    layout(getLeft(), getTop(), getRight(), getBottom());
  };

  public BannerView(Context context) {
    super(context);
    if (context instanceof ReactContext) {
      mReactContext = (ReactContext) context;
    }
    attachNewAdView();
  }

  private void attachNewAdView() {
      final GGAdview ggAdview = new GGAdview(getContext());
      GGAdview oldView = (GGAdview) getChildAt(0);
      if (oldView != null) {
        oldView.removeAllViews();
      }

      addView(ggAdview);
      setupView();
  }

  private void setupView() {
      final GGAdview ggAdview = (GGAdview) getChildAt(0);
      ggAdview.setUnitId("float-4901");
      ggAdview.setAdsMaxHeight(300);
      ggAdview.loadAd(new AdLoadCallback() {
        @Override
        public void onReadyForRefresh() {
          Log.d("GGADS","Ad Ready for refresh");
          int top = ggAdview.getTop();
          int left = ggAdview.getLeft();
          int height = ggAdview.getHeight();
          int width = ggAdview.getWidth();
          ggAdview.measure(width,height);
          ggAdview.layout(left, top, left + width, top + height);
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
        }
      });
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    post(measureAndLayout);
  }
}
