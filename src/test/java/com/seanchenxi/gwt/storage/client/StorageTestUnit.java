/*
 * Copyright 2012 Xi CHEN
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.seanchenxi.gwt.storage.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Created by: Xi
 */
public class StorageTestUnit {

  private static int line = 0;

  public static void assertEquals(String name, Object expected, Object value) {
    if (expected == value || expected.equals(value)) {
      trace(name + " - assertEquals succeed.");
    } else {
      trace("<span style=\"color:red;\">" + name + "-  assertEquals error: expected=" + String.valueOf(expected) + ", but given=" + String.valueOf(value) + "</span>");
    }
  }

  public static void assertTrue(String name, boolean value) {
    if (value) {
      trace(name + " - assertTrue succeed.");
    } else {
      trace("<span style=\"color:red;\">" + name + "-  assertTrue failed.</span>");
    }
  }

  public static void trace(String str) {
    RootPanel.get().add(new HTML((line++) + ". " + str));
  }

}
