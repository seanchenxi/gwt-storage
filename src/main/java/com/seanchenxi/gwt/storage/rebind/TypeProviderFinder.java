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

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;

import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: Xi
 */
final class TypeProviderFinder extends StorageTypeFinder {

  TypeProviderFinder(GeneratorContext context, TreeLogger logger) {
    super(context, logger);
  }

  @Override
  public Set<JType> findStorageTypes() {
    Set<JType> serializables = new HashSet<>();

    JClassType keyProviderIntf = typeOracle.findType(StorageKeyProvider.class.getName());
    JClassType[] keyProviderTypes = keyProviderIntf.getSubtypes();
    for (JClassType keyProviderType : keyProviderTypes) {
      for (JMethod method : keyProviderType.getMethods()) {
        if(method.isStatic() || method.getParameters().length > 1){
          continue;
        }
        JParameterizedType storageKeyType = method.getReturnType().isParameterized();
        if (storageKeyType != null) {
          JClassType type = storageKeyType.getTypeArgs()[0];
          addIfIsValidType(serializables, type);
        }
      }
    }
    return serializables;
  }
}
