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
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamReader;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamWriter;

/**
 * Created by: Xi
 */
public class ServerStorageSerializer {

  private static final String SERVER_READABLE_STR_1 = "_i_b";
  private static final String SERVER_READABLE_STR_2 = "_s";

  public  <T> T deserialize(Class<? super T> clazz, String serializedString, final SerializationPolicy serializationPolicy) throws SerializationException {
    if(serializedString == null || serializedString.trim().length() < 1){
      return null;
    }else if(String.class.isAssignableFrom(clazz)){
      return (T) serializedString;
    }

    RpcProtocolReversing.Builder builder = RpcProtocolReversing.forServerRead(serializedString);
    ServerSerializationStreamReader serverSerializationStreamReader = new ServerSerializationStreamReader(this.getClass().getClassLoader(), (moduleBaseURL, serializationPolicyStrongName) -> serializationPolicy);
    serverSerializationStreamReader.prepareToRead(builder.build());
    return (T) serverSerializationStreamReader.deserializeValue(clazz);
  }

  public <T> String serialize(Class<? super T> clazz, T instance, SerializationPolicy serializationPolicy) throws SerializationException {
    if (instance == null) {
      return null;
    }else if(String.class.isAssignableFrom(clazz)){
      return (String) instance;
    }

    if (serializationPolicy == null) {
      throw new IllegalArgumentException("SerializationPolicy is null, please call StorageUtils.PolicyLoader.load(...) before");
    }

    ServerSerializationStreamWriter stream = new ServerSerializationStreamWriter(serializationPolicy);
    stream.setFlags(AbstractSerializationStream.DEFAULT_FLAGS);
    stream.prepareToWrite();
    stream.writeString(SERVER_READABLE_STR_1);
    stream.writeString(SERVER_READABLE_STR_2);
    if (clazz != void.class) {
      stream.serializeValue(instance, clazz);
    }
    return stream.toString();
  }

}
