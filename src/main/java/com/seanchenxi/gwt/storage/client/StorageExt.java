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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.seanchenxi.gwt.storage.client.cache.StorageCache;
import com.seanchenxi.gwt.storage.shared.StorageUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Extends the GWT HTML5 Storage API, by adding <b>Object Value</b> Support.
 *
 *
 * <p>
 * You can obtain a Storage by either invoking
 * {@link #getLocalStorage()} or
 * {@link #getSessionStorage()}.
 * </p>
 *
 *
 * <p>
 * If Web Storage is NOT supported in the browser, these methods return <code>
 * null</code>.
 * </p>
 *
 *
 * <p>
 * Note: Storage events into other windows are not supported.
 * </p>
 *
 *
 * <p>
 * This may not be supported on all browsers.
 * </p>
 *
 *
 * @see <a href="http://www.w3.org/TR/webstorage/#storage-0">W3C Web Storage - Storage</a>
 * @see <a href="http://caniuse.com/#feat=namevalue-storage">Can I use... - Web Storage</a>
 * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideHtml5Storage.html">GWT Developer's Guide - Client-side Storage (Web Storage)</a>
 * @see <a href="
 *      http://www.gwtproject.org/javadoc/latest/com/google/gwt/storage/client/Storage.html">
 *      com.google.gwt.storage.client.Storage</a>
 */
public final class StorageExt {

  private static StorageExt localStorage;
  private static StorageExt sessionStorage;

  /**
   * Returns a Local Storage.
   *
   * @return the localStorage instance, or <code>null</code> if Web Storage is NOT supported.
   */
  public static StorageExt getLocalStorage() {
    if (localStorage == null && Storage.isLocalStorageSupported()) {
      localStorage = new StorageExt(Storage.getLocalStorageIfSupported());
    }
    return localStorage;
  }

  /**
   * Returns a Session Storage.
   *
   * @return the sessionStorage instance, or <code>null</code> if Web Storage is NOT supported.
   */
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

  /**
   * This class can never be instantiated externally. Use
   * {@link #getLocalStorage()} ()} or
   * {@link #getSessionStorage()} ()} instead.
   */
  private StorageExt(Storage storage) {
    assert storage != null : "Storage can not be null, check your browser's HTML 5 support state.";
    this.storage = storage;
    this.cache = GWT.create(StorageCache.class);
    this.eventLevel = StorageChangeEvent.Level.STRING;
  }

  /**
   * Registers an event handler for {@link StorageChangeEvent}
   *
   * @param handler an event handler instance
   * @return {@link HandlerRegistration} used to remove this handler
   */
  public HandlerRegistration addStorageChangeHandler(final StorageChangeEvent.Handler handler) {
    if(handler == null)
      throw new IllegalArgumentException("Handler can not be null");
    ensureHandlerSet().add(handler);
    return new HandlerRegistration() {
      @Override
      public void removeHandler() {
        if (handlers != null) {
          handlers.remove(handler);
          if (handlers.isEmpty()) {
            handlers = null;
          }
        }
      }
    };
  }

  /**
   * Removes all items in the Storage, and its cache if activated
   *
   * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-clear">W3C Web
   *      Storage - Storage.clear()</a>
   */
  public void clear() {
    storage.clear();
    cache.clear();
    fireEvent(StorageChangeEvent.ChangeType.CLEAR, null, null, null, null, null);
  }

  /**
   * Clear all cached serialized object from configured {@link StorageCache}
   */
  public void clearCache() {
    cache.clear();
  }

  /**
   * Test if this storage contains a value for the specified key.
   *
   * <p>
   * {@link StorageKeyFactory} is preferred to get a {@link StorageKey} instance for primitive types.
   * </p>
   *
   * @param key the key whose presence in this storage is to be tested
   * @param <T> the type of stored value
   * @return <tt>true</tt> if this storage contains a value for the specified key.
   */
  public <T> boolean containsKey(StorageKey<T> key) {
    return storage.getItem(key.name()) != null;
  }

  /**
   * Returns the value in the Storage associated with the specified key,
   * or {@code null} if this Storage contains no mapping for the key.
   * <p>
   * Note : Deserialization will be performed to return a correct value type. <br/>
   * {@link StorageKeyFactory} is preferred to get a {@link StorageKey} instance for primitive types.
   * </p>
   *
   * @param key the key to a value in the Storage
   * @param <T> the type of stored value
   * @return the value associated with the given key
   * @throws SerializationException
   * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-getitem">W3C Web
   *      Storage - Storage.getItem(k)</a>
   */
  public <T> T get(StorageKey<T> key) throws SerializationException {
    T item = cache.get(key);
    if (item == null) {
      item = StorageUtils.deserialize(key.getClazz(), storage.getItem(key.name()));
      cache.put(key, item);
    }
    return item;
  }

  /**
   * Get directly the stored string value associated with the specified key.
   *
   * <p>
   * Note : No deserialization will be performed
   * </p>
   *
   * @param key the key to a value in the Storage
   * @return the stored string value associated with the given key
   * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-getitem">W3C Web
   *      Storage - Storage.getItem(k)</a>
   */
  public String getString(String key) {
    return storage.getItem(key);
  }

  /**
   * Returns the key at the specified index.
   *
   * @param index the index of the key
   * @return the key at the specified index in this Storage
   * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-key">W3C Web
   *      Storage - Storage.key(n)</a>
   */
  public String key(int index) {
    return storage.key(index);
  }

  /**
   * Store the specified value with the specified key in this storage.
   *
   * <p>
   * Note: <code>null</code> value is not allowed. <br/>
   * If the storage previously contained a mapping for the key, the old
   * value is replaced.<br/>
   * {@link StorageKeyFactory} is preferred to get a {@link StorageKey} instance for primitive types.
   * </p>
   *
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   * @throws SerializationException 
   * @throws StorageQuotaExceededException
   */
  public <T> void put(StorageKey<T> key, T value) throws SerializationException, StorageQuotaExceededException {
    if(value == null){
      throw new NullPointerException();
    }
    try {
      String data = StorageUtils.serialize(key.getClazz(), value);
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

  /**
   * Removes the record for the specified key from this storage if present.
   *
   * <p>
   * {@link StorageKeyFactory} is preferred to get a {@link StorageKey} instance for primitive types
   * </p>
   *
   * @param key key whose mapping is to be removed from the map
   * @param <T> the type of stored value
   */
  public <T extends Serializable> void remove(StorageKey<T> key) {
    String data = storage.getItem(key.name());
    storage.removeItem(key.name());
    T value = cache.remove(key);
    fireEvent(StorageChangeEvent.ChangeType.REMOVE, key, null, value, null, data);
  }

  /**
   * Set Event Level.
   *
   * <p>
   * Note : set to {@link StorageChangeEvent.Level#STRING},
   * will prevent the deserialization process in event creation, <br/>
   * This matters only if the old value (object value) is wanted in every storage change event.
   * </p>
   *
   * @param eventLevel the event detail level
   * @see StorageChangeEvent
   */
  public void setEventLevel(StorageChangeEvent.Level eventLevel) {
    this.eventLevel = eventLevel;
  }

  /**
   * Returns the number of items in this Storage.
   *
   * @return number of items in this Storage
   * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-l">W3C Web
   *      Storage - Storage.length()</a>
   */
  public int size() {
    return storage.getLength();
  }

  /**
   * Ensure {@link StorageChangeEvent.Handler} registration set instance
   */
  private Set<StorageChangeEvent.Handler> ensureHandlerSet() {
    if (handlers == null) {
      handlers = new HashSet<StorageChangeEvent.Handler>();
    }
    return handlers;
  }

  /**
   * Fire {@link StorageChangeEvent}
   */
  private <T> void fireEvent(StorageChangeEvent.ChangeType changeType, StorageKey<T> key, T value, T oldVal, String data, String oldData) {
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (handlers != null && !handlers.isEmpty()) {
      T oldValue = oldVal;
      if (oldValue == null && oldData != null && StorageChangeEvent.Level.OBJECT.equals(eventLevel)) {    
        try {
          oldValue = StorageUtils.deserialize(key.getClazz(), data);
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
