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

import java.io.Serializable;

/**
 * Created by: Xi
 */
public class StorageKeyFactory {

  public static <T extends Serializable> StorageKey<T> objectKey(String keyName){
    return new StorageKey<T>(keyName, Serializable.class);
  }

  public static StorageKey<Boolean> boolKey(String keyName){
    return new StorageKey<Boolean>(keyName, Boolean.class);
  }

  public static StorageKey<Byte> byteKey(String keyName){
    return new StorageKey<Byte>(keyName, Byte.class);
  }

  public static StorageKey<Character> charKey(String keyName){
    return new StorageKey<Character>(keyName, Character.class);
  }

  public static StorageKey<Double> doubleKey(String keyName){
    return new StorageKey<Double>(keyName, Double.class);
  }

  public static StorageKey<Float> floatKey(String keyName){
    return new StorageKey<Float>(keyName, Float.class);
  }

  public static StorageKey<Integer> intKey(String keyName){
    return new StorageKey<Integer>(keyName, Integer.class);
  }

  public static StorageKey<Long> longKey(String keyName){
    return new StorageKey<Long>(keyName, Long.class);
  }

  public static StorageKey<Short> shortKey(String keyName){
    return new StorageKey<Short>(keyName, Short.class);
  }

  public static StorageKey<String> stringKey(String keyName){
    return new StorageKey<String>(keyName, String.class);
  }

  public static StorageKey<Boolean[]> boxedBoolArrayKey(String keyName){
    return new StorageKey<Boolean[]>(keyName, Boolean[].class);
  }

  public static StorageKey<Byte[]> boxedByteArrayKey(String keyName){
    return new StorageKey<Byte[]>(keyName, Byte[].class);
  }

  public static StorageKey<Character[]> boxedCharArrayKey(String keyName){
    return new StorageKey<Character[]>(keyName, Character[].class);
  }

  public static StorageKey<Double[]> boxedDoubleArrayKey(String keyName){
    return new StorageKey<Double[]>(keyName, Double[].class);
  }

  public static StorageKey<Float[]> boxedFloatArrayKey(String keyName){
    return new StorageKey<Float[]>(keyName, Float[].class);
  }

  public static StorageKey<Integer[]> boxedIntArrayKey(String keyName){
    return new StorageKey<Integer[]>(keyName, Integer[].class);
  }

  public static StorageKey<Long[]> boxedLongArrayKey(String keyName){
    return new StorageKey<Long[]>(keyName, Long[].class);
  }

  public static StorageKey<Short[]> boxedShortArrayKey(String keyName){
    return new StorageKey<Short[]>(keyName, Short[].class);
  }

  public static StorageKey<String[]> stringArrayKey(String keyName){
    return new StorageKey<String[]>(keyName, String[].class);
  }

  public static StorageKey<boolean[]> boolArrayKey(String keyName){
    return new StorageKey<boolean[]>(keyName, boolean[].class);
  }

  public static StorageKey<byte[]> byteArrayKey(String keyName){
    return new StorageKey<byte[]>(keyName, byte[].class);
  }

  public static StorageKey<char[]> charArrayKey(String keyName){
    return new StorageKey<char[]>(keyName, char[].class);
  }

  public static StorageKey<double[]> doubleArrayKey(String keyName){
    return new StorageKey<double[]>(keyName, double[].class);
  }

  public static StorageKey<float[]> floatArrayKey(String keyName){
    return new StorageKey<float[]>(keyName, float[].class);
  }

  public static StorageKey<int[]> intArrayKey(String keyName){
    return new StorageKey<int[]>(keyName, int[].class);
  }

  public static StorageKey<long[]> longArrayKey(String keyName){
    return new StorageKey<long[]>(keyName, long[].class);
  }

  public static StorageKey<short[]> shortArrayKey(String keyName){
    return new StorageKey<short[]>(keyName, short[].class);
  }


}





