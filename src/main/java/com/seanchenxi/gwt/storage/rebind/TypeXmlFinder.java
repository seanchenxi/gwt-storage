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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.dev.resource.ResourceOracle;
import com.google.gwt.dev.util.Util;

/**
 * Created by: Xi
 */
final class TypeXmlFinder extends StorageTypeFinder {

  private final TypeOracle typeOracle;
  private final ResourceOracle resourceOracle;
  private final boolean isProdMode;
  private final TreeLogger logger;

  TypeXmlFinder(GeneratorContext context, TreeLogger logger){
    this.typeOracle = context.getTypeOracle();
    this.resourceOracle = context.getResourcesOracle();
    this.isProdMode = context.isProdMode();
    this.logger = logger;
  }

  @Override
  public Set<JType> findStorageTypes() throws UnableToCompleteException{
    // Find all Serialization resource
    Set<String> typeNames = new HashSet<String>();
    for(Resource resource : findAllSerializationResources()){
      Collection<String> subTypeNames = parseResource(resource);
      if(subTypeNames != null) typeNames.addAll(subTypeNames);
    }

    Set<JType> serializables = new HashSet<JType>();
    for(String typeName : typeNames){
      JClassType jType = typeOracle.findType(typeName);
      boolean added = addIfIsValidType(serializables, jType, logger);
      if(added) addIfIsValidType(serializables, typeOracle.getArrayType(jType), logger);
    }
    // All primitive types and its array types will be considered as serializable
    for(JPrimitiveType jpt : JPrimitiveType.values()){
      if(JPrimitiveType.VOID.equals(jpt)){
        continue;
      }
      JClassType jBoxedType = typeOracle.findType(jpt.getQualifiedBoxedSourceName());
      boolean added = addIfIsValidType(serializables, jpt, logger);
      if(added) {
        addIfIsValidType(serializables, typeOracle.getArrayType(jpt), logger);
        if(added) added = addIfIsValidType(serializables, jBoxedType, logger);
      }
      if(added) addIfIsValidType(serializables, typeOracle.getArrayType(jBoxedType), logger);
    }

    return serializables;
  }

  private Collection<String> parseResource(Resource resource) throws UnableToCompleteException {
    InputStream input = null;
    try{
      input = resource.openContents();
      return isProdMode ? unmarshallXml(input) : parseXmlString(input);
    }catch(Exception e){
      logger.log(TreeLogger.Type.WARN, "[isProdMode=" + isProdMode + "] Error while parsing resource(" + resource.getPath() + ")", e);
      throw new UnableToCompleteException();
    } finally{
      try{
        if(input != null) input.close();
      }catch(Exception e){
        //To Ignore
      }
    }
  }

  private List<Resource> findAllSerializationResources(){
    List<Resource> resources = new ArrayList<Resource>();
    Map<String,Resource> resourceMap = resourceOracle.getResourceMap();
    for(String key : resourceMap.keySet()){
      if(key.endsWith(SERIALIZATION_CONFIG)){
        Resource resource = resourceMap.get(key);
        if(resource != null) resources.add(resource);
      }
    }
    return resources;
  }

  private Collection<String> unmarshallXml(InputStream input) throws Exception{
    JAXBContext jaxbContext = JAXBContext.newInstance(StorageSerialization.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    StorageSerialization storageSerialization = (StorageSerialization)unmarshaller.unmarshal(input);
    return storageSerialization.getClasses();
  }

  private Collection<String> parseXmlString(InputStream input) throws Exception{
    String content = Util.readStreamAsString(input);
    String elBegin = "<class>";
    String elEnd = "</class>";

    Set<String> typeNames = new HashSet<String>();
    for(String _typeName : content.split(elBegin)){
      if(_typeName.contains(elEnd)){
        String typeName = _typeName.split(elEnd)[0];
        if(typeName != null || !typeName.trim().isEmpty()){
          typeNames.add(typeName.trim());
        }
      }
    }
    return typeNames;
  }

}