import * as React from 'react';
import { requireNativeComponent, StyleProp, ViewStyle } from 'react-native';

const BannerView = requireNativeComponent('GDBannerView');

interface props {
  style?: StyleProp<ViewStyle>;
  adUnit?: string;
}

const BannerAd = (props: props) => {
  return <BannerView {...props} />;
};

export default BannerAd;
