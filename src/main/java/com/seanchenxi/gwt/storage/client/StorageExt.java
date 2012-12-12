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

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.cache.StorageCache;
import com.seanchenxi.gwt.storage.client.serializer.StorageSerializer;

public final class StorageExt {

  private static StorageExt localStorage;
  private static StorageExt sessionStorage;
  private static final StorageSerializer TYPE_SERIALIZER = GWT.create(StorageSerializer.class);

  public static StorageExt getLocalStorage() {
    if (localStorage == null && Storage.isLocalStorageSupported()) {
      localStorage = new StorageExt(Storage.getLocalStorageIfSupported());
    }
    return localStorage;
  }

  public static StorageExt getSessionStorage() {
    if (sessionStorage == null && Storage.isSessionStorageSupported()) {
      sessionStorage = new StorageExt(Storage.getSessionStorageIfSupported());
    }
    return sessionStorage;
  }

  private StorageChangeEvent.Level eventLevel;
  private Set<StorageChangeEvent.Handler> handlers;
  private final StorageCache cache;
  private final Storage storage;
  
  private StorageExt(Storage storage) {
    assert storage != null : "Storage can not be null, check your browser's HTML 5 support state.";
    this.storage = storage;
    this.cache = GWT.create(StorageCache.class);
    this.eventLevel = StorageChangeEvent.Level.STRING;
  }

  public HandlerRegistration addStorageChangeHandler(final StorageChangeEvent.Handler handler) {
    ensureHandlerSet().add(handler);
    return new HandlerRegistration() {
      @Override
      public void removeHandler() {
        if (handlers != null && handler != null) {
          handlers.remove(handler);
          if (handlers.isEmpty()) {
            handlers = null;
          }
        }
      }
    };
  }

  /**
   * Removes all items in the Storage, and its cache it activated
   *
   * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-clear">W3C Web
   *      Storage - Storage.clear()</a>
   */
  public void clear() {
    storage.clear();
    cache.clear();
    fireEvent(StorageChangeEvent.ChangeType.CLEAR, null, null, null, null, null);
  }

  public void clearCache() {
    cache.clear();
  }

  public <T> boolean containsKey(StorageKey<T> key) {
    return storage.getItem(key.name()) != null;
  }
  
  public <T> T get(StorageKey<T> key) throws SerializationException {
    T item = cache.get(key);
    if (item == null) {
      item = TYPE_SERIALIZER.deserialize(key.getClazz(), storage.getItem(key.name()));
      cache.put(key, item);
    }
    return item;
  }

  public String getString(String key) {
    return storage.getItem(key);
  }

  public String key(int index) {
    return storage.key(index);
  }

  /**
   * Sets the value in the Storage associated with the specified key to the
   * specified data.
   *
   * Note: The empty string may not be used as a key. And NULL value is not allowed.
   * 
   * @param key the key to a value in the Storage
   * @param value the value associated with the key
   * @throws SerializationException 
   * @throws StorageQuotaExceededException
   * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-setitem">W3C Web
   *      Storage - Storage.setItem(k,v)</a>
   */
  public <T> void put(StorageKey<T> key, T value) throws SerializationException,
      StorageQuotaExceededException {
    if(value == null){
      throw new NullPointerException();
    }
    try {
      String data = TYPE_SERIALIZER.serialize(key.getClazz(), value);
      // Update store and cache
      String oldData = storage.getItem(key.name());
      storage.setItem(key.name(), data);
      T oldValue = cache.put(key, value);
      fireEvent(StorageChangeEvent.ChangeType.PUT, key, value, oldValue, data, oldData);
    } catch (JavaScriptException e) {
      String msg = e.getMessage();
      if (msg != null && msg.contains("QUOTA") && msg.contains("DOM")) {
        throw new StorageQuotaExceededException(key, e);
      }
      throw e;
    }
  }

  public <T> void remove(StorageKey<T> key) {
    String data = storage.getItem(key.name());
    storage.removeItem(key.name());
    T value = cache.remove(key);
    fireEvent(StorageChangeEvent.ChangeType.REMOVE, key, null, value, null, data);
  }

  public void setEventLevel(StorageChangeEvent.Level eventLevel) {
    this.eventLevel = eventLevel;
  }

  public int size() {
    return storage.getLength();
  }

  private Set<StorageChangeEvent.Handler> ensureHandlerSet() {
    if (handlers == null) {
      handlers = new HashSet<StorageChangeEvent.Handler>();
    }
    return handlers;
  }

  private void fireEvent(StorageChangeEvent.ChangeType changeType, StorageKey<?> key, Object value,
      Object oldVal, String data, String oldData) {
    UncaughtExceptionHandler ueh = com.google.gwt.core.client.GWT.getUncaughtExceptionHandler();
    if (handlers != null && !handlers.isEmpty()) {
      Object oldValue = oldVal;
      if (oldValue == null && oldData != null && StorageChangeEvent.Level.OBJECT.equals(eventLevel)) {    
        try {
          oldValue = TYPE_SERIALIZER.deserialize(key.getClazz(), data);
        } catch (SerializationException e) {
          if (ueh != null)
            ueh.onUncaughtException(e);
          oldValue = null;
        }
      }

      final StorageChangeEvent event = new StorageChangeEvent(changeType, key, value, oldValue, data, oldData);
      for (StorageChangeEvent.Handler handler : handlers) {
        try{
          handler.onStorageChange(event);
        }catch(Exception e){
          if(ueh != null){
            ueh.onUncaughtException(e);
          }
        }
      }
    }
  }

}
