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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by: Xi
 */
public class StorageTestUtil {

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

  private static StorageExt CURRENT_STORAGE;
  private static List<Scheduler.RepeatingCommand> TESTS;

  public static void prepare(StorageExt storage) {
    traceEmptyLine();
    storage.clear();
    assertEquals("storage size", 0, storage.size());
    traceEmptyLine();
    trace("Preparing test tasks...", false);
    CURRENT_STORAGE = storage;
    TESTS = new ArrayList<Scheduler.RepeatingCommand>();
  }

  public static void start() {
    StorageTestUtil.trace("Test prepare.", false);
    StorageTestUtil.traceEmptyLine();
  }

  public static List<Scheduler.RepeatingCommand> getTests() {
    return TESTS;
  }

  public static <T1, T2 extends Serializable, V1 extends T1, V2 extends T2> void testPutValue(final StorageKey<T1> key1, final StorageKey<T2> key2, final V1 value1, final V2 value2) {
    TESTS.add(new Scheduler.RepeatingCommand() {
      @Override
      public boolean execute() {
        final String name1 = "testPut_" + key1.name() + "_Value";
        boolean isOK;
        try{
          int expectedSize = CURRENT_STORAGE.size() + 1;
          CURRENT_STORAGE.put(key1, value1);
          firstAssertGroup(name1, expectedSize, key1, value1);
          isOK = true;
        }catch (Exception e){
          trace(name1 + " error " + e.getClass().getName() + ": " + e.getMessage(), false);
          GWT.log("error", e);
          isOK = false;
        }

        if(isOK){
          final String name2 = "testPut_" + key2.name() + "_Value";
          try{
            int expectedSize = CURRENT_STORAGE.size() + 1;
            CURRENT_STORAGE.put(key2, value2);
            secondAssertGroup(name2, expectedSize, key2, value2);
            isOK = true;
          }catch (Exception e){
            trace(name2 + " error " + e.getClass().getName() + ": " + e.getMessage(), false);
            GWT.log("error", e);
            isOK = false;
          }
        }

        traceEmptyLine();
        return isOK;
      }
    });
  }

  public static <T, V, E> void testPutValue(final StorageKey<T> key, final V value1, final E value2) {
    TESTS.add(new Scheduler.RepeatingCommand() {
      @Override
      public boolean execute() {
        final String name = "testPut_" + key.name() + "_Value";
        try{
          final int expectedSize = CURRENT_STORAGE.size() + 1;
          CURRENT_STORAGE.put(key, (T) value1);
          firstAssertGroup(name, expectedSize, key, value1);

          CURRENT_STORAGE.put(key, (T) value2);
          secondAssertGroup(name, expectedSize, key, value2);

          return true;
        }catch (Exception e){
          trace(name + " error " + e.getClass().getName() + ": " + e.getMessage(), false);
          GWT.log("error", e);
          return false;
        }finally {
          traceEmptyLine();
        }
      }
    });
  }

  public static <T, V> void firstAssertGroup(String name, int expectedSize, StorageKey<T> key, V value) throws SerializationException {
    assertEquals(name + " - storage size", expectedSize, CURRENT_STORAGE.size());
    assertTrue(name + " - containsKey", CURRENT_STORAGE.containsKey(key));
    assertEquals(name + " - stored value", value, CURRENT_STORAGE.get(key));
  }

  public static <T, V> void secondAssertGroup(String name, int expectedSize, StorageKey<T> key, V value) throws SerializationException {
    assertEquals(name + " - storage size", expectedSize, CURRENT_STORAGE.size());
    assertEquals(name + " - stored value", value, CURRENT_STORAGE.get(key));
  }

  public static void assertEquals(String name, Object expected, Object value) {
    if ((expected == null || value == null) || expected == value || expected.equals(value)) {
      traceSucceed(name);
    } else if(expected.getClass().isArray() && value.getClass().isArray()){
      if(expected instanceof int[]){
        assertEquals(name, (int[])expected, (int[])value);
      }else if(expected instanceof boolean[]){
        assertEquals(name, (boolean[])expected, (boolean[])value);
      }else if(expected instanceof float[]){
        assertEquals(name, (float[])expected, (float[])value);
      }else if(expected instanceof short[]){
        assertEquals(name, (short[])expected, (short[])value);
      }else if(expected instanceof double[]){
        assertEquals(name, (double[])expected, (double[])value);
      }else if(expected instanceof long[]){
        assertEquals(name, (long[])expected, (long[])value);
      }else if(expected instanceof char[]){
        assertEquals(name, (char[])expected, (char[])value);
      }else if(expected instanceof byte[]){
        assertEquals(name, (byte[])expected, (byte[])value);
      }else if(expected instanceof Object[] && Arrays.equals((Object[])expected, (Object[])value)){
        traceSucceed(name);
      }else{
        traceError(name, Arrays.asList((Object[])expected), Arrays.asList((Object[])value));
      }
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
    return StorageTestUtil.line++;
  }

  public static boolean end() {
    boolean isOK = (StorageTestUtil.TESTS.size() * 5 + 4 == StorageTestUtil.line);
    trace(isOK ? "<b>OK</b>" : "<b>KO</b>", !isOK);
    StorageTestUtil.CURRENT_STORAGE = null;
    StorageTestUtil.TESTS.clear();
    StorageTestUtil.line = 0;
    return isOK;
  }

}
