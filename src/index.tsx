import { NativeModules } from 'react-native';
export { default as BannerAd } from './BannerView';

const { GDModule } = NativeModules;
export interface option {
  appId: string;
  themes?: number;
  enableCoppa?: boolean;
  enableCcpa?: boolean;
  enableGdpr?: boolean;
  enableDebug?: boolean;
}
export default {
  initialize: async function ({
    appId = '45921653',
    themes = 1,
    enableCcpa = true,
    enableCoppa = true,
    enableGdpr = true,
    enableDebug = true,
  }: option): Promise<boolean> {
    return await GDModule.initialize({
      appId,
      themes,
      enableCcpa,
      enableCoppa,
      enableGdpr,
      enableDebug,
    });
  },
  isinitialize: async function (): Promise<boolean> {
    return await GDModule.isinitialize();
  },
  loadAdIntertitial: async function (unitAd: string): Promise<boolean> {
    return await GDModule.loadAdIntertitial(unitAd);
  },
  destroy: function (): void {
    return GDModule.destroy();
  },
  showIntertitialAd: function (): void {
    return GDModule.showIntertitialAd();
  },
  prefetchAds: function (unitAd: string, callback: Function): void {
    return GDModule.prefetchAds(unitAd, callback);
  },
};
