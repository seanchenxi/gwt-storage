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
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracleException;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.dev.resource.ResourceOracle;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Created by: Xi
 */
final class TypeXmlFinder extends StorageTypeFinder {

  private static final String FEATURE_NAMESPACES = "http://xml.org/sax/features/namespaces";
  private static final String FEATURE_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
  private static final String STORAGE_SERIALIZATION_DTD = "storage-serialization.dtd";

  private final ResourceOracle resourceOracle;
  private final JClassType[] collectionOrMap;

  static Source createSAXSource(InputStream input) throws SAXException{
    XMLReader xmlreader = XMLReaderFactory.createXMLReader();
    xmlreader.setFeature(FEATURE_NAMESPACES, true);
    xmlreader.setFeature(FEATURE_NAMESPACE_PREFIXES, true);
    xmlreader.setEntityResolver(new EntityResolver() {
      public InputSource resolveEntity(String publicId, String systemId) {
        return new InputSource(StorageSerialization.class.getResourceAsStream(STORAGE_SERIALIZATION_DTD));
      }
    });
    return new SAXSource(xmlreader, new InputSource(input));
  }

  TypeXmlFinder(GeneratorContext context, TreeLogger logger){
    super(context, logger);
    this.resourceOracle = context.getResourcesOracle();

    JClassType[] _collectionOrMap = new JClassType[2];
    try{
      _collectionOrMap[0] = typeOracle.getType(Collection.class.getName());
      _collectionOrMap[1] = typeOracle.getType(Map.class.getName());
    }catch(NotFoundException e){
      _collectionOrMap = null;
    }
    collectionOrMap = _collectionOrMap;
  }

  @Override
  public Set<JType> findStorageTypes() throws UnableToCompleteException{
    Set<JType> serializables = new HashSet<>();

    // Find all Serialization resource
    List<StorageSerialization> storageSerializations = findAllStorageSerializations();
    if(storageSerializations == null || storageSerializations.isEmpty()){
      return serializables;
    }

    boolean isIncludePrimitive = true, isAutoArrayType = true;
    for(StorageSerialization storageSerial : storageSerializations){
      if(isIncludePrimitive && !storageSerial.isIncludePrimitiveTypes()){
        isIncludePrimitive = false;
      }
      if(isAutoArrayType && !storageSerial.isAutoArrayType()){
        isAutoArrayType = false;
      }
      parseClassNameList(serializables, storageSerial, storageSerial.getClasses());
    }

    if(isIncludePrimitive){
      parsePrimitiveTypes(serializables, isAutoArrayType);
    }
    return serializables;
  }

  private void parseClassNameList(Set<JType> serializables, StorageSerialization storageSerial, List<String> classNames){
    if(classNames == null || classNames.isEmpty()){
      logger.branch(TreeLogger.Type.DEBUG, "No defined class in StorageSerialization at " + storageSerial.getPath());
      return;
    }
    
    logger.branch(TreeLogger.Type.DEBUG, "Parsing StorageSerialization at " + storageSerial.getPath());
    for(String className : classNames){
      JType jType;
      try{
        logger.branch(TreeLogger.Type.TRACE, "Parse className: " + className);
        jType = typeOracle.parse(className);
      }catch(TypeOracleException e){
        logger.branch(TreeLogger.Type.WARN, "Parse className " +  className + " error", e);
        jType = null;
      }

      try{
        if(storageSerial.isAutoArrayType() && jType != null && jType.isArray() == null && !isCollectionOrMapType(jType)){
          addIfIsValidType(serializables, typeOracle.getArrayType(jType));
        }else{
          addIfIsValidType(serializables, jType);
        }
      }catch (Exception e){
        logger.branch(TreeLogger.Type.WARN, "Add className " +  className + " error", e);
      }
    }
  }

  private void parsePrimitiveTypes(Set<JType> serializables, boolean autoArrayType){
    logger.branch(TreeLogger.Type.DEBUG, "Adding all primitive types");
    // All primitive types and its array types will be considered as serializable
    for(JPrimitiveType jpt : JPrimitiveType.values()){
      if(JPrimitiveType.VOID.equals(jpt)){
        continue;
      }
      JClassType jBoxedType = typeOracle.findType(jpt.getQualifiedBoxedSourceName());
      boolean added = false;
      if(autoArrayType){
        if(addIfIsValidType(serializables, typeOracle.getArrayType(jpt)))
          added = addIfIsValidType(serializables, typeOracle.getArrayType(jBoxedType));
      }else{
        if(addIfIsValidType(serializables, jpt))
          added = addIfIsValidType(serializables, jBoxedType);
      }
      if(added) logger.branch(TreeLogger.Type.TRACE, "Added " + jpt.getQualifiedSourceName());
    }
  }

  private List<StorageSerialization> findAllStorageSerializations() throws UnableToCompleteException{
    logger.branch(TreeLogger.Type.DEBUG, "Find StorageSerialization XML");
    List<StorageSerialization> storageSerializations = new ArrayList<>();
    for(String pathName : resourceOracle.getPathNames()){
      if(pathName.endsWith(SERIALIZATION_CONFIG)){
        StorageSerialization storageSerialization = parseXmlResource(resourceOracle.getResource(pathName));
        if(storageSerialization != null){
          storageSerializations.add(storageSerialization);
        }
      }
    }
    return storageSerializations;
  }

  private StorageSerialization parseXmlResource(Resource resource) throws UnableToCompleteException {
    InputStream input = null;
    try{
      JAXBContext jaxbContext = JAXBContext.newInstance(StorageSerialization.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      Source source = createSAXSource(input = resource.openContents());
      StorageSerialization storageSerialization = (StorageSerialization)unmarshaller.unmarshal(source);
      storageSerialization.setPath(resource.getPath());
      return storageSerialization;
    }catch(Exception e){
      logger.branch(TreeLogger.Type.WARN, "Error while parsing xml resource at " + resource.getPath(), e);
      throw new UnableToCompleteException();
    } finally{
      try{
        if(input != null) input.close();
      }catch(Exception e){
        //To ignore
      }
    }
  }

  private boolean isCollectionOrMapType(JType jType){
    if(jType == null) return false;
    JClassType jClassType = jType.isClassOrInterface();
    if(jClassType == null) return false;
    for(JClassType possibleSuperType : collectionOrMap){
      if(jClassType.isAssignableTo(possibleSuperType)){
        return true;
      }
    }
    return false;
  }

}