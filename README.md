## react-native-sdkx
<a href="https://ibb.co/6nVTVYb"><img src="https://i.ibb.co/4m9X9WM/Untitled-presentation.png" alt="Untitled-presentation" border="0"></a>

GreedyGame’s SDK X is an SDK for app developers to increase their monetisation capabilities. You can also connect your AdMob account and mediate ads to optimise your **AdMob** Monetization.

### installed
```shell script
npm install react-native-sdkx

or

yarn add react-native-sdkx
```


### Usage

<a href="https://ibb.co/FhdnrRY"><img width="49%" src="https://i.ibb.co/104bgFX/Screenshot-1603528544.png" alt="Screenshot-1603528544" border="0"></a>
<a href="https://ibb.co/09Ds8dB"><img width="49%" src="https://i.ibb.co/SmsfW9Q/Screenshot-1603528555.png" alt="Screenshot-1603528555" border="0"></a><br /><a target='_blank' href='https://id.imgbb.com/'>free pic png</a><br />

```typescript


interface props  {
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
```

```typescript

// intertitial Ad
import SDKX from 'react-native-sdkx';

const loadAd = await SDKX.loadAdIntertitial('unit ad')
if (loadAd)  { SDKX.showIntertitialAd() }

```

```typescript jsx
//Banner
import SDKX, { BannerAd } from 'react-native-sdkx';

interface props {
  style?: StyleProp<ViewStyle>;
  adUnit?: string;
  onReadyForRefresh?: Function;
  onUiiClosed?: Function;
  onUiiOpened?: Function;
  onAdLoadFailed?(error?: string): Function;
  onAdLoaded?: Function;
}

<BannerAd adUnit={'float-4901'} style={{ height: 100, width: '100%' }} />

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
