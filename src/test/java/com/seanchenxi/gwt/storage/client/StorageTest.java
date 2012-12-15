/*
 * Copyright 2012 Xi CHEN
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.seanchenxi.gwt.storage.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Created by: Xi
 */
public class StorageTest implements EntryPoint {

  public void onModuleLoad() {
    //testStorage(StorageExt.getSessionStorage(), StorageChangeEvent.Level.STRING);
    //testStorage(StorageExt.getLocalStorage(), StorageChangeEvent.Level.STRING);
    try{
      //StorageExt.getLocalStorage().clear();
      ArrayValueTest.putBooleanArrayValue(StorageExt.getLocalStorage(), 1);
    }catch(Exception e){
      GWT.log("error", e);
    }
  }

  private void testStorage(final StorageExt storage, final StorageChangeEvent.Level level){
    assert storage != null;
    storage.clear();
    StorageTestUnit.assertEquals("storage size", 0, storage.size());
    final HandlerRegistration hr1 = OtherTest.listenerTest(storage, level);

    Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {
      private int count = 0;
      @Override
      public boolean execute() {
        int storageLength = storage.size();
        try {
          switch(count){
            case 0:
              SimpleValueTest.putBooleanValue(storage, ++storageLength);
              break;
            case 1:
              SimpleValueTest.putByteValue(storage, ++storageLength);
              break;
            case 2:
              SimpleValueTest.putCharacterValue(storage, ++storageLength);
              break;
            case 3:
              SimpleValueTest.putDoubleValue(storage, ++storageLength);
              break;
            case 4:
              SimpleValueTest.putFloatValue(storage, ++storageLength);
              break;
            case 5:
              SimpleValueTest.putIntegerValue(storage, ++storageLength);
              break;
            case 6:
              SimpleValueTest.putLongValue(storage, ++storageLength);
              break;
            case 7:
              SimpleValueTest.putShortValue(storage, ++storageLength);
              break;
            case 8:
              SimpleValueTest.putStringValue(storage, ++storageLength);
              break;
            case 9:
              OtherTest.removeValue(storage);
              break;
            default:
              hr1.removeHandler();
              testStorageArray(StorageExt.getSessionStorage(), level);
              return false;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        count++;
        return true;
      }
    });
  }

  private void testStorageArray(final StorageExt storage, final StorageChangeEvent.Level level){
    assert storage != null;
    storage.clear();
    StorageTestUnit.assertEquals("storageArray - storage size", 0, storage.size());
    final HandlerRegistration hr1 = OtherTest.listenerTest(storage, level);

    Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {
      private int count = 0;
      @Override
      public boolean execute() {
        int storageLength = storage.size();
        try {
          switch (count){
            case 0:
              ArrayValueTest.putBooleanArrayValue(storage, ++storageLength);
              break;
            case 1:
              ArrayValueTest.putByteArrayValue(storage, ++storageLength);
              break;
            case 2:
              ArrayValueTest.putCharacterArrayValue(storage, ++storageLength);
              break;
            case 3:
              ArrayValueTest.putDoubleArrayValue(storage, ++storageLength);
              break;
            case 4:
              ArrayValueTest.putFloatArrayValue(storage, ++storageLength);
              break;
            case 5:
              ArrayValueTest.putIntegerArrayValue(storage, ++storageLength);
              break;
            case 6:
              ArrayValueTest.putLongArrayValue(storage, ++storageLength);
              break;
            case 7:
              ArrayValueTest.putShortArrayValue(storage, ++storageLength);
              break;
            case 8:
              ArrayValueTest.putStringArrayValue(storage, ++storageLength);
              break;
            default:
              hr1.removeHandler();
              if(StorageChangeEvent.Level.STRING.equals(level)){
                testStorage(storage, StorageChangeEvent.Level.OBJECT);
              }
              return false;
          }
        } catch (Exception e){
          e.printStackTrace();
        }
        count++;
        return true;
      }
    });


  }

}
