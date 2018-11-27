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

class RpcProtocolReversing {

    interface Builder {
        String build();
    }

    private static final String BLANK = "";
    private static final String CLOSE_BRACKET = "]";
    private static final String COMMA = ",";
    private static final String OPEN_BRACKET = "[";


    static Builder forServerRead(String serializedString){
        int stringTableStartPos = serializedString.indexOf(OPEN_BRACKET, 1);
        int stringTableEndPos = serializedString.indexOf(CLOSE_BRACKET, stringTableStartPos);

        String[] numbers = serializedString.substring(1, stringTableStartPos).split(COMMA);
        String[] stringTable = serializedString.substring(stringTableStartPos + 2, stringTableEndPos - 1).split("\"" + COMMA + "\\s*\"");
        String[] flags = serializedString.substring(stringTableEndPos + 1, serializedString.length() - 1).split(COMMA);

        return new Builder() {

            @Override
            public String build() {
                StringBuilder sb = new StringBuilder();
                reverseAppend(sb, flags);
                appendStringTable(sb, stringTable);
                reverseAppend(sb, numbers);
                return sb.toString();
            }

            private void append(StringBuilder sb, String value){
                sb.append(value).append("|");
            }

            private void appendStringTable(StringBuilder sb, String[] stringTable){
                append(sb, stringTable.length+"");
                append(sb, String.join("|", stringTable));
            }

            private void reverseAppend(StringBuilder sb, String[] array) {
                for (int i = array.length - 1; i >= 0; i--) {
                    if(array[i] == null || array[i].length() < 1){
                        continue;
                    }
                    String str = array[i].replaceAll("^(\"|')", BLANK).replaceAll("(\"|')$", BLANK);
                    append(sb, str);
                }
            }
        };
    }

}
