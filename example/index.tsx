import { AppRegistry } from 'react-native';
import App from './src/App';
import { name as appName } from './app.json';
import SDK from 'react-native-sdkx';

SDK.initialize({
  appId:'45921653'
}).then(console.log)


AppRegistry.registerComponent(appName, () => App);
