package com.seanchenxi.gwt.storage.rebind;

import java.io.Serializable;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

/**
 * User: Xi
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
      if (method.getParameters().length == 1) {
        setDynamic(true);
        setKeyValueType(method.getParameters()[0].getType().isClassOrInterface());
      }
    }

    public void setKeyAnnotation(StorageKeyProvider.Key keyMeta){
      if(keyMeta != null){
        setStaticKeyValue(keyMeta.value());
        setKeyPrefix(keyMeta.prefix());
        setKeySuffix(keyMeta.suffix());
        setKeyScope(keyMeta.scope());
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

    public void setKeyScope(StorageKeyProvider.Scope keyScope) {
      toReturn.keyScope = keyScope;
    }

    public void setDynamic(boolean dynamic) {
      toReturn.dynamic = dynamic;
    }

    public void setKeyClazz(JClassType keyClazz) {
      if(OBJECT_TYPE == null){
        OBJECT_TYPE = keyClazz.getOracle().findType(Serializable.class.getName());
      }
      toReturn.keyClazz = isPrimitive(keyClazz) ? keyClazz : OBJECT_TYPE;
    }

    private boolean isPrimitive(JType clazz){
      if(clazz == null){
        return false;
      }else if(clazz.isPrimitive() != null || String.class.getName().equalsIgnoreCase(clazz.getQualifiedSourceName())){
        return true;
      }else if(clazz.isArray() != null){
        return isPrimitive(clazz.isArray().getComponentType());
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

  private StorageKeyProvider.Scope keyScope = StorageKeyProvider.Scope.ALL;
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

  public StorageKeyProvider.Scope getKeyScope() {
    return keyScope;
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
