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



```typescript jsx

// in-initialize
interface option {
  appId: string; // required
  themes?: number; // default 0 choose 1 | 0
  enableCoppa?: boolean; // optional default true
  enableCcpa?: boolean; // optional default true
  enableGdpr?: boolean; // optional default true
  enableDebug?: boolean; // optional default true
}

interface props  {
  initialize(option): Promise<boolean>;
  isinitialize(): Promise<boolean>;
  loadAdIntertitial(unitAd: string): Promise<boolean>;
  showIntertitialAd(): void;
  destroy(): void;
  prefetchAds(unitAd: string, callback: Function): void;
};
```


```typescript jsx
  // index.js

  import SDK from 'react-native-sdkx';

  export interface option {
    appId: string;
    themes?: number;
    enableCoppa?: boolean;
    enableCcpa?: boolean;
    enableGdpr?: boolean;
    enableDebug?: boolean;
  }

  SDK.initialize(option)
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
  adUnit?: string; // bannerId | nativeId
  onReadyForRefresh?: Function;
  onUiiClosed?: Function;
  onUiiOpened?: Function;
  onAdLoadFailed?(error?: string): Function;
  onAdLoaded?: Function;
}

<BannerAd adUnit={'AdId'} style={{ height: 100, width: '100%' }} />

```

```
To beautify the native appearance -
I suggest a minimum height style of 250

```
## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
