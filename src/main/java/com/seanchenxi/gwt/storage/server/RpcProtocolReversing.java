/*
 * Copyright 2018 Xi CHEN
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

package com.seanchenxi.gwt.storage.server;

import com.google.gwt.user.client.rpc.SerializationException;

import static org.apache.commons.text.StringEscapeUtils.unescapeJson;

/**
 * Used for server side deserialization
 * This utility class will transform serialized data structure from
 *  "server->client format" to "client->server format"
 */
final class RpcProtocolReversing {

    interface Builder {
        String build();
    }

    private static final class ServerReadFormatBuilder implements Builder{

        private final StringBuilder sb = new StringBuilder();
        private final String rawNumbers;
        private final String rawStringTable;
        private final String rawFlags;

        private ServerReadFormatBuilder(String rawNumbers, String rawStringTable, String rawFlags) {
            this.rawNumbers = rawNumbers;
            this.rawStringTable = rawStringTable;
            this.rawFlags = rawFlags;
        }

        @Override
        public String build() {
            sb.setLength(0);
            reverseAppend(rawFlags.split(COMMA));
            appendStringTable(rawStringTable.split("\"" + COMMA + "\\s*\""));
            reverseAppend(rawNumbers.split(COMMA));
            return sb.toString();
        }

        private void appendStringTable(String[] stringTable){
            append(String.valueOf(stringTable.length));
            for (String st : stringTable) {
                String value = unescapeJson(st).replace(PIPE, RPC_PIPE_REPLACE);
                append(value);
            }
        }

        private void reverseAppend(String[] array) {
            for (int i = array.length - 1; i >= 0; i--) {
                if(array[i] == null || array[i].length() < 1){
                    continue;
                }
                String str = array[i].replaceAll("(^\'|\'$)", BLANK);
                append(str);
            }
        }

        private void append(String value){
            sb.append(value).append(PIPE);
        }
    }

    private static final String STR_TABLE_START = "[\"";
    private static final String STR_TABLE_END = "\"]";
    private static final String BLANK = "";
    private static final String COMMA = ",";
    private static final String PIPE = "|";
    private static final String RPC_PIPE_REPLACE = "\\!";


    static Builder forServerRead(String serializedString) throws SerializationException {
        if(!serializedString.matches("^\\[(('[a-zA-Z0-9+/=]+'|\\d)+,)+\\[(\".*\",?)+\\](,\\d){2}\\]$")){
            throw new SerializationException("Data (" + serializedString + ") doesn't match the required data structure");
        }

        int rawNumbersStartPos = 1;
        int rawNumbersEndPos = serializedString.indexOf(STR_TABLE_START, rawNumbersStartPos) - 1; // skip ending ,
        int stringTableStartPos = rawNumbersEndPos + STR_TABLE_START.length() + 1; // skip [ and "
        int stringTableEndPos = serializedString.indexOf(STR_TABLE_END, stringTableStartPos); // skip ending "
        int flagsStartPos = stringTableEndPos + STR_TABLE_END.length() + 1; // skip connecting ,
        int flagsEndPos = serializedString.length() - 1; // skip connecting ,

        String rawNumbers = serializedString.substring(rawNumbersStartPos, rawNumbersEndPos);
        String rawStringTable = serializedString.substring(stringTableStartPos, stringTableEndPos);
        String rawFlags = serializedString.substring(flagsStartPos, flagsEndPos);

        return new ServerReadFormatBuilder(rawNumbers, rawStringTable, rawFlags);
    }

}
