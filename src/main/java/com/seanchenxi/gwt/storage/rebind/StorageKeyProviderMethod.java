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

package com.seanchenxi.gwt.storage.rebind;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

/**
 * Created by: Xi
 */
class StorageKeyProviderMethod {

  /**
   * Builds StorageKeyProviderMethods.
   */
  public static class Builder {

    private static JClassType OBJECT_TYPE = null;
    private StorageKeyProviderMethod toReturn = new StorageKeyProviderMethod();

    public StorageKeyProviderMethod build() {
      try {
        return toReturn;
      } finally {
        toReturn = null;
      }
    }

    public void setMethod(JMethod method) {
      setName(method.getName());
      JType returnType = method.getReturnType();
      setReturnType(returnType.isClassOrInterface());
      setKeyClazz(returnType.isParameterized().getTypeArgs()[0]);
      int paramCount = method.getParameters().length;
      switch (paramCount){
        case 0:
          setDynamic(false);
          setStaticKeyValue(method.getName() + paramCount);
          break;
        case 1:
          setDynamic(true);
          setKeyValueType(method.getParameters()[0].getType().isClassOrInterface());
          break;
      }
    }

    public void setKeyAnnotation(StorageKeyProvider.Key keyMeta){
      if(keyMeta != null){
        String staticValue = keyMeta.value();
        if(staticValue != null && !staticValue.trim().isEmpty()){
          setStaticKeyValue(staticValue);
        }
        setKeyPrefix(keyMeta.prefix());
        setKeySuffix(keyMeta.suffix());
      }
    }

    public void setName(String name) {
      toReturn.name = name;
    }

    public void setReturnType(JClassType returnType) {
      toReturn.returnType = returnType;
    }

    public void setKeyValueType(JClassType wrapped) {
      toReturn.keyValueType = wrapped;
    }

    public void setStaticKeyValue(String staticKeyValue) {
      toReturn.staticKeyValue = staticKeyValue;
    }

    public void setKeyPrefix(String keyPrefix) {
      toReturn.keyPrefix = keyPrefix;
    }

    public void setKeySuffix(String keySuffix) {
      toReturn.keySuffix = keySuffix;
    }

    public void setDynamic(boolean dynamic) {
      toReturn.dynamic = dynamic;
    }

    public void setKeyClazz(JClassType keyClazz) {
      if(OBJECT_TYPE == null){
        OBJECT_TYPE = keyClazz.getOracle().findType(Object.class.getName());
      }
      //Since the referenced type was already verified before, we use Object.class for all non primitive/non array types
      toReturn.keyClazz = isPrimitive(keyClazz) ? keyClazz : OBJECT_TYPE;
    }

    private boolean isPrimitive(JType clazz){
      if(clazz == null){
        return false;
      }else if(clazz.isPrimitive() != null || String.class.getName().equalsIgnoreCase(clazz.getQualifiedSourceName())){
        return true;
      }else if(clazz.isArray() != null){ //Array should be considered as primitive type
        return true;
      }else{
          String qualifiedSourceName = clazz.getQualifiedSourceName();
          for (JPrimitiveType primitiveType : JPrimitiveType.values()) {
            if(primitiveType.getQualifiedBoxedSourceName().equalsIgnoreCase(qualifiedSourceName) ||
                primitiveType.getQualifiedSourceName().equalsIgnoreCase(qualifiedSourceName)){
              return true;
            }
          }
          return false;
      }
    }
  }

  private JType returnType;
  private String name;
  private boolean dynamic = false;

  private JClassType keyClazz;
  private JClassType keyValueType;
  private String staticKeyValue = "";
  private String keyPrefix = "";
  private String keySuffix = "";

  private StorageKeyProviderMethod() {
  }

  public JType getReturnType() {
    return returnType;
  }

  public String getName() {
    return name;
  }

  public boolean isDynamicKey() {
    return dynamic;
  }

  public JClassType getKeyClazz() {
    return keyClazz;
  }

  public JClassType getKeyValueType() {
    return keyValueType;
  }

  String getStaticKeyValue() {
    return staticKeyValue;
  }

  public String getKeyPrefix() {
    return keyPrefix;
  }

  public String getKeySuffix() {
    return keySuffix;
  }
}
