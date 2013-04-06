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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a Storage Change Event.
 *
 * <p>
 *   A Storage Change Event is fired when a storage area changes
 * </p>
 *
 * <p>
 *   <span style="color:red">
 *     Note: If the {@link StorageExt#eventLevel} is set to {@link StorageChangeEvent.Level#STRING},
 *     the value returned by {@link #getOldValue()} could be null</code>, if it wasn't cached before
 *   </span>
 * </p>
 *
 * @see Handler
 */
public class StorageChangeEvent extends GwtEvent<StorageChangeEvent.Handler> {

  /**
   * Level of Storage Change Event information
   */
  public enum Level {
    /**
     * Prevents the deserialization in {@link StorageChangeEvent} creation process.
     */
    STRING,
    /**
     * Enables the deserialization in {@link StorageChangeEvent} creation process.
     */
    OBJECT
  }

  /**
   * Type of Storage Change
   */
  public enum ChangeType {
    CLEAR, PUT, REMOVE
  }

  /**
   * Represents an Event handler for {@link StorageChangeEvent}s.
   *
   * <p>
   *   Apply your StorageChangeEventHandler using
   *   {@link StorageExt#addStorageChangeHandler(StorageChangeEvent.Handler)}
   * </p>
   *
   * @see StorageChangeEvent
   */
  public interface Handler extends EventHandler {
    /**
     * Invoked when a {@link StorageChangeEvent} is fired.
     *
     * @param event the fired StorageChangeEvent
     */
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

  /**
   * Returns the change type
   *
   * @return the change type
   */
  public ChangeType getChangeType() {
    return changeType;
  }

  /**
   * Returns the new serialized string value of the key being changed.
   *
   * @return the new serialized string value of the key being changed
   */
  public String getData() {
    return data;
  }

  /**
   *  Returns the key being changed.
   *
   * @return the key being changed
   */
  public StorageKey<?> getKey() {
    return key;
  }

  /**
   * Returns the old serialized string value of the key being changed.
   *
   * @return the old serialized string value of the key being changed
   */
  public String getOldData() {
    return oldData;
  }

  /**
   * Returns the old value of the key being changed.
   *
   * @return the old value of the key being changed
   */
  public Object getOldValue() {
    return oldValue;
  }

  /**
   * Returns the new value of the key being changed.
   *
   * @return the new value of the key being changed
   */
  public Object getValue() {
    return value;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onStorageChange(this);
  }
}
