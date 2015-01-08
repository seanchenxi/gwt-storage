package com.seanchenxi.gwt.storage.server;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.AbstractSerializationStream;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamWriter;

/**
 * Created by Xi on 2015/1/6.
 */
public class ServerStorageSerializer {

    public <T> T deserialize(Class<? super T> clazz, String serializedString) throws SerializationException {
        throw new UnsupportedOperationException();
    }

    public <T> String serialize(Class<? super T> clazz, T instance, SerializationPolicy serializationPolicy) throws SerializationException {
        if(serializationPolicy == null){
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
