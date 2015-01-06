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

import com.google.gwt.core.ext.CachedGeneratorResult;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.IncrementalGenerator;
import com.google.gwt.core.ext.RebindMode;
import com.google.gwt.core.ext.RebindResult;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.EmittedArtifact;
import com.google.gwt.core.ext.linker.GeneratedResource;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.util.Util;
import com.google.gwt.user.rebind.rpc.CachedRpcTypeInformation;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracle;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracleBuilder;
import com.google.gwt.user.rebind.rpc.SerializationUtils;
import com.google.gwt.user.rebind.rpc.TypeSerializerCreator;
import com.google.gwt.user.rebind.rpc.GwtUtilityDelegate;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Generator for {@link com.seanchenxi.gwt.storage.client.serializer.StorageSerializer}
 */
public class StorageTypeSerializerGenerator extends IncrementalGenerator {

  private static final long GENERATOR_VERSION_ID = 1L;

  private static final String TYPE_SERIALIZER_SUFFIX = "Impl";
  private static final String SERIALIZATION_POLICY_NAME = "StorageSerializerPolicy";
  private JClassType serializerType = null;

  @Override
  public RebindResult generateIncrementally(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    TypeOracle typeOracle = context.getTypeOracle();

    serializerType = typeOracle.findType(typeName);
    if (serializerType == null || serializerType.isInterface() == null)
      throw new UnableToCompleteException();

    final Set<JType> serializables = StorageTypeFinder.getInstance(context, logger).findStorageTypes();

    String typeSerializerClassName = serializerType.getQualifiedSourceName() + TYPE_SERIALIZER_SUFFIX;
    String typeSerializerSimpleName = serializerType.getSimpleSourceName() + TYPE_SERIALIZER_SUFFIX;
    JClassType typeSerializer = typeOracle.findType(typeSerializerClassName);

    SerializableTypeOracle serializationOracle = buildSerializableTypeOracle(logger, context, serializables);

    if (typeSerializer != null && typeSerializer.isClass() != null
        && isNothingChanged(logger, context, serializables, serializationOracle)) {
      return new RebindResult(RebindMode.USE_EXISTING, typeSerializerClassName);
    }

    writeSerializationPolicyFile(logger, context, serializationOracle, serializationOracle);

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

  private SerializableTypeOracle buildSerializableTypeOracle(TreeLogger logger, GeneratorContext context, Set<JType> serializables) throws UnableToCompleteException {
    SerializableTypeOracleBuilder builder = new SerializableTypeOracleBuilder(logger, context);
    for (JType type : serializables) {
      builder.addRootType(logger, type);
    }
    return builder.build(logger);
  }

  private boolean isNothingChanged(TreeLogger logger, GeneratorContext context, Set<JType> serializables, SerializableTypeOracle serializationOracle) {// caching use
    CachedGeneratorResult cachedResult = context.getCachedGeneratorResult();
    if (cachedResult == null || !context.isGeneratorResultCachingEnabled()){
      return false;
    }
    Object obj = context.getCachedGeneratorResult().getClientData(ProxyCreator.CACHED_TYPE_INFO_KEY);
    return obj != null && ((CachedRpcTypeInformation) obj).checkTypeInformation(logger, context.getTypeOracle(),
      serializationOracle, serializationOracle);
  }

  protected void writeSerializationPolicyFile(TreeLogger logger, GeneratorContext ctx,
                                                SerializableTypeOracle serializationSto,
                                                SerializableTypeOracle deserializationSto)
          throws UnableToCompleteException {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OutputStreamWriter osw =
              new OutputStreamWriter(baos, SerializationPolicyLoader.SERIALIZATION_POLICY_FILE_ENCODING);
      PrintWriter pw = new PrintWriter(osw);

      JType[] serializableTypes =
              unionOfTypeArrays(serializationSto.getSerializableTypes(), deserializationSto
                      .getSerializableTypes(), new JType[] {serializerType});

      logger.branch(TreeLogger.Type.INFO, "Generate StorageSerialize policy file with " + serializableTypes.length + " types");
      pw.print(SerializationPolicyLoader.FINAL_FIELDS_KEYWORD);
      pw.print(", ");
      pw.print(GwtUtilityDelegate.shouldSerializeFinalFields(logger, ctx));
      pw.print('\n');

      for (int i = 0; i < serializableTypes.length; ++i) {
        JType type = serializableTypes[i];
        if(type.isPrimitive() != null) continue;
        String binaryTypeName = SerializationUtils.getRpcTypeName(type);
        pw.print(binaryTypeName);
        pw.print(", " + Boolean.toString(deserializationSto.isSerializable(type)));
        pw.print(", " + Boolean.toString(deserializationSto.maybeInstantiated(type)));
        pw.print(", " + Boolean.toString(serializationSto.isSerializable(type)));
        pw.print(", " + Boolean.toString(serializationSto.maybeInstantiated(type)));
        //no need pw.print(", " + typeStrings.get(type));

        /*
         * Include the serialization signature to bump the RPC file name if
         * obfuscated identifiers are used.
         */
        pw.print(", " + GwtUtilityDelegate.getSerializationSignature(ctx, type));
        pw.print('\n');

        /*
         * Emit client-side field information for classes that may be enhanced
         * on the server. Each line consists of a comma-separated list
         * containing the keyword '@ClientFields', the class name, and a list of
         * all potentially serializable client-visible fields.
         */
        if ((type instanceof JClassType) && ((JClassType) type).isEnhanced()) {
          JField[] fields = ((JClassType) type).getFields();
          JField[] rpcFields = new JField[fields.length];
          int numRpcFields = 0;
          for (JField f : fields) {
            if (f.isTransient() || f.isStatic() || f.isFinal()) {
              continue;
            }
            rpcFields[numRpcFields++] = f;
          }

          pw.print(SerializationPolicyLoader.CLIENT_FIELDS_KEYWORD);
          pw.print(',');
          pw.print(binaryTypeName);
          for (int idx = 0; idx < numRpcFields; idx++) {
            pw.print(',');
            pw.print(rpcFields[idx].getName());
          }
          pw.print('\n');
        }
      }

      // Closes the wrapped streams.
      pw.close();

      byte[] serializationPolicyFileContents = baos.toByteArray();

      String serializationPolicyFileName =
              SerializationPolicyLoader.getSerializationPolicyFileName(SERIALIZATION_POLICY_NAME);
      OutputStream os = ctx.tryCreateResource(logger, serializationPolicyFileName);
      if (os != null) {
        os.write(serializationPolicyFileContents);
        GeneratedResource resource = ctx.commitResource(logger, os);

        /*
         * Record which proxy class created the resource. A manifest will be
         * emitted by the RpcPolicyManifestLinker.
         */
        emitPolicyFileArtifact(logger, ctx, resource.getPartialPath());
      } else {
        if (logger.isLoggable(TreeLogger.TRACE)) {
          logger.log(TreeLogger.TRACE, "SerializationPolicy file for StorageSerializer '"
                          + serializerType.getQualifiedSourceName() + "' already exists; no need to rewrite it.",
                  null);
        }
      }

    } catch (UnsupportedEncodingException e) {
      logger.log(TreeLogger.ERROR, SerializationPolicyLoader.SERIALIZATION_POLICY_FILE_ENCODING
              + " is not supported", e);
      throw new UnableToCompleteException();
    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, null, e);
      throw new UnableToCompleteException();
    }
  }

  /**
   * Take the union of two type arrays, and then sort the results
   * alphabetically.
   */
  private static JType[] unionOfTypeArrays(JType[]... types) {
    Set<JType> typesList = new HashSet<JType>();
    for (JType[] a : types) {
      typesList.addAll(Arrays.asList(a));
    }
    JType[] serializableTypes = typesList.toArray(new JType[typesList.size()]);
    Arrays.sort(serializableTypes, GwtUtilityDelegate.JTYPE_COMPARATOR);
    return serializableTypes;
  }

  private void emitPolicyFileArtifact(TreeLogger logger, GeneratorContext context,
                                      String partialPath) throws UnableToCompleteException {
    try {
      String qualifiedSourceName = serializerType.getQualifiedSourceName();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Writer writer;
      writer = new OutputStreamWriter(baos, SerializationPolicyLoader.SERIALIZATION_POLICY_FILE_ENCODING);
      writer.write("serviceClass: " + qualifiedSourceName + "\n");
      writer.write("path: " + partialPath + "\n");
      writer.close();

      byte[] manifestBytes = baos.toByteArray();
      String md5 = Util.computeStrongName(manifestBytes);
      OutputStream os =
              context.tryCreateResource(logger, ProxyCreator.MANIFEST_ARTIFACT_DIR + "/" + md5 + ".txt");
      os.write(manifestBytes);

      GeneratedResource resource = context.commitResource(logger, os);
      // TODO: change to Deploy when possible
      resource.setVisibility(EmittedArtifact.Visibility.LegacyDeploy);
    } catch (UnsupportedEncodingException e) {
      logger.log(TreeLogger.ERROR, SerializationPolicyLoader.SERIALIZATION_POLICY_FILE_ENCODING + " is not supported", e);
      throw new UnableToCompleteException();
    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, null, e);
      throw new UnableToCompleteException();
    }
  }
}
