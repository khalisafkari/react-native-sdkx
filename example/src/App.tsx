import * as React from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';
import Sdkx, { BannerAd } from 'react-native-sdkx';

export default function App() {
  const [isinitialize, setisinitialize] = React.useState<boolean>(false);
  const [isLoadIntertitial, setLoadIntertitial] = React.useState<boolean>(
    false
  );

  React.useEffect(() => {
    Sdkx.initialize('45921653');
    Sdkx.isinitialize().then(setisinitialize);
    Sdkx.loadAdIntertitial('float-4898').then(setLoadIntertitial);
  }, []);

  return (
    <View style={styles.container}>
      <Text>isinitialize {String(isinitialize)}</Text>
      <Text>isLoadIntertitial {String(isLoadIntertitial)}</Text>
      <Button
        title={'Show Intertitial'}
        onPress={() => {
          Sdkx.showIntertitialAd();
        }}
      />
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'red',
          padding: 5,
        }}
      >
        <BannerAd
          adUnit={'float-4906'}
          style={{ height: '100%', width: '100%' }}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
