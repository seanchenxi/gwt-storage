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
