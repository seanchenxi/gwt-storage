/*
 * Copyright 2012 Xi CHEN
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.seanchenxi.gwt.storage.rebind;

import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.dev.resource.ResourceOracle;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.rebind.rpc.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Generator for {@link com.seanchenxi.gwt.storage.client.serializer.StorageSerializer}
 */
public class StorageTypeSerializerGenerator extends IncrementalGenerator {

  private static final long GENERATOR_VERSION_ID = 1L;

  private static final String SERIALIZATION_CONFIG = "storage-serialization.xml";
  private static final String TYPE_FINDER = "storage.type.finder";
  private static final List<String> TYPE_FINDER_VALUES = Arrays.asList("rpc", "xml", "rpc_xml");

  private static final String TYPE_SERIALIZER_SUFFIX = "Impl";

  private static void addIfIsValidType(HashSet<JType> serializables, JType jType, TreeLogger logger) {
    if (jType != null && jType.isInterface() == null){
      serializables.add(jType);
      logger.log(TreeLogger.TRACE, "Add " + jType + " as storage serializable.");
    } else{
      logger.log(TreeLogger.WARN, "Failed to add " + jType + " as storage serializable.");
    }
  }

  @Override
  public RebindResult generateIncrementally(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    TypeOracle typeOracle = context.getTypeOracle();

    JClassType serializerType = typeOracle.findType(typeName);
    if (serializerType == null || serializerType.isInterface() == null)
      throw new UnableToCompleteException();

    HashSet<JType> serializables = new HashSet<JType>();

    int typeFinder = getTypeFinder(logger, context.getPropertyOracle());
    if (typeFinder != 1)
      findRPCSerializableTypes(logger, typeOracle, serializables);
    if (typeFinder != 0)
      findXMLSerializableTypes(logger, typeOracle, context.getResourcesOracle(), serializables);

    String typeSerializerClassName = serializerType.getQualifiedSourceName() + TYPE_SERIALIZER_SUFFIX;
    String typeSerializerSimpleName = serializerType.getSimpleSourceName() + TYPE_SERIALIZER_SUFFIX;
    JClassType typeSerializer = typeOracle.findType(typeSerializerClassName);

    SerializableTypeOracle serializationOracle = buildSerializableTypeOracle(logger, context, serializables);

    if (typeSerializer != null && typeSerializer.isClass() != null
        && isNothingChanged(logger, context, serializables, serializationOracle)) {
      return new RebindResult(RebindMode.USE_EXISTING, typeSerializerClassName);
    }

    TypeSerializerCreator tsc =
        new TypeSerializerCreator(logger, serializationOracle, serializationOracle, context,
            typeSerializerClassName, typeSerializerSimpleName);
    tsc.realize(logger);

    if (context.isGeneratorResultCachingEnabled()) {
      RebindResult result = new RebindResult(RebindMode.USE_PARTIAL_CACHED, typeSerializerClassName);
      CachedRpcTypeInformation cti = new CachedRpcTypeInformation(serializationOracle, serializationOracle, serializables, new HashSet<JType>());
      result.putClientData(ProxyCreator.CACHED_TYPE_INFO_KEY, cti);
      return result;
    } else {
      return new RebindResult(RebindMode.USE_ALL_NEW_WITH_NO_CACHING, typeSerializerClassName);
    }
  }

  @Override
  public long getVersionId() {
    return GENERATOR_VERSION_ID;
  }

  private SerializableTypeOracle buildSerializableTypeOracle(TreeLogger logger, GeneratorContext context, HashSet<JType> serializables) throws UnableToCompleteException {
    SerializableTypeOracleBuilder builder = new SerializableTypeOracleBuilder(logger, context.getPropertyOracle(), context);
    for (JType type : serializables) {
      builder.addRootType(logger, type);
    }
    return builder.build(logger);
  }

  private void findRPCSerializableTypes(TreeLogger logger, TypeOracle typeOracle, HashSet<JType> serializables) {
    JClassType remoteSvcIntf = typeOracle.findType(RemoteService.class.getName());
    JClassType[] remoteSvcTypes = remoteSvcIntf.getSubtypes();
    for (JClassType remoteSvcType : remoteSvcTypes) {
      for (JMethod method : remoteSvcType.getMethods()) {
        JType type = method.getReturnType();
        if (JPrimitiveType.VOID != type){
          addIfIsValidType(serializables, type, logger);
        }
        for (JType param : method.getParameterTypes()) {
          addIfIsValidType(serializables, param, logger);
        }
      }
    }
  }

  private void findXMLSerializableTypes(TreeLogger logger, TypeOracle typeOracle, ResourceOracle resourceOracle, HashSet<JType> serializables) {
    // All primitive types and its array types will be considered as serializable
    for(JPrimitiveType jpt: JPrimitiveType.values()){
      if(JPrimitiveType.VOID.equals(jpt)){
        continue;
      }
      addIfIsValidType(serializables, jpt, logger);
      addIfIsValidType(serializables, typeOracle.getArrayType(jpt), logger);
      JClassType jType = typeOracle.findType(jpt.getQualifiedBoxedSourceName());
      addIfIsValidType(serializables, jType, logger);
      addIfIsValidType(serializables, typeOracle.getArrayType(jType), logger);
    }

    // Find all Serialization resource
    Resource resource = null;
    for (String key : resourceOracle.getResourceMap().keySet()) {
      if (key.endsWith(SERIALIZATION_CONFIG)) {
        resource = resourceOracle.getResourceMap().get(key);
      }
    }

    if (null == resource){
      return;
    }

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(StorageSerialization.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      StorageSerialization storageSerialization = (StorageSerialization) unmarshaller.unmarshal(resource.openContents());
      for (String typeName : storageSerialization.getClasses()) {
        if (typeName == null || typeName.trim().isEmpty()){
          continue;
        }
        JClassType jType = typeOracle.findType(typeName);
        addIfIsValidType(serializables, jType, logger);
        addIfIsValidType(serializables, typeOracle.getArrayType(jType), logger);
      }
    } catch (Exception e) {
      logger.log(Type.WARN, "Error reading XML Source: " + e.getMessage(), e);
    }
  }

  private int getTypeFinder(TreeLogger logger, PropertyOracle propertyOracle) {
    try {
      ConfigurationProperty property = propertyOracle.getConfigurationProperty(TYPE_FINDER);
      List<String> values = property.getValues();
      logger.log(TreeLogger.DEBUG, "Configured " + TYPE_FINDER + "=" + values);
      return Math.max(0, TYPE_FINDER_VALUES.indexOf(values.get(0)));
    } catch (Exception e) {
      return 0;
    }
  }

  @SuppressWarnings("unchecked")
  private boolean isNothingChanged(TreeLogger logger, GeneratorContext context, HashSet<JType> serializables, SerializableTypeOracle serializationOracle) {// caching use
    CachedGeneratorResult cachedResult = context.getCachedGeneratorResult();
    if (cachedResult == null || !context.isGeneratorResultCachingEnabled()){
      return false;
    }
    Object obj = context.getCachedGeneratorResult().getClientData(ProxyCreator.CACHED_TYPE_INFO_KEY);
    return obj != null && ((CachedRpcTypeInformation) obj).checkTypeInformation(logger, context.getTypeOracle(),
      serializationOracle, serializationOracle);
  }
}
