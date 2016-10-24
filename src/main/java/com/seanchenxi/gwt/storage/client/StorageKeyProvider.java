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

package com.seanchenxi.gwt.storage.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A tag interface for the StorageKey generator. Instances of StorageKey are
 * created by declaring factory methods on a subtype of this interface.
 *
 * <pre>
 * interface MyStorageKeyProvider extends StorageKeyProvider {
 *   // A factory method for a ArrayList type StorageKey
 *   StorageKey&lt;ArrayList&lt;String>> strListKey();
 *   // A factory method for a wrapper bean
 *   &#64;Key(prefix= "pre-", suffix="-suf")
 *   StorageKey&lt;Boolean> boolKey(String keyName);
 * }
 * </pre>
 */
public interface StorageKeyProvider {

  /**
   * Used for adding additional and generic information to generated key
   */
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  @interface Key {

    String value() default "";

    String prefix() default "";

    String suffix() default "";

  }

}
