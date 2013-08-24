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
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by: Xi
 */
public class StorageTestUnit {

  private static final DateTimeFormat format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
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

  public static void assertEquals(String name, Object expected, Object value) {
    if ((expected == null || value == null) || expected == value || expected.equals(value)) {
      trace(name + " - assertEquals succeed.");
    } else if(expected.getClass().isArray() && value.getClass().isArray() &&
      Arrays.equals((Object[])expected, (Object[])value)){
      trace(name + " - assertEquals succeed.");
    }else if(expected.getClass().isArray() && value.getClass().isArray()) {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList((Object[])expected)) + ", but given=" + String.valueOf(Arrays.asList((Object[])value)), true);
    }else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(expected) + ", but given=" + String.valueOf(value), true);
    }
  }

  public static void assertEquals(String name, boolean[] expected, boolean[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertEquals(String name, float[] expected, float[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertEquals(String name, int[] expected, int[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertEquals(String name, short[] expected, short[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertEquals(String name, double[] expected, double[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertEquals(String name, long[] expected, long[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertEquals(String name, byte[] expected, byte[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertEquals(String name, char[] expected, char[] value) {
    if (Arrays.equals(expected, value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace(name + "-  assertEquals error: expected=" + String.valueOf(Arrays.asList(expected)) + ", but given=" + String.valueOf(Arrays.asList(value)), true);
    }
  }

  public static void assertTrue(String name, boolean value) {
    if (value) {
      trace(name + " - assertTrue succeed.");
    } else {
      trace(name + "-  assertTrue failed.", true);
    }
  }

  public static void trace(String str) {
    trace(str, false);
  }

  public static void trace(String str, boolean isError) {
    if("==".equalsIgnoreCase(str)){
      TRACE.add(new HTML("<br/>"));
    } else{
      TRACE.add(
        new HTML("&nbsp;" + (line++) + ".&nbsp;<span style=\"color:" + (isError ? "red" : "green") + ";\">"
          + str +
          "</span>"));
    }
  }

  public static void event(String str, boolean isError) {
    if("==".equalsIgnoreCase(str)){
      EVENT.add(new HTML("<br/>"));
    } else{
      EVENT.add(
        new HTML("&nbsp;" + format.format(new Date()) + "&nbsp;&nbsp;<span style=\"color:"+ (isError ? "red" : "black") + ";\">"
          + str +
          "</span>"));
    }
  }

  public static int getLineNumber(){
    return line;
  }

}
