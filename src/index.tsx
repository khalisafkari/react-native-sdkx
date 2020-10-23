import { NativeModules } from 'react-native';
import BannerAd from './BannerView';

type SdkxType = {
  multiply(a: number, b: number): Promise<number>;
  initialize(appId: string): void;
  isinitialize(): Promise<boolean>;
  loadAdIntertitial(unitAd: string): Promise<boolean>;
  showIntertitialAd(): void;
};

const { Sdkx } = NativeModules;
export { BannerAd };

export default Sdkx as SdkxType;
