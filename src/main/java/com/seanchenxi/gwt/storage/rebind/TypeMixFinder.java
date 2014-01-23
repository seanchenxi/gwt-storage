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

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by: Xi
 */
final class TypeMixFinder extends StorageTypeFinder {

  private final List<StorageTypeFinder> typeFinders;

  TypeMixFinder(List<StorageTypeFinder> typeFinders){
    this.typeFinders = typeFinders;
  }

  @Override
  public Set<JType> findStorageTypes() throws UnableToCompleteException{
    Set<JType> serializables = new HashSet<JType>();
    for(StorageTypeFinder finder : typeFinders){
      serializables.addAll(finder.findStorageTypes());
    }
    return serializables;
  }

}
