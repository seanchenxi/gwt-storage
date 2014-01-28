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
package com.seanchenxi.gwt.storage.client.keyprovider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;

import com.seanchenxi.gwt.storage.client.value.GenericTestValue;
import com.seanchenxi.gwt.storage.client.StorageExt;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageQuotaExceededException;
import com.seanchenxi.gwt.storage.client.StorageTestUnit;
import com.seanchenxi.gwt.storage.client.value.TestValue;

public class SimpleTypeKeys extends StorageTestUnit {

  private final static SimpleTypeKeyProvider KEY_PROVIDER = GWT.create(SimpleTypeKeyProvider.class);

  public static void putIntegerValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Integer> key = KEY_PROVIDER.intKey();
    final Integer value = Integer.MAX_VALUE;
    storage.put(key, value);
    assertEquals("putIntegerValue - storage size", expectedSize, storage.size());
    assertTrue("putIntegerValue - containsKey", storage.containsKey(key));
    assertEquals("putIntegerValue - stored value", value, storage.get(key));

    final int value2 = 39023948;
    storage.put(key, value2);
    assertEquals("putIntegerValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putIntegerValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putIntegerValue2(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Integer> key = KEY_PROVIDER.intKey("putIntegerValue");
    final Integer value = Integer.MAX_VALUE;
    storage.put(key, value);
    assertEquals("putIntegerValue - storage size", expectedSize, storage.size());
    assertTrue("putIntegerValue - containsKey", storage.containsKey(key));
    assertEquals("putIntegerValue - stored value", value, storage.get(key));

    final int value2 = 39023948;
    storage.put(key, value2);
    assertEquals("putIntegerValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putIntegerValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putStringValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<String> key = KEY_PROVIDER.stringKey("putStringValue");
    final String value = "StringValue";
    storage.put(key, value);
    assertEquals("putStringValue - storage size", expectedSize, storage.size());
    assertTrue("putStringValue - containsKey", storage.containsKey(key));
    assertEquals("putStringValue - stored value", value, storage.get(key));

    final String value2 = "";
    storage.put(key, value2);
    assertEquals("putIntegerValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putIntegerValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putBooleanValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Boolean> key = KEY_PROVIDER.boolKey("putBooleanValue");
    final Boolean value = false;
    storage.put(key, value);
    assertEquals("putBooleanValue - storage size", expectedSize, storage.size());
    assertTrue("putBooleanValue - containsKey", storage.containsKey(key));
    assertEquals("putBooleanValue - stored value", value, storage.get(key));

    final boolean value2 = true;
    storage.put(key, value2);
    assertEquals("putBooleanValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putBooleanValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putByteValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Byte> key = KEY_PROVIDER.byteKey("putByteValue");
    final Byte value = Byte.MAX_VALUE;
    storage.put(key, value);
    assertEquals("putByteValue - storage size", expectedSize, storage.size());
    assertTrue("putByteValue - containsKey", storage.containsKey(key));
    assertEquals("putByteValue - stored value", value, storage.get(key));

    final byte value2 = Byte.parseByte("01");
    storage.put(key, value2);
    assertEquals("putByteValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putByteValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putDoubleValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Double> key = KEY_PROVIDER.doubleKey("putDoubleValue");
    final Double value = 25.58D;
    storage.put(key, value);
    assertEquals("putDoubleValue - storage size", expectedSize, storage.size());
    assertTrue("putDoubleValue - containsKey", storage.containsKey(key));
    assertEquals("putDoubleValue - stored value", value, storage.get(key));

    final double value2 = 32.45d;
    storage.put(key, value2);
    assertEquals("putDoubleValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putDoubleValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putCharacterValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Character> key = KEY_PROVIDER.charKey("putCharacterValue");
    final Character value = Character.MAX_VALUE;
    storage.put(key, value);
    assertEquals("putCharacterValue - storage size", expectedSize, storage.size());
    assertTrue("putCharacterValue - containsKey", storage.containsKey(key));
    assertEquals("putCharacterValue - stored value", value, storage.get(key));

    final char value2 = Character.MIN_VALUE;
    storage.put(key, value2);
    assertEquals("putCharacterValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putCharacterValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putFloatValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Float> key = KEY_PROVIDER.floatKey("putFloatValue");
    final Float value = 25.58F;
    storage.put(key, value);
    assertEquals("putFloatValue - storage size", expectedSize, storage.size());
    assertTrue("putFloatValue - containsKey", storage.containsKey(key));
    assertEquals("putFloatValue - stored value", value, storage.get(key));

    final float value2 = 32.45f;
    storage.put(key, value2);
    assertEquals("putFloatValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putFloatValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putLongValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Long> key = KEY_PROVIDER.longKey("putLongValue");
    final Long value = Long.parseLong("12345");
    storage.put(key, value);
    assertEquals("putLongValue - storage size", expectedSize, storage.size());
    assertTrue("putLongValue - containsKey", storage.containsKey(key));
    assertEquals("putLongValue - stored value", value, storage.get(key));

    final long value2 = Long.parseLong("23456");
    storage.put(key, value2);
    assertEquals("putLongValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putLongValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putShortValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<Short> key = KEY_PROVIDER.shortKey("putShortValue");
    final Short value = Short.MAX_VALUE;
    storage.put(key, value);
    assertEquals("putShortValue - storage size", expectedSize, storage.size());
    assertTrue("putShortValue - containsKey", storage.containsKey(key));
    assertEquals("putShortValue - stored value", value, storage.get(key));

    final short value2 = Short.MIN_VALUE;
    storage.put(key, value2);
    assertEquals("putShortValue(primitive) - storage size", expectedSize, storage.size());
    assertEquals("putShortValue(primitive) - stored value", value2, storage.get(key));
  }

  public static void putObjectValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<TestValue> key = KEY_PROVIDER.testValueKey("putObjectValue");
    final TestValue value = new TestValue("hello");
    storage.put(key, value);
    assertEquals("putObjectValue - storage size", expectedSize, storage.size());
    assertTrue("putObjectValue - containsKey", storage.containsKey(key));
    assertEquals("putObjectValue - stored value", value, storage.get(key));

    final TestValue value2 = new TestValue("hello2");
    storage.put(key, value2);
    assertEquals("putObjectValue - storage size", expectedSize, storage.size());
    assertEquals("putObjectValue - stored value", value2, storage.get(key));
  }

  public static void putGenericObjectValue(StorageExt storage, int expectedSize) throws SerializationException, StorageQuotaExceededException {
    StorageKey<GenericTestValue<TestValue>> key = KEY_PROVIDER.genericTestValueKey("putGenericObjectValue");
    final GenericTestValue<TestValue> value = new GenericTestValue<TestValue>(new TestValue("hello"));
    storage.put(key, value);
    assertEquals("putGenericObjectValue - storage size", expectedSize, storage.size());
    assertTrue("putGenericObjectValue - containsKey", storage.containsKey(key));
    assertEquals("putGenericObjectValue - stored value", value, storage.get(key));

    final GenericTestValue<TestValue> value2 = new GenericTestValue<TestValue>(new TestValue("hello2"));
    storage.put(key, value2);
    assertEquals("putGenericObjectValue - storage size", expectedSize, storage.size());
    assertEquals("putGenericObjectValue - stored value", value2, storage.get(key));
  }

}