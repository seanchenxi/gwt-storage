/*
 * Copyright 2013 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seanchenxi.gwt.storage.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.gwt.storage.client.serializer.StorageSerializer;
import com.seanchenxi.gwt.storage.client.value.GenericTestValue;
import com.seanchenxi.gwt.storage.client.value.TestValue;

/**
 * Created by: Xi
 */
public class StorageTest implements EntryPoint {

  StorageSerializer serializer = GWT.create(StorageSerializer.class);
  StorageKeyGetter KEY_GETTER = GWT.create(StorageKeyGetter.class);

  public void onModuleLoad() {
    testStorage(StorageExt.getLocalStorage(), new AsyncCallback<Boolean>() {
      @Override
      public void onSuccess(Boolean result) {
        if(result)
          testStorage(StorageExt.getSessionStorage(), null);
      }

      @Override
      public void onFailure(Throwable caught) {
      }
    });
  }

  private void testStorage(final StorageExt storage, final AsyncCallback<Boolean> callback) {
    StorageTestUnit.start(storage);
    scheduleSimpleValueTests();
    scheduleArrayValueTests();

    final HandlerRegistration hr1 = OtherTest.listenerTest(storage, StorageChangeEvent.Level.STRING);
    final Iterator<Scheduler.RepeatingCommand> iterator = new ArrayList<Scheduler.RepeatingCommand>(StorageTestUnit.getTests()).iterator();
    Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {
      @Override
      public boolean execute() {
        Scheduler.RepeatingCommand next = iterator.next();
        try {
          boolean isOK = next != null && next.execute();
          if(!isOK){
            callback.onSuccess(false);
          }
          return isOK && iterator.hasNext();
        } catch (Exception e){
          callback.onFailure(e);
          return false;
        } finally {
          if(next != null)
            iterator.remove();
          if(!iterator.hasNext()){
            boolean isOK = StorageTestUnit.end();
            hr1.removeHandler();
            if(callback != null) callback.onSuccess(isOK);
          }
        }
      }
    });
  }

  private void scheduleSimpleValueTests() {
    StorageTestUnit.testPutValue(KEY_GETTER.intKey(), Integer.MAX_VALUE, 25);
    StorageTestUnit.testPutValue(KEY_GETTER.stringKey(), "StringValue", "");
    StorageTestUnit.testPutValue(KEY_GETTER.boolKey(), Boolean.FALSE, true);
    StorageTestUnit.testPutValue(KEY_GETTER.byteKey(), Byte.MAX_VALUE, Byte.parseByte("01"));
    StorageTestUnit.testPutValue(KEY_GETTER.doubleKey(), 25.58D, 32.45d);
    StorageTestUnit.testPutValue(KEY_GETTER.charKey(), Character.MAX_VALUE, Character.MIN_VALUE);
    StorageTestUnit.testPutValue(KEY_GETTER.floatKey(), 25.58F, 32.45f);
    StorageTestUnit.testPutValue(KEY_GETTER.longKey(), 12345L, 23456l);
    StorageTestUnit.testPutValue(KEY_GETTER.shortKey(), Short.MAX_VALUE, Short.MIN_VALUE);

    TestValue test1 = new TestValue("hello");
    TestValue test2 = new TestValue("hello2");
    StorageTestUnit.testPutValue(KEY_GETTER.objectKey("objectKey"), test1, test2);
    StorageTestUnit.testPutValue(KEY_GETTER.objectKey("genericObjectKey"), new GenericTestValue<TestValue>(test1), new GenericTestValue<TestValue>(test2));
  }

  private void scheduleArrayValueTests() {
    StorageTestUnit.testPutValue(KEY_GETTER.boxedIntArrayKey(), KEY_GETTER.intArrayKey(), new Integer[]{Integer.MAX_VALUE,Integer.MIN_VALUE}, new int[]{39023948,234234});
    StorageTestUnit.testPutValue(KEY_GETTER.stringArrayKey(), new String[]{"StringValue", "StringValue"}, new String[]{"StringValue2", "StringValue2"});
    StorageTestUnit.testPutValue(KEY_GETTER.boxedBoolArrayKey(), KEY_GETTER.boolArrayKey(), new Boolean[]{Boolean.FALSE, Boolean.TRUE}, new boolean[]{true,false});
    StorageTestUnit.testPutValue(KEY_GETTER.boxedByteArrayKey(), KEY_GETTER.byteArrayKey(), new Byte[]{Byte.MAX_VALUE, Byte.MIN_VALUE}, new byte[]{Byte.MIN_VALUE, Byte.MIN_VALUE});
    StorageTestUnit.testPutValue(KEY_GETTER.boxedDoubleArrayKey(), KEY_GETTER.doubleArrayKey(),new Double[]{25.58D,32.466d}, new double[]{32.45d,43.345d});
    StorageTestUnit.testPutValue(KEY_GETTER.boxedCharArrayKey(), KEY_GETTER.charArrayKey(), new Character[]{Character.MAX_VALUE, Character.MAX_VALUE}, new char[]{Character.MIN_VALUE, Character.MIN_VALUE});
    StorageTestUnit.testPutValue(KEY_GETTER.boxedFloatArrayKey(), KEY_GETTER.floatArrayKey(), new Float[]{25.58F, 65.45F}, new float[]{32.45f, 98.99f});
    StorageTestUnit.testPutValue(KEY_GETTER.boxedLongArrayKey(), KEY_GETTER.longArrayKey(), new Long[]{12345L,35234523453245L}, new long[]{123412341234l, 9087098709798l});
    StorageTestUnit.testPutValue(KEY_GETTER.boxedShortArrayKey(), KEY_GETTER.shortArrayKey(), new Short[]{Short.MAX_VALUE, Short.MIN_VALUE}, new short[]{Short.MIN_VALUE, Short.MAX_VALUE});

    TestValue hello1 = new TestValue("hello1");
    TestValue hello2 = new TestValue("hello2");
    final TestValue[] values = new TestValue[]{hello2, hello1};
    final TestValue[] values2 = new TestValue[]{hello1, hello2};
    StorageTestUnit.testPutValue(KEY_GETTER.objectKey("objectArrayKey"), values, values2);
    final GenericTestValue<TestValue>[] genericValues = new GenericTestValue[]{new GenericTestValue<TestValue>(hello2), new GenericTestValue<TestValue>(hello1)};
    final GenericTestValue<TestValue>[] genericValues2 = new GenericTestValue[]{new GenericTestValue<TestValue>(hello1), new GenericTestValue<TestValue>(hello2)};
    StorageTestUnit.testPutValue(KEY_GETTER.objectKey("genericObjectArrayKey"), genericValues, genericValues2);
  }

  private void testStorageRpcValue(final StorageExt storage, final StorageChangeEvent.Level level){
    assert storage != null;
    storage.clear();
    StorageTestUnit.assertEquals("testStorageRpcValue - storage size", 0, storage.size());
    final HandlerRegistration hr1 = OtherTest.listenerTest(storage, level);

    final AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
      @Override
      public void onFailure(Throwable caught) {
        StorageTestUnit.trace("<b>KO</b>", true);
        StorageTestUnit.traceEmptyLine();
        onRpcTesEnd(hr1, storage, level);
      }

      @Override
      public void onSuccess(Boolean result) {
        StorageTestUnit.trace("<b>OK</b>", false);
        StorageTestUnit.traceEmptyLine();
        onRpcTesEnd(hr1, storage, level);
      }
    };
    
    int storageLength = storage.size();
    try {
      RpcValueTest.putRpcTestValue(storage, ++storageLength, callback);
    }catch (Exception e){
      GWT.log("error", e);
    }

  }
  
  public void onRpcTesEnd(final HandlerRegistration hr1, final StorageExt storage, final StorageChangeEvent.Level level){
    hr1.removeHandler();
    if(StorageChangeEvent.Level.STRING.equals(level)){
      //testStorage(storage, StorageChangeEvent.Level.OBJECT);
    }
  }

}
