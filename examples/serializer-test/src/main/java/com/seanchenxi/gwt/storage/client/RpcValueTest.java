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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.service.TestService;
import com.seanchenxi.gwt.storage.client.service.TestServiceAsync;
import com.seanchenxi.gwt.storage.shared.RpcTestValue;

/**
 * Created by: Xi
 */
public class RpcValueTest extends StorageTestUnit {

  private final static TestServiceAsync testService = GWT.create(TestService.class);

  public static void putRpcTestValue(final StorageExt storage, final int expectedSize) throws SerializationException, StorageQuotaExceededException {
    final StorageKey<RpcTestValue> key = StorageKeyFactory.objectKey("putRpcTestValue");
    final AsyncCallback<RpcTestValue> asyncCallback = new AsyncCallback<RpcTestValue>() {
      @Override
      public void onFailure(Throwable e){
        StorageTestUnit.trace("error " + e.getClass().getName() + ": " + e.getMessage());
        GWT.log("error", e);
      }

      @Override
      public void onSuccess(RpcTestValue value){
        try{
          storage.put(key, value);
          assertEquals("putRpcTestValue - storage size", expectedSize, storage.size());
          assertTrue("putRpcTestValue - containsKey", storage.containsKey(key));
          assertEquals("putRpcTestValue - stored value", value, storage.get(key));
        }catch(Exception e){
          StorageTestUnit.trace("error " + e.getClass().getName() + ": " + e.getMessage());
          GWT.log("error", e);
        }

      }
    };
    testService.getRpcTestValue(asyncCallback);
  }

}
