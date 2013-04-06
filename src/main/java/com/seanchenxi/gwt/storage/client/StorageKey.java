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

/**
 * The Storage Key
 *
 * @param <T> the associate value's type
 */
public class StorageKey<T extends Serializable> {

  private final Class<? super T> clazz;
  private final String name;

  /**
   * Create an StorageKey instance
   *
   * <p>
   *   The <b>clazz</b> only used to distinguish primitive type and object type.<br/>
   *   For all primitive types, the <b>clazz</b> should equals to <b><T></b>.<br/>
   *   Otherwise, use {@link Serializable} as <b>clazz</b>, and real class type for <b><T></b>
   * </p>
   *
   * @param name the key's name, used to store value in storage, should be unique
   * @param clazz the future associate value's class type for serialization
   * @see StorageKeyFactory
   */
  public StorageKey(String name, Class<? super T> clazz) {
    if(name == null || name.trim().length() < 1){
      throw new IllegalArgumentException("StorageKey's name can not be null or empty");
    }
    if(clazz == null){
      throw new IllegalArgumentException("StorageKey's class type can not be null");
    }
    this.name = name;
    this.clazz = clazz;
  }

  /**
   * Get associate value's class type
   *
   * @return the associate value's class type
   */
  public Class<? super T> getClazz() {
    return clazz;
  }

  /**
   * Get the Storage Key's name
   *
   * @return the key's name
   */
  public String name() {
    return this.name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StorageKey other = (StorageKey) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return name;
  }
  
}