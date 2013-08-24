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

package com.seanchenxi.gwt.storage.shared;

import java.io.Serializable;

/**
 * Created by: Xi
 */
public class RpcTestMapKey implements Serializable{

  public enum MapKey{
    KEY_1, KEY_2, KEY_3, KEY_4, KEY_5
  }

  private MapKey key;

  public RpcTestMapKey(){}

  public RpcTestMapKey(MapKey key){
    this.key = key;
  }

  public MapKey getKey(){
    return key;
  }

  public void setKey(MapKey key){
    this.key = key;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof RpcTestMapKey)) return false;

    RpcTestMapKey that = (RpcTestMapKey)o;

    if(key != that.key) return false;

    return true;
  }

  @Override
  public int hashCode(){
    return key != null ? key.hashCode() : 0;
  }
}
