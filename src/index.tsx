import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-cipherlab-module' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const CipherlabModule = NativeModules.CipherlabModule
  ? NativeModules.CipherlabModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function getConstants(): {
  Intent_SOFTTRIGGER_DATA: string;
  Intent_PASS_TO_APP: string;
} {
  return CipherlabModule.getConstants();
}
export function softScanTrigger(): void {
  return CipherlabModule.softScanTrigger();
}
