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
 * @since 25 February 2014
 */
public class StorageKeyGetter extends StorageTestUnit {

  public <T extends Serializable> StorageKey<T> objectKey(String keyName) {
    return StorageKeyFactory.objectKey(keyName);
  }

  public StorageKey<short[]> shortArrayKey() {
    return StorageKeyFactory.shortArrayKey("shortArrayKey");
  }

  public StorageKey<Integer> intKey() {
    return StorageKeyFactory.intKey("intKey");
  }

  public StorageKey<boolean[]> boolArrayKey() {
    return StorageKeyFactory.boolArrayKey("boolArrayKey");
  }

  public StorageKey<byte[]> byteArrayKey() {
    return StorageKeyFactory.byteArrayKey("byteArrayKey");
  }

  public StorageKey<String[]> stringArrayKey() {
    return StorageKeyFactory.stringArrayKey("stringArrayKey");
  }

  public StorageKey<String> stringKey() {
    return StorageKeyFactory.stringKey("stringKey");
  }

  public StorageKey<char[]> charArrayKey() {
    return StorageKeyFactory.charArrayKey("charArrayKey");
  }

  public StorageKey<Float> floatKey() {
    return StorageKeyFactory.floatKey("floatKey");
  }

  public StorageKey<Integer[]> boxedIntArrayKey() {
    return StorageKeyFactory.boxedIntArrayKey("boxedIntArrayKey");
  }

  public StorageKey<Short[]> boxedShortArrayKey() {
    return StorageKeyFactory.boxedShortArrayKey("boxedShortArrayKey");
  }

  public StorageKey<Float[]> boxedFloatArrayKey() {
    return StorageKeyFactory.boxedFloatArrayKey("boxedFloatArrayKey");
  }

  public StorageKey<Long[]> boxedLongArrayKey() {
    return StorageKeyFactory.boxedLongArrayKey("boxedLongArrayKey");
  }

  public StorageKey<Character[]> boxedCharArrayKey() {
    return StorageKeyFactory.boxedCharArrayKey("boxedCharArrayKey");
  }

  public StorageKey<float[]> floatArrayKey() {
    return StorageKeyFactory.floatArrayKey("floatArrayKey");
  }

  public StorageKey<int[]> intArrayKey() {
    return StorageKeyFactory.intArrayKey("intArrayKey");
  }

  public StorageKey<Boolean> boolKey() {
    return StorageKeyFactory.boolKey("boolKey");
  }

  public StorageKey<long[]> longArrayKey() {
    return StorageKeyFactory.longArrayKey("longArrayKey");
  }

  public StorageKey<Byte> byteKey() {
    return StorageKeyFactory.byteKey("byteKey");
  }

  public StorageKey<Double> doubleKey() {
    return StorageKeyFactory.doubleKey("doubleKey");
  }

  public StorageKey<Short> shortKey() {
    return StorageKeyFactory.shortKey("shortKey");
  }

  public StorageKey<Long> longKey() {
    return StorageKeyFactory.longKey("longKey");
  }

  public StorageKey<double[]> doubleArrayKey() {
    return StorageKeyFactory.doubleArrayKey("doubleArrayKey");
  }

  public StorageKey<Boolean[]> boxedBoolArrayKey() {
    return StorageKeyFactory.boxedBoolArrayKey("boxedBoolArrayKey");
  }

  public StorageKey<Double[]> boxedDoubleArrayKey() {
    return StorageKeyFactory.boxedDoubleArrayKey("boxedDoubleArrayKey");
  }

  public StorageKey<Byte[]> boxedByteArrayKey() {
    return StorageKeyFactory.boxedByteArrayKey("boxedByteArrayKey");
  }

  public StorageKey<Character> charKey() {
    return StorageKeyFactory.charKey("charKey");
  }
}
