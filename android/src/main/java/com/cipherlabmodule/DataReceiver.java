package com.cipherlabmodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cipherlab.barcode.GeneralString;
import com.cipherlab.barcode.ReaderManager;
import com.cipherlab.barcode.decoder.BcReaderType;
import com.cipherlab.barcode.decoder.Enable_State;
import com.cipherlab.barcode.decoder.KeyboardEmulationType;
import com.cipherlab.barcode.decoder.OutputEnterChar;
import com.cipherlab.barcode.decoder.OutputEnterWay;
import com.cipherlab.barcode.decoderparams.ReaderOutputConfiguration;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class DataReceiver extends BroadcastReceiver{
  public ReaderManager readerManager;
  public ReactContext reactContext;

  public DataReceiver(ReaderManager readerManager, ReactContext reactContext) {
    this.readerManager = readerManager;
    this.reactContext = reactContext;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(GeneralString.Intent_SOFTTRIGGER_DATA)) {
      String data = intent.getStringExtra(GeneralString.BcReaderData);
      sendEvent(GeneralString.Intent_SOFTTRIGGER_DATA,data);
    } else if (intent.getAction().equals(GeneralString.Intent_PASS_TO_APP)) {
      String data = intent.getStringExtra(GeneralString.BcReaderData);
      sendEvent(GeneralString.Intent_PASS_TO_APP,data);
    } else if (intent.getAction().equals(GeneralString.Intent_READERSERVICE_CONNECTED)) {
      sendEvent(GeneralString.Intent_READERSERVICE_CONNECTED,"conntected");
      BcReaderType myReaderType = readerManager.GetReaderType();
      ReaderOutputConfiguration settings = new ReaderOutputConfiguration();
      readerManager.Get_ReaderOutputConfiguration(settings);
      settings.enableKeyboardEmulation = KeyboardEmulationType.None;
      settings.autoEnterWay = OutputEnterWay.Disable;
      settings.autoEnterChar = OutputEnterChar.None;
      settings.showCodeLen = Enable_State.FALSE;
      settings.showCodeType = Enable_State.FALSE;
      settings.szPrefixCode = "";
      settings.szSuffixCode = "";
      settings.useDelim = ':';
      readerManager.Set_ReaderOutputConfiguration(settings);
      readerManager.SetActive(true);
    }
  }

  private void sendEvent(String eventName,Object params) {
    if (reactContext == null){
      return;
    }
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
  }
}
