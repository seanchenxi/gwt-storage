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

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.seanchenxi.gwt.storage.client.AbstractStorageKeyProvider;

/**
 * Created by: Xi
 */
public class StorageKeyProviderGenerator extends Generator {

  private String simpleSourceName;

  private StorageKeyProviderModel model;

  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    TypeOracle oracle = context.getTypeOracle();
    JClassType keyProviderIntf = oracle.findType(typeName);
    JClassType toGenerate = keyProviderIntf.isInterface();

    if (toGenerate == null) {
      logger.log(TreeLogger.ERROR, typeName + " is not an interface type");
      throw new UnableToCompleteException();
    }

    String packageName = toGenerate.getPackage().getName();
    simpleSourceName = toGenerate.getName().replace('.', '_') + "Impl";
    try (PrintWriter pw = context.tryCreate(logger, packageName, simpleSourceName)) {
      if (pw == null) {
        return packageName + "." + simpleSourceName;
      }
    
      model = new StorageKeyProviderModel(logger, toGenerate);
      model.loadMethods();
    
      ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(packageName, simpleSourceName);
      factory.setSuperclass(AbstractStorageKeyProvider.class.getCanonicalName());
      factory.addImplementedInterface(typeName);
      SourceWriter sw = factory.createSourceWriter(context, pw);
      writeMethods(sw);
      sw.commit(logger);
      return factory.getCreatedClassName();
    }
  }

  private void writeMethods(SourceWriter sw) throws UnableToCompleteException {
    final String keyParamName = "key";
    final String varKeyValueName = "keyValue";
    for (StorageKeyProviderMethod method : model.getMethods()) {
      final String returnName = method.getReturnType().getParameterizedQualifiedSourceName();
      final String parameters = method.isDynamicKey() ? (method.getKeyValueType().getQualifiedSourceName() + " " + keyParamName) : "";
      final String keyPrefix = method.getKeyPrefix();
      final String keySuffix = method.getKeySuffix();

      sw.println();
      sw.println("public %s %s(%s) {", returnName, method.getName(), parameters);
      sw.indent();
      String keyClazz = method.getKeyClazz().getQualifiedSourceName();
      if (method.isDynamicKey()) {
        String keyPrefixStr = "";
        String keySuffixStr = "";
        if(keyPrefix != null && !keyPrefix.trim().isEmpty()) {
          keyPrefixStr = "\"" + keyPrefix + "\" + ";
        }
        if(keySuffix != null && !keySuffix.trim().isEmpty()){
          keySuffixStr = " + \"" + keySuffix + "\"";
        }
        sw.println("String %s = %sString.valueOf(key)%s;", varKeyValueName, keyPrefixStr, keySuffixStr);
        sw.println("return createIfAbsent(%s, %s.class);", varKeyValueName, keyClazz);
      }else{
        String staticKeyValue = method.getStaticKeyValue();
        if(keyPrefix != null && !keyPrefix.trim().isEmpty()){
          staticKeyValue = keyPrefix + staticKeyValue;
        }
        if(keySuffix != null && !keySuffix.trim().isEmpty()){
          staticKeyValue += keySuffix;
        }
        sw.println("return createIfAbsent(\"%s\", %s.class);", staticKeyValue, keyClazz);
      }
      sw.outdent();
      sw.println("}");
    }
  }

}
