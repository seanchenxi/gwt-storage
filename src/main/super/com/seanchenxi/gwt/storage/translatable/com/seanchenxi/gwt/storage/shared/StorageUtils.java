package com.seanchenxi.gwt.storage.shared;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.serializer.StorageSerializer;

/**
 * Created by Xi on 2015/1/6.
 */
public class StorageUtils {

    private static final StorageSerializer SERIALIZER;
    static{
        SERIALIZER = GWT.create(StorageSerializer.class);
    }

    public static String serialize(String str) throws SerializationException {
        return serialize(String.class, str);
    }

    public static String serialize(boolean bool) throws SerializationException {
        return serialize(boolean.class, bool);
    }

    public static String serialize(byte value) throws SerializationException {
        return serialize(byte.class, value);
    }

    public static String serialize(char value) throws SerializationException {
        return serialize(char.class, value);
    }

    public static String serialize(double value) throws SerializationException {
        return serialize(double.class, value);
    }

    public static String serialize(float value) throws SerializationException {
        return serialize(float.class, value);
    }

    public static String serialize(int value) throws SerializationException {
        return serialize(int.class, value);
    }

    public static String serialize(long value) throws SerializationException {
        return serialize(long.class, value);
    }

    public static String serialize(short value) throws SerializationException {
        return serialize(short.class, value);
    }

    public static String serialize(Object value) throws SerializationException {
        return serialize(Object.class, value);
    }

    public static <T> String serialize(Class<? super T> clazz, T instance) throws SerializationException {
        return SERIALIZER.serialize(clazz, instance);
    }

    public static <T> T deserialize(Class<? super T> clazz, String serializedString) throws SerializationException {
        return SERIALIZER.deserialize(clazz, serializedString);
    }

}
