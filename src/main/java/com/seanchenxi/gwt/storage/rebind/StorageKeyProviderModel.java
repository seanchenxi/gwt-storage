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
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;

import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by: Xi
 */
class StorageKeyProviderModel {

  private static final Class<StorageKeyProvider.Key> KEY_ANNOTATION = StorageKeyProvider.Key.class;

  private final TreeLogger logger;
  private final List<StorageKeyProviderMethod> methods;
  private final JClassType providerType;

  public StorageKeyProviderModel(TreeLogger logger, JClassType providerType) {
    this.providerType = providerType;
    this.methods = new ArrayList<StorageKeyProviderMethod>();
    this.logger = logger;
  }

  public void loadMethods() throws UnableToCompleteException {
    for (JMethod method : providerType.getMethods()) {
      JParameterizedType returnType = method.getReturnType().isParameterized();
      if(returnType == null || !returnType.getBaseType().getQualifiedSourceName().equals(StorageKey.class.getName())){
        logger.branch(TreeLogger.Type.INFO, "method "+ method.getReadableDeclaration() +" will be ignored.");
        continue;
      }

      StorageKeyProviderMethod keyMethod = buildKeyMethod(method);
      methods.add(keyMethod);
    }
  }

  private StorageKeyProviderMethod buildKeyMethod(JMethod method) throws UnableToCompleteException {
    logger.branch(TreeLogger.Type.DEBUG, "buildKeyMethod with method=" + method.getReadableDeclaration());
    if(method.getParameters().length > 1){
      logger.branch(TreeLogger.Type.ERROR, "key creation method can only have one parameter");
      throw new UnableToCompleteException();
    }

    StorageKeyProviderMethod.Builder builder = new StorageKeyProviderMethod.Builder();
    builder.setMethod(method);

    if(method.isAnnotationPresent(KEY_ANNOTATION)) {
      builder.setKeyAnnotation(method.getAnnotation(KEY_ANNOTATION));
    }

    return builder.build();
  }

  List<StorageKeyProviderMethod> getMethods() {
    return Collections.unmodifiableList(methods);
  }
}
