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

import java.io.Serializable;

/**
 * @author <a href="mailto:chenxifr@gmail.com">Xi CHEN</a>
 * @since 26 February 2014
 */
public interface StorageKeyGetter {

  <T extends Serializable> StorageKey<T> objectKey(String keyName);

  StorageKey<short[]> shortArrayKey();

  StorageKey<Integer> intKey();

  StorageKey<boolean[]> boolArrayKey();

  StorageKey<byte[]> byteArrayKey();

  StorageKey<String[]> stringArrayKey();

  StorageKey<String> stringKey();

  StorageKey<char[]> charArrayKey();

  StorageKey<Float> floatKey();

  StorageKey<Integer[]> boxedIntArrayKey();

  StorageKey<Short[]> boxedShortArrayKey();

  StorageKey<Float[]> boxedFloatArrayKey();

  StorageKey<Long[]> boxedLongArrayKey();

  StorageKey<Character[]> boxedCharArrayKey();

  StorageKey<float[]> floatArrayKey();

  StorageKey<int[]> intArrayKey();

  StorageKey<Boolean> boolKey();

  StorageKey<long[]> longArrayKey();

  StorageKey<Byte> byteKey();

  StorageKey<Double> doubleKey();

  StorageKey<Short> shortKey();

  StorageKey<Long> longKey();

  StorageKey<double[]> doubleArrayKey();

  StorageKey<Boolean[]> boxedBoolArrayKey();

  StorageKey<Double[]> boxedDoubleArrayKey();

  StorageKey<Byte[]> boxedByteArrayKey();

  StorageKey<Character> charKey();
}
