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

package com.seanchenxi.gwt.storage.client.serializer;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamReader;

enum StorageValueType {
  BOOLEAN {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readBoolean();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeBoolean(((Boolean) instance).booleanValue());
    }
  },
  BOOLEAN_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      boolean[] vector = (boolean[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeBoolean(vector[i]);
      }
    }
  },
  BYTE {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readByte();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeByte(((Byte) instance).byteValue());
    }
  },
  BYTE_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      byte[] vector = (byte[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeByte(vector[i]);
      }
    }
  },
  CHAR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readChar();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeChar(((Character) instance).charValue());
    }
  },
  CHAR_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      char[] vector = (char[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeChar(vector[i]);
      }
    }
  },
  DOUBLE {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readDouble();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeDouble(((Double) instance).doubleValue());
    }
  },
  DOUBLE_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      double[] vector = (double[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeDouble(vector[i]);
      }
    }
  },
  FLOAT {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readFloat();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeFloat(((Float) instance).floatValue());
    }
  },
  FLOAT_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      float[] vector = (float[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeFloat(vector[i]);
      }
    }
  },
  INT {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readInt();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeInt(((Integer) instance).intValue());
    }
  },
  INT_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      int[] vector = (int[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeInt(vector[i]);
      }
    }
  },
  LONG {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readLong();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeLong(((Long) instance).longValue());
    }
  },
  LONG_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      long[] vector = (long[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeLong(vector[i]);
      }
    }
  },
  OBJECT {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance)
        throws SerializationException {
      writer.writeObject(instance);
    }
  },
  OBJECT_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance)
        throws SerializationException {
      Object[] vector = (Object[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeObject(vector[i]);
      }
    }
  },
  SHORT {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readShort();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeShort(((Short) instance).shortValue());
    }
  },
  SHORT_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      short[] vector = (short[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeShort(vector[i]);
      }
    }
  },
  STRING {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readString();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      writer.writeString((String) instance);
    }
  },
  STRING_VECTOR {
    @Override
    Object read(ClientSerializationStreamReader reader) throws SerializationException {
      return reader.readObject();
    }

    @Override
    void write(StorageSerializationStreamWriter writer, Object instance) {
      String[] vector = (String[]) instance;
      writer.writeInt(vector.length);
      for (int i = 0, n = vector.length; i < n; ++i) {
        writer.writeString(vector[i]);
      }
    }
  };

  abstract Object read(ClientSerializationStreamReader reader) throws SerializationException;

  abstract void write(StorageSerializationStreamWriter writer, Object instance)
      throws SerializationException;

}