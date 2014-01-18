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
import java.util.HashMap;
import java.util.Map;

/**
 * User: Xi
 */
public abstract class AbstractStorageKeyProvider implements StorageKeyProvider {

  private static final Map<StorageScope, Map<String, StorageKey<? extends Serializable>>> KEY_MAP;

  static {
    KEY_MAP = new HashMap<StorageScope, Map<String, StorageKey<? extends Serializable>>>();
    for (StorageScope scope : StorageScope.values()) {
      KEY_MAP.put(scope, new HashMap<String, StorageKey<? extends Serializable>>());
    }
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Serializable> StorageKey<T> createIfAbsent(String key, StorageScope scope, Class<? super T> clazz) {
    final Map<String, StorageKey<? extends Serializable>> scopeKeyMap = KEY_MAP.get(scope);
    StorageKey<? extends Serializable> storageKey;
    if (null == (storageKey = scopeKeyMap.get(key))) {
      scopeKeyMap.put(key, storageKey = new StorageKey<T>(key, clazz));
    }
    try {
      return (StorageKey<T>) storageKey;
    } catch (ClassCastException e) {
      throw new IllegalStateException("The storage key " + key + " was already assigned to another type ", e);
    }
  }
}
