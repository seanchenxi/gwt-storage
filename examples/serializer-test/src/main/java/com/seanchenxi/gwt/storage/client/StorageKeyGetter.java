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
