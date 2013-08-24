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
public class RpcTestMapValue implements Serializable{

  public static class MapValue implements Serializable{

    private String value;

    public MapValue(){}

    public MapValue(String value){
      this.value = value;
    }

    public String getValue(){
      return value;
    }

    public void setValue(String value){
      this.value = value;
    }

    @Override
    public boolean equals(Object o){
      if(this == o) return true;
      if(!(o instanceof MapValue)) return false;

      MapValue mapValue = (MapValue)o;

      if(value != null ? !value.equals(mapValue.value) : mapValue.value != null) return false;

      return true;
    }

    @Override
    public int hashCode(){
      return value != null ? value.hashCode() : 0;
    }
  }

  private MapValue value;

  public RpcTestMapValue(){}

  public RpcTestMapValue(MapValue value){
    this.value = value;
  }

  public MapValue getValue(){
    return value;
  }

  public void setValue(MapValue value){
    this.value = value;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof RpcTestMapValue)) return false;

    RpcTestMapValue that = (RpcTestMapValue)o;

    if(value != null ? !value.equals(that.value) : that.value != null) return false;

    return true;
  }

  @Override
  public int hashCode(){
    return value != null ? value.hashCode() : 0;
  }
}
