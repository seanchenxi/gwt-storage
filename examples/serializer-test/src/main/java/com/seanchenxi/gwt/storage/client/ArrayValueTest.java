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

import com.google.gwt.user.client.rpc.SerializationException;

public class ArrayValueTest extends StorageTestUnit {

  public static void putIntegerArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Integer[]> key = StorageKeyFactory.boxedIntArrayKey("putIntegerArrayValue");
    final Integer[] value = new Integer[]{Integer.MAX_VALUE,Integer.MIN_VALUE};
    storage.put(key, value);
    assertEquals("putIntegerArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putIntegerArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putIntegerArrayValue - stored value", value, storage.get(key));

    StorageKey<int[]> key2 = StorageKeyFactory.intArrayKey("putIntegerArrayValue");
    final int[] value2 = new int[]{39023948,234234};
    storage.put(key2, value2);
    assertEquals("putIntegerArrayValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putIntegerArrayValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putStringArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<String[]> key = StorageKeyFactory.stringArrayKey("putStringArrayValue");
    final String[] value = new String[]{"StringValue", "StringValue"};
    storage.put(key, value);
    assertEquals("putStringArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putStringArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putStringArrayValue - stored value", value, storage.get(key));

    StorageKey<String[]> key2 = StorageKeyFactory.stringArrayKey("putStringArrayValue");
    final String[] value2 = new String[]{"StringValue2", "StringValue2"};
    storage.put(key2, value);
    assertEquals("putStringArrayValue - storage size", expectedSize, storage.size());
    assertEquals("putStringArrayValue - stored value", value2, storage.get(key2));
  }

  public static void putBooleanArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Boolean[]> key = StorageKeyFactory.boxedBoolArrayKey("putBooleanArrayValue");
    final Boolean[] value = new Boolean[]{false, true};
    storage.put(key, value);
    assertEquals("putBooleanArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putBooleanArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putBooleanArrayValue - stored value", value, storage.get(key));

    StorageKey<boolean[]> key2 = StorageKeyFactory.boolArrayKey("putBooleanArrayValue");
    final boolean[] value2 = new boolean[]{true,false};
    storage.put(key2, value2);
    assertEquals("putBooleanArrayValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putBooleanArrayValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putByteArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Byte[]> key = StorageKeyFactory.boxedByteArrayKey("putByteArrayValue");
    final Byte[] value = new Byte[]{Byte.MAX_VALUE, Byte.MIN_VALUE};
    storage.put(key, value);
    assertEquals("putByteArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putByteArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putByteArrayValue - stored value", value, storage.get(key));

    StorageKey<byte[]> key2 = StorageKeyFactory.byteArrayKey("putByteArrayValue");
    final byte[] value2 = new byte[]{Byte.MIN_VALUE, Byte.MIN_VALUE};
    storage.put(key2, value2);
    assertEquals("putByteArrayValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putByteArrayValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putDoubleArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Double[]> key = StorageKeyFactory.boxedDoubleArrayKey("putDoubleArrayValue");
    final Double[] value = new Double[]{25.58D,32.466d};
    storage.put(key, value);
    assertEquals("putDoubleArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putDoubleArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putDoubleArrayValue - stored value", value, storage.get(key));

    StorageKey<double[]> key2 = StorageKeyFactory.doubleArrayKey("putDoubleArrayValue");
    final double[] value2 = new double[]{32.45d,43.345d};
    storage.put(key2, value2);
    assertEquals("putDoubleArraValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putDoubleArraValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putCharacterArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Character[]> key = StorageKeyFactory.boxedCharArrayKey("putCharacterArrayValue");
    final Character[] value = new Character[]{Character.MAX_VALUE, Character.MAX_VALUE};
    storage.put(key, value);
    assertEquals("putCharacterArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putCharacterArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putCharacterArrayValue - stored value", value, storage.get(key));

    StorageKey<char[]> key2 = StorageKeyFactory.charArrayKey("putCharacterArrayValue");
    final char[] value2 = new char[]{Character.MIN_VALUE, Character.MIN_VALUE};
    storage.put(key2, value2);
    assertEquals("putCharacterArrayValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putCharacterArrayValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putFloatArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Float[]> key = StorageKeyFactory.boxedFloatArrayKey("putFloatArrayValue");
    final Float[] value = new Float[]{25.58F, 65.45F};
    storage.put(key, value);
    assertEquals("putFloatArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putFloatArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putFloatArrayValue - stored value", value, storage.get(key));

    StorageKey<float[]> key2 = StorageKeyFactory.floatArrayKey("putFloatArrayValue");
    final float[] value2 = new float[]{32.45f, 98.99f};
    storage.put(key2, value2);
    assertEquals("putFloatArrayValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putFloatArrayValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putLongArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Long[]> key = StorageKeyFactory.boxedLongArrayKey("putLongArrayValue");
    final Long[] value = new Long[]{12345L,35234523453245L};
    storage.put(key, value);
    assertEquals("putLongArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putLongArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putLongArrayValue - stored value", value, storage.get(key));

    StorageKey<long[]> key2 = StorageKeyFactory.longArrayKey("putLongArrayValue");
    final long[] value2 = new long[]{123412341234l, 9087098709798l};
    storage.put(key2, value2);
    assertEquals("putLongArrayValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putLongArrayValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putShortArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Short[]> key = StorageKeyFactory.boxedShortArrayKey("putShortArrayValue");
    final Short[] value = new Short[]{Short.MAX_VALUE, Short.MIN_VALUE};
    storage.put(key, value);
    assertEquals("putShortArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putShortArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putShortArrayValue - stored value", value, storage.get(key));

    StorageKey<short[]> key2 = StorageKeyFactory.shortArrayKey("putShortArrayValue");
    final short[] value2 = new short[]{Short.MIN_VALUE, Short.MAX_VALUE};
    storage.put(key2, value2);
    assertEquals("putShortArrayValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putShortArrayValue(primitive) - stored value", value2, storage.get(key2));
  }

  public static void putObjectArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<TestValue[]> key = StorageKeyFactory.objectKey("putObjectArrayValue");
    final TestValue[] values = new TestValue[]{new TestValue("hello2"), new TestValue("hello2")};
    storage.put(key, values);
    assertEquals("putObjectArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putObjectArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putObjectArrayValue - stored value", values, storage.get(key));

    final TestValue[] values2 = new TestValue[]{new TestValue("hello1"), new TestValue("hello2")};
    storage.put(key, values2);
    assertEquals("putObjectArrayValue - storage size", expectedSize, storage.size());
    assertEquals("putObjectArrayValue - stored value", values2, storage.get(key));
  }

  public static void putGenericObjectArrayValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<GenericTestValue<TestValue>[]> key = StorageKeyFactory.objectKey("putGenericObjectArrayValue");
    final GenericTestValue<TestValue>[] values = new GenericTestValue[]{new GenericTestValue<TestValue>(new TestValue("hello2")), new GenericTestValue<TestValue>(new TestValue("hello2"))};
    storage.put(key, values);
    assertEquals("putGenericObjectArrayValue - storage size", expectedSize, storage.size());
    assertTrue("putGenericObjectArrayValue - containsKey", storage.containsKey(key));
    assertEquals("putGenericObjectArrayValue - stored value", values, storage.get(key));

    final GenericTestValue<TestValue>[] values2 = new GenericTestValue[]{new GenericTestValue<TestValue>(new TestValue("hello1")), new GenericTestValue<TestValue>(new TestValue("hello2"))};
    storage.put(key, values2);
    assertEquals("putGenericObjectArrayValue - storage size", expectedSize, storage.size());
    assertEquals("putGenericObjectArrayValue - stored value", values2, storage.get(key));
  }

}