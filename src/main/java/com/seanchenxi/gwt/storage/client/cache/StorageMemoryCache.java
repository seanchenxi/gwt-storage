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

package com.seanchenxi.gwt.storage.client.cache;

import java.io.Serializable;
import java.util.HashMap;

import com.seanchenxi.gwt.storage.client.StorageKey;

/**
 * Default implementation of {@link StorageCache}
 */
class StorageMemoryCache implements StorageCache {

  private final HashMap<StorageKey<? extends Serializable>, Object> map;

  public StorageMemoryCache() {
    map = new HashMap<StorageKey<? extends Serializable>, Object>();
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public <T extends Serializable> boolean containsValue(T value) {
    return map.containsValue(value);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Serializable> T get(StorageKey<T> key) {
    Object val = map.get(key);
    return val != null ? (T) val : null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Serializable> T put(StorageKey<T> key, T value) {
    Object old = map.put(key, value);
    return old != null ? (T) old : null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Serializable> T remove(StorageKey<T> key) {
    Object val = map.remove(key);
    return val != null ? (T) val : null;
  }
}