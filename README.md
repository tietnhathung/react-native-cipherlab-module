# react-native-cipherlab-module

Sdk to control cipherlab scaner

## Installation

```sh
npm install react-native-cipherlab-module
```

## Usage

```js
import { getConstants, softScanTrigger } from 'react-native-cipherlab-module';

// ...

softScanTrigger();

// ...

const { Intent_SOFTTRIGGER_DATA, Intent_PASS_TO_APP } = getConstants();

function onScan(code: string) {
  console.log('QR code: ', code);
}

const eventEmitter = new NativeEventEmitter();
eventEmitter.addListener(Intent_SOFTTRIGGER_DATA, onScan);
eventEmitter.addListener(Intent_PASS_TO_APP, onScan);

```

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
