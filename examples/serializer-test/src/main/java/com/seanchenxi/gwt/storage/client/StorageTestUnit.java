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
package com.seanchenxi.gwt.storage.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.ui.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by: Xi
 */
public class StorageTestUnit {

  private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
  private static int line = 0;
  private static VerticalPanel TRACE;
  private static VerticalPanel EVENT;
  static{
    DockLayoutPanel dlp = new DockLayoutPanel(Style.Unit.PCT);
    ScrollPanel sp;
    dlp.addSouth(sp = new ScrollPanel(EVENT = new VerticalPanel()), 25);
    dlp.add(new ScrollPanel(TRACE = new VerticalPanel()));
    final Style style = sp.getElement().getStyle();
    style.setPaddingTop(5, Style.Unit.PX);
    style.setBorderWidth(1, Style.Unit.PX);
    style.setBorderColor("#222");
    style.setBorderStyle(Style.BorderStyle.SOLID);
    RootLayoutPanel.get().add(dlp);
  }

  public static <T extends Serializable, V> void testPutValue(StorageExt storage, String name, StorageKey<T> key, final V value1, final V value2) throws StorageQuotaExceededException, SerializationException {
    final int expectedSize = storage.size();
    storage.put(key, (T)value1);
    assertEquals(name + " - storage size", expectedSize, storage.size());
    assertTrue(name + " - contains key(" + key.name() + ")", storage.containsKey(key));
    assertEquals(name + " - stored value with key(" + key.name() + ")", value1, storage.get(key));

    storage.put(key, (T)value2);
    assertEquals(name + " - storage size", expectedSize, storage.size());
    assertEquals(name + " - stored value with key(" + key.name() + ")", value2, storage.get(key));
  }

  public static void assertEquals(String name, Object expected, Object value) {
    if ((expected == null || value == null) || expected == value || expected.equals(value)) {
      traceSucceed(name);
    } else if(expected.getClass().isArray() && value.getClass().isArray() && Arrays.equals((Object[])expected, (Object[])value)){
      traceSucceed(name);
    }else if(expected.getClass().isArray() && value.getClass().isArray()) {
      traceError(name, Arrays.asList((Object[])expected), Arrays.asList((Object[])value));
    }else {
      traceError(name, expected, value);
    }
  }

  public static void assertEquals(String name, boolean[] expected, boolean[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertEquals(String name, float[] expected, float[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertEquals(String name, int[] expected, int[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertEquals(String name, short[] expected, short[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertEquals(String name, double[] expected, double[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertEquals(String name, long[] expected, long[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertEquals(String name, byte[] expected, byte[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertEquals(String name, char[] expected, char[] value) {
    if (Arrays.equals(expected, value)) {
      traceSucceed(name);
    } else {
      traceError(name, Arrays.asList(expected), Arrays.asList(value));
    }
  }

  public static void assertTrue(String name, boolean value) {
    if (value) {
      traceSucceed(name);
    } else {
      traceError(name, true, false);
    }
  }

  public static void traceSucceed(String name){
    trace(name + " - assertTrue succeed.", false);
  }

  public static void traceError(String name, Object expected, Object value){
    trace(name + "-  assertEquals error: expected=" + String.valueOf(expected) + ", but given=" + String.valueOf(value), true);
  }

  public static void traceEmptyLine(){
    trace("==", false);
  }

  public static void trace(String str, boolean isError) {
    if("==".equalsIgnoreCase(str)){
      TRACE.add(new HTML("<br/>"));
    } else{
      HTML line = new HTML(incrementLineCounter() + ".&nbsp;<b>"  + str + "</b>");
      line.setStyleName(isError ? "error" : "succeed");
      TRACE.add(line);
    }
  }

  public static void event(String str, boolean isError) {
    if("==".equalsIgnoreCase(str)){
      EVENT.add(new HTML("<br/>"));
    } else{
      HTML line = new HTML(FORMAT.format(new Date()) + "&nbsp;&nbsp;<b>" + str + "</b>");
      line.setStyleName(isError ? "error" : "succeed");
      EVENT.add(line);
    }
  }

  public static int getLineNumber(){
    return line;
  }

  private static int incrementLineCounter() {
    return StorageTestUnit.line++;
  }

}
