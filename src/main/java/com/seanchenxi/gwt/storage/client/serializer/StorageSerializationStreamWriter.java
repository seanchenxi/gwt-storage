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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.lang.LongLib;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.AbstractSerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.Serializer;

final class StorageSerializationStreamWriter extends AbstractSerializationStreamWriter {

  private static final String APOSTROPHE = "'";
  private static final String BLANK = "";
  private static final String CLOSE_BRACKET = "]";
  private static final String COMMA = ",";
  private static final String OPEN_BRACKET = "[";

  private static void append(StringBuffer sb, String token) {
    assert (token != null);
    sb.append(token).append(COMMA);
  }

  private final Serializer serializer;
  private ArrayList<String> tokenList;

  public StorageSerializationStreamWriter(Serializer serializer) {
    this.serializer = serializer;
  }

  @Override
  public void prepareToWrite() {
    super.prepareToWrite();
    tokenList = new ArrayList<String>();
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer(OPEN_BRACKET);
    writePayload(buffer);
    writeStringTable(buffer);
    writeHeader(buffer);
    return JsonUtils.escapeJsonForEval(buffer.append(CLOSE_BRACKET).toString());
  }

  @Override
  public void writeLong(long value) {
    append(APOSTROPHE + LongLib.toBase64(value) + APOSTROPHE);
  }

  @Override
  protected void append(String token) {
    assert (token != null);
    tokenList.add(tokenList.size(), token);
  }

  @Override
  protected String getObjectTypeSignature(Object o) {
    Class<?> clazz = o.getClass();
    if (o instanceof Enum<?>) {
      Enum<?> e = (Enum<?>) o;
      clazz = e.getDeclaringClass();
    }
    return serializer.getSerializationSignature(clazz);
  }

  @Override
  protected void serialize(Object instance, String typeSignature) throws SerializationException {
    serializer.serialize(this, instance, typeSignature);
  }

  private String escapeString(String toEscape) {
    if (toEscape == null) return null;
    return JsonUtils.escapeValue(toEscape);
  }

  private void writeHeader(StringBuffer buffer) {
     append(buffer, String.valueOf(getFlags()));
     buffer.append(getVersion());
  }

  private void writePayload(StringBuffer buffer) {
    ListIterator<String> list = tokenList.listIterator(tokenList.size());
    while (list.hasPrevious()) {
      append(buffer, list.previous());
    }
  }

  private void writeStringTable(StringBuffer buffer) {
    StringBuffer sb = new StringBuffer(OPEN_BRACKET);
    Iterator<String> stringTable = getStringTable().iterator();
    while (stringTable.hasNext()) {
      append(sb, escapeString(stringTable.next()));
    }
    sb.append(CLOSE_BRACKET);
    int index = sb.lastIndexOf(COMMA);
    String table = (index > -1) ? sb.replace(index, index + 1, BLANK).toString() : sb.toString();
    append(buffer, table);
  }

}
