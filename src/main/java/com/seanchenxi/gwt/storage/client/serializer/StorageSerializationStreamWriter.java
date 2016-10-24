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
    append(APOSTROPHE + longToBase64(value) + APOSTROPHE);
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

  /**
   * Copy from {@link com.google.gwt.user.client.rpc.impl.AbstractSerializationStream#longToBase64(long)}
   *
   * Return an optionally single-quoted string containing a base-64 encoded
   * version of the given long value.
   *
   * Keep this synchronized with the version in Base64Utils.
   */
  static String longToBase64(long value) {
    int low = (int)(value & -1L);
    int high = (int)(value >> 32);
    StringBuilder sb = new StringBuilder();
    boolean haveNonZero = base64Append(sb, high >> 28 & 15, false);
    haveNonZero = base64Append(sb, high >> 22 & 63, haveNonZero);
    haveNonZero = base64Append(sb, high >> 16 & 63, haveNonZero);
    haveNonZero = base64Append(sb, high >> 10 & 63, haveNonZero);
    haveNonZero = base64Append(sb, high >> 4 & 63, haveNonZero);
    int v = (high & 15) << 2 | low >> 30 & 3;
    haveNonZero = base64Append(sb, v, haveNonZero);
    haveNonZero = base64Append(sb, low >> 24 & 63, haveNonZero);
    haveNonZero = base64Append(sb, low >> 18 & 63, haveNonZero);
    haveNonZero = base64Append(sb, low >> 12 & 63, haveNonZero);
    base64Append(sb, low >> 6 & 63, haveNonZero);
    base64Append(sb, low & 63, true);
    return sb.toString();
  }

  private static boolean base64Append(StringBuilder sb, int digit, boolean haveNonZero) {
    if(digit > 0) {
      haveNonZero = true;
    }

    if(haveNonZero) {
      int c;
      if(digit < 26) {
        c = 65 + digit;
      } else if(digit < 52) {
        c = 97 + digit - 26;
      } else if(digit < 62) {
        c = 48 + digit - 52;
      } else if(digit == 62) {
        c = 36;
      } else {
        c = 95;
      }

      sb.append((char)c);
    }

    return haveNonZero;
  }
}
