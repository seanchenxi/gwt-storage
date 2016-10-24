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

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Storage Key Factory
 *
 * <p>
 *   <span style="color:red;">
 *     Choose type correspond method for all primitive type values' key, <br/>
 *     Use {@link #serializableKey(String)} for Serializable object type
 *     Use {@link #isSerializableKey(String)} for IsSerializable object type
 *   </span>
 * </p>
 *
 */
public class StorageKeyFactory {

  /**
   * Returns non-primitive type value's storage key.
   *
   * @param keyName name of storage key
   * @param <T> type of value
   * @return non-primitive type value's storage key
   * @deprecated use {@link #isSerializableKey(String)} or {@link #serializableKey(String)} instead
   */
  @Deprecated
  public static <T> StorageKey<T> objectKey(String keyName){
    return new StorageKey<>(keyName, Object.class);
  }

  /**
   * Returns IsSerializable type value's storage key.
   *
   * @param keyName name of storage key
   * @param <T> type of value
   * @return IsSerializable type value's storage key
   */
  public static <T extends IsSerializable> StorageKey<T> isSerializableKey(String keyName){
      return new StorageKey<>(keyName, IsSerializable.class);
  }

  /**
   * Returns Serializable type value's storage key.
   *
   * @param keyName name of storage key
   * @param <T> type of value
   * @return Serializable type value's storage key
   */
  public static <T extends Serializable> StorageKey<T> serializableKey(String keyName){
      return new StorageKey<>(keyName, Serializable.class);
  }

  public static StorageKey<Boolean> boolKey(String keyName){
    return new StorageKey<>(keyName, Boolean.class);
  }

  public static StorageKey<Byte> byteKey(String keyName){
    return new StorageKey<>(keyName, Byte.class);
  }

  public static StorageKey<Character> charKey(String keyName){
    return new StorageKey<Character>(keyName, Character.class);
  }

  public static StorageKey<Double> doubleKey(String keyName){
    return new StorageKey<>(keyName, Double.class);
  }

  public static StorageKey<Float> floatKey(String keyName){
    return new StorageKey<>(keyName, Float.class);
  }

  public static StorageKey<Integer> intKey(String keyName){
    return new StorageKey<>(keyName, Integer.class);
  }

  public static StorageKey<Long> longKey(String keyName){
    return new StorageKey<>(keyName, Long.class);
  }

  public static StorageKey<Short> shortKey(String keyName){
    return new StorageKey<>(keyName, Short.class);
  }

  public static StorageKey<String> stringKey(String keyName){
    return new StorageKey<>(keyName, String.class);
  }

  public static StorageKey<Boolean[]> boxedBoolArrayKey(String keyName){
    return new StorageKey<>(keyName, Boolean[].class);
  }

  public static StorageKey<Byte[]> boxedByteArrayKey(String keyName){
    return new StorageKey<>(keyName, Byte[].class);
  }

  public static StorageKey<Character[]> boxedCharArrayKey(String keyName){
    return new StorageKey<>(keyName, Character[].class);
  }

  public static StorageKey<Double[]> boxedDoubleArrayKey(String keyName){
    return new StorageKey<>(keyName, Double[].class);
  }

  public static StorageKey<Float[]> boxedFloatArrayKey(String keyName){
    return new StorageKey<>(keyName, Float[].class);
  }

  public static StorageKey<Integer[]> boxedIntArrayKey(String keyName){
    return new StorageKey<>(keyName, Integer[].class);
  }

  public static StorageKey<Long[]> boxedLongArrayKey(String keyName){
    return new StorageKey<>(keyName, Long[].class);
  }

  public static StorageKey<Short[]> boxedShortArrayKey(String keyName){
    return new StorageKey<>(keyName, Short[].class);
  }

  public static StorageKey<String[]> stringArrayKey(String keyName){
    return new StorageKey<>(keyName, String[].class);
  }

  public static StorageKey<boolean[]> boolArrayKey(String keyName){
    return new StorageKey<>(keyName, boolean[].class);
  }

  public static StorageKey<byte[]> byteArrayKey(String keyName){
    return new StorageKey<>(keyName, byte[].class);
  }

  public static StorageKey<char[]> charArrayKey(String keyName){
    return new StorageKey<>(keyName, char[].class);
  }

  public static StorageKey<double[]> doubleArrayKey(String keyName){
    return new StorageKey<>(keyName, double[].class);
  }

  public static StorageKey<float[]> floatArrayKey(String keyName){
    return new StorageKey<>(keyName, float[].class);
  }

  public static StorageKey<int[]> intArrayKey(String keyName){
    return new StorageKey<>(keyName, int[].class);
  }

  public static StorageKey<long[]> longArrayKey(String keyName){
    return new StorageKey<>(keyName, long[].class);
  }

  public static StorageKey<short[]> shortArrayKey(String keyName){
    return new StorageKey<>(keyName, short[].class);
  }


}





