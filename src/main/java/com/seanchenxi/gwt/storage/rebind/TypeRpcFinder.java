package com.seanchenxi.gwt.storage.rebind;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Created by: Xi
 */
final class TypeRpcFinder extends StorageTypeFinder {

  private final TypeOracle typeOracle;
  private final StorageTypeFilter typeFilter;
  private final TreeLogger logger;

  TypeRpcFinder(GeneratorContext context, TreeLogger logger) throws UnableToCompleteException{
    this.typeOracle = context.getTypeOracle();
    this.logger = logger;
    StorageTypeFilter _typeFilter;
    try {
      ConfigurationProperty prop = context.getPropertyOracle().getConfigurationProperty(PROP_STORAGE_BLACKLIST);
      _typeFilter = new StorageTypeFilter(logger, prop.getValues());
    } catch (BadPropertyValueException e) {
      logger.log(TreeLogger.DEBUG, "Could not find property " + PROP_STORAGE_BLACKLIST, e);
      _typeFilter = null;
    }
    typeFilter = _typeFilter;
  }

  @Override
  public Set<JType> findStorageTypes() throws UnableToCompleteException{
    Set<JType> serializables = new HashSet<JType>();

    JClassType remoteSvcIntf = typeOracle.findType(RemoteService.class.getName());
    JClassType[] remoteSvcTypes = remoteSvcIntf.getSubtypes();
    for(JClassType remoteSvcType : remoteSvcTypes){
      for(JMethod method : remoteSvcType.getMethods()){
        JType type = method.getReturnType();
        if(JPrimitiveType.VOID != type){
          addIfIsValidType(serializables, type, typeFilter, logger);
        }
        for(JType param : method.getParameterTypes()){
          addIfIsValidType(serializables, param, typeFilter, logger);
        }
      }
    }

    return serializables;
  }

}