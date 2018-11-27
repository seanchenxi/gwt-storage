/*
 * Copyright 2018 Xi CHEN
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

package com.seanchenxi.gwt.storage.client.serializer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.AbstractSerializationStream;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamReader;
import com.google.gwt.user.client.rpc.impl.Serializer;

import java.util.HashMap;

/**
 * Abstract definition of {@link StorageSerializer}
 *
 * Use GWT RPC way to realize object's serialization or deserialization.
 *
 */
abstract class AbstractStorageSerializerImpl implements StorageSerializer {

  private static final String SERVER_READABLE_STR_1 = "_i_b";
  private static final String SERVER_READABLE_STR_2 = "_s";
  private static final String SERVER_READABLE_REGEX = ".*\\[\\s*\""+SERVER_READABLE_STR_1+"\"\\s*,\\s*\""+SERVER_READABLE_STR_2+"\".*";
  private static final Serializer TYPE_SERIALIZER;
  private static final HashMap<Class<?>, StorageValueType> TYPE_MAP;

  static{
    TYPE_SERIALIZER = GWT.create(StorageTypeSerializer.class);
    TYPE_MAP = new HashMap<>();

    TYPE_MAP.put(boolean[].class, StorageValueType.BOOLEAN_VECTOR);
    TYPE_MAP.put(byte[].class, StorageValueType.BYTE_VECTOR);
    TYPE_MAP.put(char[].class, StorageValueType.CHAR_VECTOR);
    TYPE_MAP.put(double[].class, StorageValueType.DOUBLE_VECTOR);
    TYPE_MAP.put(float[].class, StorageValueType.FLOAT_VECTOR);
    TYPE_MAP.put(int[].class, StorageValueType.INT_VECTOR);
    TYPE_MAP.put(long[].class, StorageValueType.LONG_VECTOR);
    TYPE_MAP.put(short[].class, StorageValueType.SHORT_VECTOR);
    TYPE_MAP.put(String[].class, StorageValueType.STRING_VECTOR);

    TYPE_MAP.put(boolean.class, StorageValueType.BOOLEAN);
    TYPE_MAP.put(byte.class, StorageValueType.BYTE);
    TYPE_MAP.put(char.class, StorageValueType.CHAR);
    TYPE_MAP.put(double.class, StorageValueType.DOUBLE);
    TYPE_MAP.put(float.class, StorageValueType.FLOAT);
    TYPE_MAP.put(int.class, StorageValueType.INT);
    TYPE_MAP.put(long.class, StorageValueType.LONG);
    TYPE_MAP.put(short.class, StorageValueType.SHORT);
    TYPE_MAP.put(String.class, StorageValueType.STRING);
  }

  AbstractStorageSerializerImpl(){}

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(Class<? super T> clazz, String serializedString) throws SerializationException {
    if (serializedString == null) {
      return null;
    }else if(String.class.equals(clazz)){
      return (T) serializedString;
    }
    ClientSerializationStreamReader reader = new ClientSerializationStreamReader(TYPE_SERIALIZER);
    reader.prepareToRead(serializedString);
    if(serializedString.matches(SERVER_READABLE_REGEX)) {
      reader.readString();
      reader.readString();
    }
    Object obj = findType(clazz).read(reader);
    return obj != null ? (T) obj : null;
  }

  @Override
  public <T> String serialize(Class<? super T> clazz, T instance) throws SerializationException {
    if (instance == null) {
      return null;
    }else if(String.class.equals(clazz)){
      return (String) instance;
    }
    StorageSerializationStreamWriter writer = new StorageSerializationStreamWriter(TYPE_SERIALIZER);
    writer.setFlags(AbstractSerializationStream.DEFAULT_FLAGS);
    writer.prepareToWrite();
    if(isSeverReadable()){
      writer.writeString(SERVER_READABLE_STR_1);
      writer.writeString(SERVER_READABLE_STR_2);
    }
    if(clazz.isArray()){ // for array type, must write its type name at first
      writer.writeString(writer.getSerializationSignature(clazz));
    }
    findType(clazz).write(writer, instance);
    return writer.toString();
  }

  protected abstract boolean isSeverReadable();

  private StorageValueType findType(Class<?> clazz) {
    StorageValueType type = TYPE_MAP.get(clazz);
    if (type == null) { // for primitive array, use object writer
      type = clazz.isArray() ? StorageValueType.OBJECT_VECTOR : StorageValueType.OBJECT;
    }
    return type;
  }

}
