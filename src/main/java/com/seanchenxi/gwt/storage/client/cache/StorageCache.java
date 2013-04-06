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

package com.seanchenxi.gwt.storage.client.cache;

import com.seanchenxi.gwt.storage.client.StorageKey;

import java.io.Serializable;

/**
 * Interface for deferred binding implementation.
 *
 * <p>
 *   Default implementation is {@link StorageMemoryCache}<br/>
 * </p>
 *
 * <p>
 *   Do <b>Object <--> String</b> serialization/deserialization takes time,
 *   especially when there has frequent calls of {@link com.seanchenxi.gwt.storage.client.StorageExt#get(StorageKey)},
 *   performance problem will come out.
 * </p>
 *
 * <p>
 *   Using <b>cache</b> is always a good practice to reduce the number of serialization/deserialization.
 * </p>
 */
public interface StorageCache {

  /**
   *  Clear cache
   */
  void clear();

  /**
   * Test if this Cache contains a value.
   *
   * @param value the value whose presence in this storage is to be tested
   * @param <T> type of the given value
   * @return <tt>true</tt> if this Cache contains a cached value for the specified key.
   */
  <T extends Serializable> boolean containsValue(T value);

  /**
   * Returns the value in this Cache associated with the specified key.
   * or {@code null} if this Cache contains no cached value for the key.
   *
   * @param key the key to a value in the Cache
   * @param <T> type of value which will be returned
   * @return the cached value for the given key
   */
  <T extends Serializable> T get(StorageKey<T> key);

  /**
   * Cache the specified value with the specified key.
   * If the cache previously contained a mapping for the key, the old
   * value is replaced and returned.
   *
   * @param key key with which the specified value is to be cached
   * @param value value to be cached
   * @param <T> type of given value
   * @return the previous value cached with <tt>key</tt>, or
   *         <tt>null</tt> if there was no cache value for <tt>key</tt>.
   */
  <T extends Serializable> T put(StorageKey<T> key, T value);

  /**
   * Removes the cached value for the specified key from this cache if present.
   *
   * @param key key whose value is to be removed from the cache
   * @param <T> type of given value
   * @return the previous value cached with <tt>key</tt>, or
   *         <tt>null</tt> if there was no cache value for <tt>key</tt>.
   */
  <T extends Serializable> T remove(StorageKey<T> key);

}