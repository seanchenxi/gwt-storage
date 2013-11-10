package com.seanchenxi.gwt.storage.rebind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

/**
 * User: Xi
 */
class StorageKeyProviderModel {

  private final TypeOracle oracle;
  private final TreeLogger logger;
  private final List<StorageKeyProviderMethod> methods;

  public StorageKeyProviderModel(TreeLogger logger, JClassType providerType) {
    this.logger = logger;
    this.oracle = providerType.getOracle();
    this.methods = new ArrayList<StorageKeyProviderMethod>();

    for (JMethod method : providerType.getOverridableMethods()) {
      JParameterizedType returnType = method.getReturnType().isParameterized();
      if(returnType == null || !returnType.getBaseType().getQualifiedSourceName().equalsIgnoreCase(StorageKey.class.getName())){
        continue;
      }

      StorageKeyProviderMethod.Builder builder = new StorageKeyProviderMethod.Builder();
      builder.setMethod(method);
      Class<StorageKeyProvider.Key> keyAnnotation = StorageKeyProvider.Key.class;
      if(method.isAnnotationPresent(keyAnnotation)) {
        builder.setKeyAnnotation(method.getAnnotation(keyAnnotation));
      }else{
        builder.setKeyScope(StorageKeyProvider.Scope.ALL);
      }
      methods.add(builder.build());
    }
  }

  List<StorageKeyProviderMethod> getMethods() {
    return Collections.unmodifiableList(methods);
  }
}
