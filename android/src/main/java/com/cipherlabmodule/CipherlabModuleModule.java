package com.cipherlabmodule;

import android.content.IntentFilter;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cipherlab.barcode.ReaderManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;


import java.util.HashMap;
import java.util.Map;

@ReactModule(name = CipherlabModuleModule.NAME)
public class CipherlabModuleModule extends ReactContextBaseJavaModule {
  public static final String NAME = "CipherlabModule";
  Context context;
  private ReaderManager mReaderManager;
  public CipherlabModuleModule(ReactApplicationContext reactContext) {
    super(reactContext);
    context = reactContext.getApplicationContext();
    mReaderManager = ReaderManager.InitInstance(context);
    IntentFilter filter = new IntentFilter();
    filter.addAction(com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA);
    filter.addAction(com.cipherlab.barcode.GeneralString.Intent_PASS_TO_APP);
    filter.addAction(com.cipherlab.barcode.GeneralString.Intent_DECODE_ERROR);
    filter.addAction(com.cipherlab.barcode.GeneralString.Intent_READERSERVICE_CONNECTED);
    DataReceiver dataReceiver = new DataReceiver(mReaderManager,reactContext);
    context.registerReceiver(dataReceiver, filter);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @Nullable
  @Override
  public Map<String, Object> getConstants() {
    Map<String,Object> map = new HashMap<>();
    map.put("Intent_SOFTTRIGGER_DATA",com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA);
    map.put("Intent_PASS_TO_APP",com.cipherlab.barcode.GeneralString.Intent_PASS_TO_APP);
    return map;
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void softScanTrigger() {
    if (mReaderManager != null)
      {
        Thread sThread = new Thread(new Runnable() {
          @Override
          public void run() {
            mReaderManager.SoftScanTrigger();
          }
        });
        sThread.setPriority( Thread.MAX_PRIORITY );
        sThread.start();
      }
  }
}
