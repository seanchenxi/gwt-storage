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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.gwt.storage.client.serializer.StorageSerializer;
import com.seanchenxi.gwt.storage.client.service.TestService;
import com.seanchenxi.gwt.storage.client.service.TestServiceAsync;
import com.seanchenxi.gwt.storage.client.value.GenericTestValue;
import com.seanchenxi.gwt.storage.client.value.TestValue;
import com.seanchenxi.gwt.storage.shared.RpcTestMapKey;
import com.seanchenxi.gwt.storage.shared.RpcTestMapValue;
import com.seanchenxi.gwt.storage.shared.RpcTestValue;

/**
 * Created by: Xi
 */
public class StorageTest implements EntryPoint {

  private static final StorageSerializer OBJ_SERIALIZER = GWT.create(StorageSerializer.class);
  private static final TestServiceAsync TEST_SERVICE = GWT.create(TestService.class);
  private static final KeyFactoryGetter KG = new KeyFactoryGetter();
  private static KeyProviderGetter KP;

  public void onModuleLoad() {
    KP = GWT.create(KeyProviderGetter.class);

    testStorage(KP != null, StorageExt.getLocalStorage(), new AsyncCallback<Boolean>() {
      @Override
      public void onSuccess(Boolean result) {
        if(result){
          testStorage(KP != null, StorageExt.getSessionStorage(), new AsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
              if(result){
                testSerializer();
              }
            }

            @Override
            public void onFailure(Throwable caught) {
              GWT.log("getRpcTestValueList - onFailure", caught);
            }
          });
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        GWT.log("getRpcTestValueList - onFailure", caught);
      }
    });
  }

  private void testSerializer() {
    try {
      StorageTestUtil.traceEmptyLine();
      TestValue testObject1 = new TestValue("test");
      String test = OBJ_SERIALIZER.serialize(TestValue.class, testObject1);
      StorageTestUtil.trace(test, false);
      TestValue testObject2 = OBJ_SERIALIZER.deserialize(TestValue.class, test);
      StorageTestUtil.assertEquals("Serializer test", testObject1, testObject2);
    } catch (Exception e) {
      GWT.log("testSerializer - error", e);
    }
  }

  private void testStorage(boolean userKeyProvider, final StorageExt storage, final AsyncCallback<Boolean> callback) {
    StorageTestUtil.prepare(storage);

    scheduleSimpleValueTests(userKeyProvider);
    scheduleArrayValueTests(userKeyProvider);
    scheduleRpcValueTests(userKeyProvider, new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        doTest(storage, callback);
      }
    });
  }

  private void doTest(StorageExt storage, final AsyncCallback<Boolean> callback) {
    StorageTestUtil.start();
    final HandlerRegistration hr1 = OtherTest.listenerTest(storage, StorageChangeEvent.Level.STRING);
    final Iterator<Scheduler.RepeatingCommand> iterator = new ArrayList<>(StorageTestUtil.getTests()).iterator();
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
            boolean isOK = StorageTestUtil.end();
            hr1.removeHandler();
            if(callback != null) callback.onSuccess(isOK);
          }
        }
      }
    });
  }

  private void scheduleSimpleValueTests(boolean userKeyProvider) {
    StorageTestUtil.testPutValue(userKeyProvider? KP.intKey() : KG.intKey(), Integer.MAX_VALUE, 25);
    StorageTestUtil.testPutValue(userKeyProvider? KP.stringKey() : KG.stringKey(), "StringValue", "");
    StorageTestUtil.testPutValue(userKeyProvider? KP.boolKey() : KG.boolKey(), Boolean.FALSE, true);
    StorageTestUtil.testPutValue(userKeyProvider? KP.byteKey() : KG.byteKey(), Byte.MAX_VALUE, Byte.parseByte("01"));
    StorageTestUtil.testPutValue(userKeyProvider? KP.doubleKey() : KG.doubleKey(), 25.58D, 32.45d);
    StorageTestUtil.testPutValue(userKeyProvider? KP.charKey() : KG.charKey(), Character.MAX_VALUE, Character.MIN_VALUE);
    StorageTestUtil.testPutValue(userKeyProvider? KP.floatKey() : KG.floatKey(), 25.58F, 32.45f);
    StorageTestUtil.testPutValue(userKeyProvider? KP.longKey() : KG.longKey(), 12345L, 23456l);
    StorageTestUtil.testPutValue(userKeyProvider? KP.shortKey() : KG.shortKey(), Short.MAX_VALUE, Short.MIN_VALUE);

    TestValue test1 = new TestValue("hello");
    TestValue test2 = new TestValue("hello2");
    StorageKey<TestValue> key1 = userKeyProvider ? KP.testValueKey("objectKey") : StorageKeyFactory.<TestValue>serializableKey("objectKey");
    StorageKey<GenericTestValue<TestValue>> key2 = userKeyProvider ? KP.genericTestValueKey("genericObjectKey") : StorageKeyFactory.<GenericTestValue<TestValue>>isSerializableKey("genericObjectKey");
    StorageTestUtil.testPutValue(key1, test1, test2);
    StorageTestUtil.testPutValue(key2, new GenericTestValue<>(test1), new GenericTestValue<>(test2));
  }

  @SuppressWarnings("unchecked")
  private void scheduleArrayValueTests(boolean userKP) {
    StorageTestUtil.testPutValue(userKP ? KP.boxedIntArrayKey() : KG.boxedIntArrayKey(), userKP ? KP.intArrayKey() : KG.intArrayKey(), new Integer[]{Integer.MAX_VALUE, Integer.MIN_VALUE}, new int[]{39023948, 234234});
    StorageTestUtil.testPutValue(userKP ? KP.stringArrayKey() : KG.stringArrayKey(), new String[]{"StringValue", "StringValue"}, new String[]{"StringValue2", "StringValue2"});
    StorageTestUtil.testPutValue(userKP ? KP.boxedBoolArrayKey() : KG.boxedBoolArrayKey(), userKP ? KP.boolArrayKey() : KG.boolArrayKey(), new Boolean[]{Boolean.FALSE, Boolean.TRUE}, new boolean[]{true, false});
    StorageTestUtil.testPutValue(userKP ? KP.boxedByteArrayKey() : KG.boxedByteArrayKey(), userKP ? KP.byteArrayKey() : KG.byteArrayKey(), new Byte[]{Byte.MAX_VALUE, Byte.MIN_VALUE}, new byte[]{Byte.MIN_VALUE, Byte.MIN_VALUE});
    StorageTestUtil.testPutValue(userKP ? KP.boxedDoubleArrayKey() : KG.boxedDoubleArrayKey(), userKP ? KP.doubleArrayKey() : KG.doubleArrayKey(), new Double[]{25.58D, 32.466d}, new double[]{32.45d, 43.345d});
    StorageTestUtil.testPutValue(userKP ? KP.boxedCharArrayKey() : KG.boxedCharArrayKey(), userKP ? KP.charArrayKey() : KG.charArrayKey(), new Character[]{Character.MAX_VALUE, Character.MAX_VALUE}, new char[]{Character.MIN_VALUE, Character.MIN_VALUE});
    StorageTestUtil.testPutValue(userKP ? KP.boxedFloatArrayKey() : KG.boxedFloatArrayKey(), userKP ? KP.floatArrayKey() : KG.floatArrayKey(), new Float[]{25.58F, 65.45F}, new float[]{32.45f, 98.99f});
    StorageTestUtil.testPutValue(userKP ? KP.boxedLongArrayKey() : KG.boxedLongArrayKey(), userKP ? KP.longArrayKey() : KG.longArrayKey(), new Long[]{12345L, 35234523453245L}, new long[]{123412341234l, 9087098709798l});
    StorageTestUtil.testPutValue(userKP ? KP.boxedShortArrayKey() : KG.boxedShortArrayKey(), userKP ? KP.shortArrayKey() : KG.shortArrayKey(), new Short[]{Short.MAX_VALUE, Short.MIN_VALUE}, new short[]{Short.MIN_VALUE, Short.MAX_VALUE});

    TestValue hello1 = new TestValue("hello1");
    TestValue hello2 = new TestValue("hello2");
    final TestValue[] values = new TestValue[]{hello2, hello1};
    final TestValue[] values2 = new TestValue[]{hello1, hello2};
    StorageTestUtil.testPutValue(userKP ? KP.testValueArrayKey() : KG.objectKey("objectArrayKey"), values, values2);
    final GenericTestValue<TestValue>[] genericValues = new GenericTestValue[]{new GenericTestValue<>(hello2), new GenericTestValue<>(hello1)};
    final GenericTestValue<TestValue>[] genericValues2 = new GenericTestValue[]{new GenericTestValue<>(hello1), new GenericTestValue<>(hello2)};
    StorageTestUtil.testPutValue(userKP ? KP.genericTestValueArrayKey() : KG.objectKey("genericObjectArrayKey"), genericValues, genericValues2);
  }

  private void scheduleRpcValueTests(final boolean userKP, final Scheduler.ScheduledCommand command){
    TEST_SERVICE.getRpcTestValue(new AsyncCallback<RpcTestValue>() {
      @Override
      public void onFailure(Throwable caught) {
        GWT.log("getRpcTestValue - onFailure", caught);
      }

      @Override
      public void onSuccess(RpcTestValue result) {
        StorageTestUtil.testPutValue(userKP ? KP.rpcTestValueKey() : KG.objectKey("getRpcTestValue"), result, new RpcTestValue());

        TEST_SERVICE.getRpcTestValueList(new AsyncCallback<List<RpcTestValue>>() {
          @Override
          public void onFailure(Throwable caught) {
            GWT.log("getRpcTestValueList - onFailure", caught);
          }

          @Override
          public void onSuccess(List<RpcTestValue> result) {
            StorageTestUtil.testPutValue(userKP ? KP.rpcTestValueListKey() : KG.objectKey("getRpcTestValueList"), new ArrayList<>(result), new ArrayList<RpcTestValue>());

            TEST_SERVICE.getRpcTestValueStringMap(new AsyncCallback<Map<RpcTestMapKey, RpcTestMapValue>>() {
              @Override
              public void onFailure(Throwable caught) {
                GWT.log("getRpcTestValueStringMap - onFailure", caught);
              }

              @Override
              public void onSuccess(Map<RpcTestMapKey, RpcTestMapValue> result) {
                StorageTestUtil.testPutValue(userKP ? KP.rpcTestValueStringMapKey() : KG.objectKey("getRpcTestValueStringMap"), new HashMap<>(result), new HashMap<RpcTestMapKey, RpcTestMapValue>());

                command.execute();
              }
            });
          }
        });
      }
    });
  }

}
