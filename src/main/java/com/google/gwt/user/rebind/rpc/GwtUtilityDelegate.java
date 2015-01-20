/*
 * Copyright 2015 Xi CHEN
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

package com.google.gwt.user.rebind.rpc;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;

import com.seanchenxi.gwt.storage.rebind.StorageTypeFinder;

import java.util.Comparator;

/**
 * GWT Package protected utility delegation
 */
public class GwtUtilityDelegate {

    public static final Comparator<JType> JTYPE_COMPARATOR;
    static{
        JTYPE_COMPARATOR = SerializableTypeOracleBuilder.JTYPE_COMPARATOR;
    }

    public static String getSerializationSignature(GeneratorContext ctx, JType type){
        return SerializationUtils.getSerializationSignature(ctx, type);
    }

    public static boolean shouldSerializeFinalFields(TreeLogger logger, GeneratorContext ctx){
        return Shared.shouldSerializeFinalFields(logger, ctx);
    }

    public static void attachTypeFinder(SerializableTypeOracleBuilder oracleBuilder, final StorageTypeFinder typeFinder, TreeLogger logger) throws UnableToCompleteException {
        for (JType type : typeFinder.findStorageTypes()) {
            oracleBuilder.addRootType(logger, type);
        }
        oracleBuilder.setTypeFilter(new TypeFilter() {
            @Override
            public String getName() {
                return "StorageTypeFilter";
            }
            @Override
            public boolean isAllowed(JClassType type) {
                return typeFinder.isAllowed(type);
            }
        });
    }
}
