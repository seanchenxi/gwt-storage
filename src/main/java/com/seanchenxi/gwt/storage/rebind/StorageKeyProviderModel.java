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

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;

import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Xi
 */
class StorageKeyProviderModel {

  private static final Class<StorageKeyProvider.Scope> SCOPE_ANNOTATION = StorageKeyProvider.Scope.class;
  private static final Class<StorageKeyProvider.Key> KEY_ANNOTATION = StorageKeyProvider.Key.class;

  private final TreeLogger logger;
  private final List<StorageKeyProviderMethod> methods;

  public StorageKeyProviderModel(TreeLogger logger, JClassType providerType) {
    this.methods = new ArrayList<StorageKeyProviderMethod>();
    this.logger = logger;

    StorageKeyProvider.StorageScope providerScope = null;
    if(providerType.isAnnotationPresent(SCOPE_ANNOTATION)){
      providerScope = providerType.getAnnotation(SCOPE_ANNOTATION).value();
      logger.branch(TreeLogger.Type.DEBUG, providerType.getQualifiedSourceName() + "'s scope is defined to " + providerScope);
    }

    for (JMethod method : providerType.getOverridableMethods()) {
      JParameterizedType returnType = method.getReturnType().isParameterized();
      if(returnType == null || !returnType.getBaseType().getQualifiedSourceName().equals(StorageKey.class.getName())){
        continue;
      }

      StorageKeyProviderMethod keyMethod = buildKeyMethod(providerType, providerScope, method);
      methods.add(keyMethod);
    }
  }

  private StorageKeyProviderMethod buildKeyMethod(JClassType providerType, StorageKeyProvider.StorageScope providerScope, JMethod method) {
    StorageKeyProviderMethod.Builder builder = new StorageKeyProviderMethod.Builder();
    builder.setMethod(method);

    if(method.isAnnotationPresent(KEY_ANNOTATION)) {
      builder.setKeyAnnotation(method.getAnnotation(KEY_ANNOTATION));
    }

    if(method.isAnnotationPresent(SCOPE_ANNOTATION)) {
      StorageKeyProvider.StorageScope keyScope = method.getAnnotation(SCOPE_ANNOTATION).value();
      if(providerScope != null && !keyScope.equals(providerScope)){
        String warn = method.getReadableDeclaration() + " has different scope definition with " + providerType.getQualifiedSourceName();
        logger.branch(TreeLogger.Type.WARN, warn + ", StorageKeyProvider's scope definition (" + providerScope + ") will be used !");
        builder.setKeyScope(providerScope);
      }else{
        builder.setKeyScope(keyScope);
      }
    }

    return builder.build();
  }

  List<StorageKeyProviderMethod> getMethods() {
    return Collections.unmodifiableList(methods);
  }
}
