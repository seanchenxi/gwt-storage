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

package com.seanchenxi.gwt.storage.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.seanchenxi.gwt.storage.client.value.GenericTestValue;
import com.seanchenxi.gwt.storage.client.value.TestValue;
import com.seanchenxi.gwt.storage.shared.RpcTestMapKey;
import com.seanchenxi.gwt.storage.shared.RpcTestMapValue;
import com.seanchenxi.gwt.storage.shared.RpcTestValue;

import java.util.List;
import java.util.Map;

/**
 * Created by: Xi
 */
public interface TestServiceAsync {

  void getRpcTestValue(AsyncCallback<RpcTestValue> async);

  void getRpcTestValueList(AsyncCallback<List<RpcTestValue>> async);

  void getRpcTestValueStringMap(AsyncCallback<Map<RpcTestMapKey, RpcTestMapValue>> async);

  void testDeserialization(TestValue className, String serialized, TestValue stored, AsyncCallback<TestValue> async);

  void testGenericDeserialization(GenericTestValue<TestValue> value1, String serialized, AsyncCallback<GenericTestValue<TestValue>> async);

  void testSerialization(TestValue value, String serialized, TestValue stored, AsyncCallback<String> async);

  void testGenericSerialization(GenericTestValue<TestValue> value1, String serialized, AsyncCallback<String> async);
}
