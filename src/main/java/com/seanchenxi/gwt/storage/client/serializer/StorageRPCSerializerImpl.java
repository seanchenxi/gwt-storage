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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamReader;
import com.google.gwt.user.client.rpc.impl.Serializer;

import java.io.Serializable;

final class StorageRPCSerializerImpl implements StorageSerializer {

  private final static Serializer TYPE_SERIALIZER = GWT.create(StorageTypeSerializer.class);

  @Override @SuppressWarnings("unchecked")
  public <T extends Serializable> T deserialize(Class<? super T> clazz, String encodedString) throws SerializationException {
    if (encodedString == null) {
      return null;
    }else if(String.class.equals(clazz)){
      return (T) encodedString;
    }
    ClientSerializationStreamReader reader = new ClientSerializationStreamReader(TYPE_SERIALIZER);
    reader.prepareToRead(encodedString);
    Object obj = findType(clazz).read(reader);
    return obj != null ? (T) obj : null;
  }

  @Override
  public <T extends Serializable> String serialize(Class<? super T> clazz, T instance) throws SerializationException {
    if (instance == null) {
      return null;
    }else if(String.class.equals(clazz)){
      return (String) instance;
    }
    StorageSerializationStreamWriter writer = new StorageSerializationStreamWriter(TYPE_SERIALIZER);
    writer.prepareToWrite();
    if(clazz.isArray()){ // for array type, must write its type name at first
      writer.writeString(TYPE_SERIALIZER.getSerializationSignature(clazz));
    }
    findType(clazz).write(writer, instance);
    return writer.toString();
  }

  private StorageValueType findType(Class<?> clazz) {
    StorageValueType type = TYPE_MAP.get(clazz);
    if (type == null) { // for primitive array, use object writer
      type = clazz.isArray() ? StorageValueType.OBJECT_VECTOR : StorageValueType.OBJECT;
    }
    return type;
  }

}
