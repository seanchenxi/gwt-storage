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

import java.util.HashMap;
import java.util.Map;

/**
 * Provides base implementations of StorageKeyProvider methods.
 */
public abstract class AbstractStorageKeyProvider implements StorageKeyProvider {

  private static final Map<String, StorageKey<?>> KEY_MAP;

  static {
    KEY_MAP = new HashMap<String, StorageKey<?>>();
  }

  @SuppressWarnings("unchecked")
  public static <T, V extends T> StorageKey<V> createIfAbsent(String key, Class<T> clazz) {
    StorageKey<?> storageKey = KEY_MAP.get(key);
    if (null == storageKey) {
      KEY_MAP.put(key, storageKey = new StorageKey<V>(key, clazz));
    }
    try {
      return (StorageKey<V>) storageKey;
    } catch (ClassCastException e) {
      throw new IllegalStateException("The storage key " + key + " was already assigned to another type ", e);
    }
  }
}
