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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class StorageChangeEvent extends GwtEvent<StorageChangeEvent.Handler> {

  public enum Level {
    STRING, OBJECT
  }
  
  public enum ChangeType {
    CLEAR, PUT, REMOVE
  }

  public interface Handler extends EventHandler {
    void onStorageChange(StorageChangeEvent event);
  }

  private static Type<StorageChangeEvent.Handler> TYPE;

  public static Type<StorageChangeEvent.Handler> getType() {
    if (TYPE == null) {
      TYPE = new Type<StorageChangeEvent.Handler>();
    }
    return TYPE;
  }

  private ChangeType changeType;
  private String data;
  private StorageKey<?> key;
  private String oldData;
  private Object oldValue;
  private Object value;

  StorageChangeEvent(ChangeType changeType, StorageKey<?> key, Object value, Object oldValue,
      String data, String oldData) {
    super();
    this.changeType = changeType;
    this.key = key;
    this.value = value;
    this.oldValue = oldValue;
    this.data = data;
    this.oldData = oldData;
  }

  @Override
  public Type<StorageChangeEvent.Handler> getAssociatedType() {
    return TYPE;
  }

  public ChangeType getChangeType() {
    return changeType;
  }

  public String getData() {
    return data;
  }

  public StorageKey<?> getKey() {
    return key;
  }

  public String getOldData() {
    return oldData;
  }

  public Object getOldValue() {
    return oldValue;
  }

  public Object getValue() {
    return value;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onStorageChange(this);
  }
}
