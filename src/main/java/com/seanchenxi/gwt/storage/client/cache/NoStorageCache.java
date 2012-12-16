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

import com.seanchenxi.gwt.storage.client.StorageKey;

import java.io.Serializable;

/**
 * Empty implementation of {@link StorageCache}
 */
class NoStorageCache implements StorageCache {

  @Override
  public void clear() {
  }

  @Override
  public <T extends Serializable> boolean containsValue(T value) {
    return false;
  }

  @Override
  public <T extends Serializable> T get(StorageKey<T> key) {
    return null;
  }

  @Override
  public <T extends Serializable> T put(StorageKey<T> key, T value) {
    return null;
  }

  @Override
  public <T extends Serializable> T remove(StorageKey<T> key) {
    return null;
  }

}
