import * as React from 'react';

import {
  StyleSheet,
  View,
  Text,
  NativeEventEmitter,
  Button,
} from 'react-native';
import { getConstants, softScanTrigger } from 'react-native-cipherlab-module';
import { useEffect } from 'react';
const { Intent_SOFTTRIGGER_DATA, Intent_PASS_TO_APP } = getConstants();
export default function App() {
  const [result, setResult] = React.useState<string | undefined | null>();

  function onScan(code: string) {
    console.log('onScan', code);
    setResult(code);
  }

  useEffect(function () {
    const eventEmitter = new NativeEventEmitter();
    eventEmitter.addListener(Intent_SOFTTRIGGER_DATA, onScan);
    eventEmitter.addListener(Intent_PASS_TO_APP, onScan);
    return () => {
      console.log('clear event');
      eventEmitter.removeAllListeners(Intent_SOFTTRIGGER_DATA);
      eventEmitter.removeAllListeners(Intent_PASS_TO_APP);
    };
  }, []);

  function triggerScan() {
    softScanTrigger();
  }

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      <Button title={'Scan now'} onPress={triggerScan} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
