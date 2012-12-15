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

package com.seanchenxi.gwt.storage.client.serializer;

import com.google.gwt.user.client.rpc.SerializationException;

import java.io.Serializable;
import java.util.HashMap;

public interface StorageSerializer {

  static final class PrimitiveTypeMap extends HashMap<Class<?>, StorageValueType> {

    private PrimitiveTypeMap() {
      put(boolean[].class, StorageValueType.BOOLEAN_VECTOR);
      put(byte[].class, StorageValueType.BYTE_VECTOR);
      put(char[].class, StorageValueType.CHAR_VECTOR);
      put(double[].class, StorageValueType.DOUBLE_VECTOR);
      put(float[].class, StorageValueType.FLOAT_VECTOR);
      put(int[].class, StorageValueType.INT_VECTOR);
      put(long[].class, StorageValueType.LONG_VECTOR);
      put(short[].class, StorageValueType.SHORT_VECTOR);
      put(String[].class, StorageValueType.STRING_VECTOR);

      put(Boolean[].class, StorageValueType.BOOLEAN_VECTOR);
      put(Byte[].class, StorageValueType.BYTE_VECTOR);
      put(Character[].class, StorageValueType.CHAR_VECTOR);
      put(Double[].class, StorageValueType.DOUBLE_VECTOR);
      put(Float[].class, StorageValueType.FLOAT_VECTOR);
      put(Integer[].class, StorageValueType.INT_VECTOR);
      put(Long[].class, StorageValueType.LONG_VECTOR);
      put(Short[].class, StorageValueType.SHORT_VECTOR);

      put(boolean.class, StorageValueType.BOOLEAN);
      put(byte.class, StorageValueType.BYTE);
      put(char.class, StorageValueType.CHAR);
      put(double.class, StorageValueType.DOUBLE);
      put(float.class, StorageValueType.FLOAT);
      put(int.class, StorageValueType.INT);
      put(long.class, StorageValueType.LONG);
      put(short.class, StorageValueType.SHORT);
      put(String.class, StorageValueType.STRING);

      put(Boolean.class, StorageValueType.BOOLEAN);
      put(Byte.class, StorageValueType.BYTE);
      put(Character.class, StorageValueType.CHAR);
      put(Double.class, StorageValueType.DOUBLE);
      put(Float.class, StorageValueType.FLOAT);
      put(Integer.class, StorageValueType.INT);
      put(Long.class, StorageValueType.LONG);
      put(Short.class, StorageValueType.SHORT);
    }

  }

  static final PrimitiveTypeMap TYPE_MAP = new PrimitiveTypeMap();

  <T extends Serializable> T deserialize(Class<? super T> clazz, String encodedString) throws SerializationException;

  <T extends Serializable> String serialize(Class<? super T> clazz, T instance) throws SerializationException;
}