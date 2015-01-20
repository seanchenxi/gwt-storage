package com.seanchenxi.gwt.storage.rebind;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

  TypeRpcFinder(GeneratorContext context, TreeLogger logger) throws UnableToCompleteException{
    super(context, logger);

    Set<String> regexes = new HashSet<String>();
    try {
      ConfigurationProperty prop = context.getPropertyOracle().getConfigurationProperty(PROP_RPC_BLACKLIST);
      logger.branch(TreeLogger.INFO, "Analyzing RPC blacklist information");
      regexes.addAll(prop.getValues());
    } catch (BadPropertyValueException e) {
      logger.log(TreeLogger.DEBUG, "Could not find property " + PROP_RPC_BLACKLIST);
    }

    try {
      ConfigurationProperty prop = context.getPropertyOracle().getConfigurationProperty(PROP_STORAGE_BLACKLIST);
      String log = "Analyzing Storage blacklist information";
      if(!regexes.isEmpty()){
        log += ", will be an addition of RPC blacklist filter regex";
      }
      logger.branch(TreeLogger.INFO, log);
      regexes.addAll(prop.getValues());
    } catch (BadPropertyValueException e) {
      logger.log(TreeLogger.DEBUG, "Could not find property " + PROP_STORAGE_BLACKLIST, e);
    }

    if(!regexes.isEmpty()){
      setTypeFilter(new StorageTypeFilter(logger, new ArrayList<String>(regexes)));
    }
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
          addIfIsValidType(serializables, type);
        }
        for(JType param : method.getParameterTypes()){
          addIfIsValidType(serializables, param);
        }
      }
    }

    return serializables;
  }

}