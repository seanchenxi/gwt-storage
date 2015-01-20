/*
 * Copyright 2015 Xi CHEN
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

package com.seanchenxi.gwt.storage.server;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.AbstractSerializationStream;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamWriter;

/**
 * Created by: Xi
 */
public class ServerStorageSerializer {

  public <T> T deserialize(Class<? super T> clazz, String serializedString) throws SerializationException {
    throw new UnsupportedOperationException();
  }

  public <T> String serialize(Class<? super T> clazz, T instance, SerializationPolicy serializationPolicy) throws SerializationException {
    if (serializationPolicy == null) {
      throw new IllegalArgumentException("SerializationPolicy is null, please call ");
    }
    ServerSerializationStreamWriter stream = new ServerSerializationStreamWriter(serializationPolicy);
    stream.setFlags(AbstractSerializationStream.DEFAULT_FLAGS);
    stream.prepareToWrite();
    if (clazz != void.class) {
      stream.serializeValue(instance, clazz);
    }
    return stream.toString();
  }

}
