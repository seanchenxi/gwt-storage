/*
 * Copyright 2014 Xi CHEN
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

import com.seanchenxi.gwt.storage.client.value.GenericTestValue;
import com.seanchenxi.gwt.storage.client.value.TestValue;
import com.seanchenxi.gwt.storage.shared.RpcTestMapKey;
import com.seanchenxi.gwt.storage.shared.RpcTestMapValue;
import com.seanchenxi.gwt.storage.shared.RpcTestValue;

public interface KeyProviderGetter extends StorageKeyProvider {

  @Key(prefix = "prefix", suffix = "suffix")
  StorageKey<Boolean> boolKey();

  @Key(prefix = "prefix", value = "byteKey00", suffix = "suffix")
  StorageKey<Byte> byteKey();

  @Key(value = "byteKey00", suffix = "suffix")
  StorageKey<Character> charKey();

  @Key(prefix = "prefix", value = "byteKey00")
  StorageKey<Double> doubleKey();

  @Key(prefix = "prefix")
  StorageKey<Float> floatKey();

  @Key(suffix = "suffix")
  StorageKey<Integer> intKey();

  StorageKey<Long> longKey();

  StorageKey<Short> shortKey();

  StorageKey<String> stringKey();

  StorageKey<Boolean> boolKey(String keyName);

  StorageKey<Byte> byteKey(String keyName);

  StorageKey<Character> charKey(String keyName);

  StorageKey<Double> doubleKey(String keyName);

  StorageKey<Float> floatKey(String keyName);

  @Key(value = "shortKey1", suffix = "suffix")
  StorageKey<Integer> intKey(String keyName);

  @Key(prefix = "prefix", value = "shortKey1")
  StorageKey<Long> longKey(String keyName);

  @Key(prefix = "prefix", value = "shortKey1", suffix = "suffix")
  StorageKey<Short> shortKey(String keyName);

  @Key(prefix = "prefix", suffix = "suffix")
  StorageKey<String> stringKey(String keyName);

  @Key(suffix = "suffix")
  StorageKey<TestValue> testValueKey(String keyName);

  @Key(prefix = "prefix")
  StorageKey<GenericTestValue<TestValue>> genericTestValueKey(String keyName);

  StorageKey<short[]> shortArrayKey();

  StorageKey<boolean[]> boolArrayKey();

  StorageKey<byte[]> byteArrayKey();

  StorageKey<String[]> stringArrayKey();

  StorageKey<char[]> charArrayKey();

  StorageKey<Integer[]> boxedIntArrayKey();

  StorageKey<Short[]> boxedShortArrayKey();

  StorageKey<Float[]> boxedFloatArrayKey();

  StorageKey<Long[]> boxedLongArrayKey();

  StorageKey<Character[]> boxedCharArrayKey();

  StorageKey<float[]> floatArrayKey();

  StorageKey<int[]> intArrayKey();

  StorageKey<long[]> longArrayKey();

  StorageKey<double[]> doubleArrayKey();

  StorageKey<Boolean[]> boxedBoolArrayKey();

  StorageKey<Double[]> boxedDoubleArrayKey();

  StorageKey<Byte[]> boxedByteArrayKey();

  StorageKey<TestValue[]> testValueArrayKey();

  StorageKey<GenericTestValue<TestValue>[]> genericTestValueArrayKey();

  StorageKey<RpcTestValue> rpcTestValueKey();

  StorageKey<ArrayList<RpcTestValue>> rpcTestValueListKey();

  StorageKey<HashMap<RpcTestMapKey, RpcTestMapValue>> rpcTestValueStringMapKey();
}
