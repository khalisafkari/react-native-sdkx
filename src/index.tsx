import { NativeModules } from 'react-native';
import BannerAd from './BannerView';

type SdkxType = {
  initialize(appId: string): void;
  isinitialize(): Promise<boolean>;
  loadAdIntertitial(unitAd: string): Promise<boolean>;
  showIntertitialAd(): void;
  destroy(): void;
  setThemes(select: 0 | 1): void;
  setCCPA(status: boolean): void;
  setCOPPA(status: boolean): void;
  setGDPR(status: boolean): void;
  setDebug(status: boolean): void;
  MopubAds(status: boolean): void;
  setAdmob(status: boolean): void;
  setFacebookAds(status: boolean): void;
  prefetchAds(unitAd: string, callback: Function): void;
};

const { Sdkx } = NativeModules;
export { BannerAd };

export default Sdkx as SdkxType;
