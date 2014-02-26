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
public class KeyFactoryGetter implements StorageKeyGetter {

  @Override
  public <T extends Serializable> StorageKey<T> objectKey(String keyName) {
    return StorageKeyFactory.objectKey(keyName);
  }

  @Override
  public StorageKey<short[]> shortArrayKey() {
    return StorageKeyFactory.shortArrayKey("shortArrayKey");
  }

  @Override
  public StorageKey<Integer> intKey() {
    return StorageKeyFactory.intKey("intKey");
  }

  @Override
  public StorageKey<boolean[]> boolArrayKey() {
    return StorageKeyFactory.boolArrayKey("boolArrayKey");
  }

  @Override
  public StorageKey<byte[]> byteArrayKey() {
    return StorageKeyFactory.byteArrayKey("byteArrayKey");
  }

  @Override
  public StorageKey<String[]> stringArrayKey() {
    return StorageKeyFactory.stringArrayKey("stringArrayKey");
  }

  @Override
  public StorageKey<String> stringKey() {
    return StorageKeyFactory.stringKey("stringKey");
  }

  @Override
  public StorageKey<char[]> charArrayKey() {
    return StorageKeyFactory.charArrayKey("charArrayKey");
  }

  @Override
  public StorageKey<Float> floatKey() {
    return StorageKeyFactory.floatKey("floatKey");
  }

  @Override
  public StorageKey<Integer[]> boxedIntArrayKey() {
    return StorageKeyFactory.boxedIntArrayKey("boxedIntArrayKey");
  }

  @Override
  public StorageKey<Short[]> boxedShortArrayKey() {
    return StorageKeyFactory.boxedShortArrayKey("boxedShortArrayKey");
  }

  @Override
  public StorageKey<Float[]> boxedFloatArrayKey() {
    return StorageKeyFactory.boxedFloatArrayKey("boxedFloatArrayKey");
  }

  @Override
  public StorageKey<Long[]> boxedLongArrayKey() {
    return StorageKeyFactory.boxedLongArrayKey("boxedLongArrayKey");
  }

  @Override
  public StorageKey<Character[]> boxedCharArrayKey() {
    return StorageKeyFactory.boxedCharArrayKey("boxedCharArrayKey");
  }

  @Override
  public StorageKey<float[]> floatArrayKey() {
    return StorageKeyFactory.floatArrayKey("floatArrayKey");
  }

  @Override
  public StorageKey<int[]> intArrayKey() {
    return StorageKeyFactory.intArrayKey("intArrayKey");
  }

  @Override
  public StorageKey<Boolean> boolKey() {
    return StorageKeyFactory.boolKey("boolKey");
  }

  @Override
  public StorageKey<long[]> longArrayKey() {
    return StorageKeyFactory.longArrayKey("longArrayKey");
  }

  @Override
  public StorageKey<Byte> byteKey() {
    return StorageKeyFactory.byteKey("byteKey");
  }

  @Override
  public StorageKey<Double> doubleKey() {
    return StorageKeyFactory.doubleKey("doubleKey");
  }

  @Override
  public StorageKey<Short> shortKey() {
    return StorageKeyFactory.shortKey("shortKey");
  }

  @Override
  public StorageKey<Long> longKey() {
    return StorageKeyFactory.longKey("longKey");
  }

  @Override
  public StorageKey<double[]> doubleArrayKey() {
    return StorageKeyFactory.doubleArrayKey("doubleArrayKey");
  }

  @Override
  public StorageKey<Boolean[]> boxedBoolArrayKey() {
    return StorageKeyFactory.boxedBoolArrayKey("boxedBoolArrayKey");
  }

  @Override
  public StorageKey<Double[]> boxedDoubleArrayKey() {
    return StorageKeyFactory.boxedDoubleArrayKey("boxedDoubleArrayKey");
  }

  @Override
  public StorageKey<Byte[]> boxedByteArrayKey() {
    return StorageKeyFactory.boxedByteArrayKey("boxedByteArrayKey");
  }

  @Override
  public StorageKey<Character> charKey() {
    return StorageKeyFactory.charKey("charKey");
  }
}
