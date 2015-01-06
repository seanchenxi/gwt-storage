package com.google.gwt.user.rebind.rpc;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JType;

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
}
