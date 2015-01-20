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

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JRealClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

import com.seanchenxi.gwt.storage.client.StorageKeyProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by: Xi
 */
public abstract class StorageTypeFinder {

  public static final String SERIALIZATION_CONFIG = "storage-serialization.xml";

  public static final String PROP_RPC_BLACKLIST = "rpc.blacklist";
  public static final String PROP_STORAGE_BLACKLIST = "storage.blacklist";
  public static final String PROP_TYPE_FINDER = "storage.type.finder";
  public static final List<String> TYPE_FINDER_VALUES = Arrays.asList("rpc", "xml", "mix", "none");

  public static StorageTypeFinder getInstance(GeneratorContext context, TreeLogger logger) throws UnableToCompleteException{
    final List<StorageTypeFinder> typeFinders = getStorageTypeFinders(context, logger);
    switch (typeFinders.size()){
      case 0:
        return new TypeRpcFinder(context, logger);
      case 1:
        return typeFinders.get(0);
      default:
        return new TypeMixFinder(context, logger, typeFinders);
    }
  }

  private static List<StorageTypeFinder> getStorageTypeFinders(GeneratorContext context, TreeLogger logger) throws UnableToCompleteException {
    final List<StorageTypeFinder> typeFinders = new ArrayList<StorageTypeFinder>();

    JClassType keyProviderIntf = context.getTypeOracle().findType(StorageKeyProvider.class.getName());
    if(keyProviderIntf.getSubtypes() != null && keyProviderIntf.getSubtypes().length > 1){
      typeFinders.add(new TypeProviderFinder(context, logger));
    }

    PropertyOracle propertyOracle = context.getPropertyOracle();
    try {
      ConfigurationProperty property = propertyOracle.getConfigurationProperty(PROP_TYPE_FINDER);
      String value = property == null ? TYPE_FINDER_VALUES.get(0) : property.getValues().get(0).toLowerCase();
      switch(TYPE_FINDER_VALUES.indexOf(value)){
        case 0:
          typeFinders.add(new TypeRpcFinder(context, logger));
          break;
        case 1:
          typeFinders.add(new TypeXmlFinder(context, logger));
          break;
        case 2:
          typeFinders.add(new TypeRpcFinder(context, logger));
          typeFinders.add(new TypeXmlFinder(context, logger));
          break;
        default:
          break;
      }
    } catch (BadPropertyValueException e) {
      logger.branch(TreeLogger.DEBUG, "Could not find property " + PROP_TYPE_FINDER, e);
    }
    return typeFinders;
  }

  protected final TreeLogger logger;
  protected final TypeOracle typeOracle;

  private StorageTypeFilter typeFilter;

  public StorageTypeFinder(GeneratorContext context, TreeLogger logger) {
    this.logger = logger;
    this.typeOracle = context.getTypeOracle();
  }

  public void setTypeFilter(StorageTypeFilter typeFilter) {
    this.typeFilter = typeFilter;
  }

  protected boolean addIfIsValidType(final Set<JType> serializables, JType jType) {
    if(jType == null) return false;
    // we don't filter interface types here, GWT's SerializableTypeOracle will treat them
    if(!isAllowed(jType.isClass())){
      logger.branch(TreeLogger.DEBUG, jType + " was filtered.");
      return false;
    }else{
      serializables.add(jType);
      logger.branch(TreeLogger.TRACE, "add " + jType + " as storage serializable.");
      return true;
    }
  }

  public boolean isAllowed(JClassType type){
    if(this.typeFilter == null){
      return true;
    }

    String name = getBaseTypeName(type);
    // For types not handled by getBaseTypeName just return true.
    if (name == null) {
      return true;
    }
    return typeFilter.isIncluded(logger, name);
  }

  /**
   * Returns a simple types, including classes and
   * interfaces, parameterized, and raw types. Null is returned for other types
   * such as arrays and type parameters (e.g., 'E' in java.util.List<E>) because
   * filtering is meaningless for such types.
   */
  protected String getBaseTypeName(JClassType type) {
    if(type == null){
      return null;
    }
    if (type instanceof JRealClassType) {
      return type.getQualifiedSourceName();
    } else if (type.isParameterized() != null) {
      return type.isParameterized().getBaseType().getQualifiedSourceName();
    } else if (type.isRawType() != null) {
      return type.isRawType().getQualifiedSourceName();
    }
    return type.getQualifiedSourceName();
  }

  public abstract Set<JType> findStorageTypes() throws UnableToCompleteException;
}
