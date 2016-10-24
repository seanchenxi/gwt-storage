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
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

import java.io.Serializable;
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
  private final JGenericType storageKeyGenericType;
  private final JClassType serializableIntf;
  private final JClassType isSerializableIntf;

  public StorageKeyProviderModel(TreeLogger logger, JClassType providerType) {
    this.providerType = providerType;
    this.storageKeyGenericType = providerType.getOracle().findType(StorageKey.class.getCanonicalName()).isGenericType();
    this.serializableIntf = providerType.getOracle().findType(Serializable.class.getCanonicalName()).isInterface();
    this.isSerializableIntf = providerType.getOracle().findType(IsSerializable.class.getCanonicalName()).isInterface();
    this.methods = new ArrayList<>();
    this.logger = logger;
  }

  public void loadMethods() throws UnableToCompleteException {
    for (JMethod method : providerType.getOverridableMethods()) {
      if(!validateMethodDef(method)){
        continue;
      }
      StorageKeyProviderMethod keyMethod = buildKeyMethod(method);
      methods.add(keyMethod);
    }
  }

  List<StorageKeyProviderMethod> getMethods() {
    return Collections.unmodifiableList(methods);
  }

  private StorageKeyProviderMethod buildKeyMethod(JMethod method) throws UnableToCompleteException {
    logger.branch(TreeLogger.Type.DEBUG, "buildKeyMethod with method=" + method.getReadableDeclaration());
    StorageKeyProviderMethod.Builder builder = new StorageKeyProviderMethod.Builder();
    builder.setMethod(method);
    if(method.isAnnotationPresent(KEY_ANNOTATION)) {
      builder.setKeyAnnotation(method.getAnnotation(KEY_ANNOTATION));
    }
    return builder.build();
  }

  private boolean validateMethodDef(JMethod method) throws UnableToCompleteException {
    if(!method.getEnclosingType().equals(providerType)){
      return false;
    }
    JParameterizedType returnType = method.getReturnType().isParameterized();
    boolean isCorrectReturnType = returnType != null  && returnType.isAssignableTo(storageKeyGenericType);
    JClassType valueType = isCorrectReturnType ? returnType.getTypeArgs()[0] : null;
    if(!isValideType(valueType)){
      logger.branch(TreeLogger.Type.ERROR, "method "+ method.getReadableDeclaration() +"'s returned store type is not extends to IsSerializable nor Serializable");
      throw new UnableToCompleteException();
    }

    int length = method.getParameters().length;
    if(length > 1){
      logger.branch(TreeLogger.Type.WARN, "method "+ method.getReadableDeclaration() +" should only have one or zero parameter");
      throw new UnableToCompleteException();
    }
    return true;
  }

  private boolean isValideType(JType type){
    if(type == null)
      return false;

    if(type.isInterface() != null)
      return false;

    if(type.isPrimitive() != null)
      return true;

    JClassType aClass = type.isClass();
    if(aClass != null && (aClass.isAssignableTo(serializableIntf) || aClass.isAssignableTo(isSerializableIntf))){
      return true;
    }

    JArrayType array = type.isArray();
    if(array == null) return false;
    return isValideType(array.getComponentType());
  }
}
