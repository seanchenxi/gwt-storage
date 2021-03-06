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

import java.util.List;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.util.regexfilter.RegexFilter;

/**
 * Created by: Xi
 */
public final class StorageTypeFilter extends RegexFilter {

  public StorageTypeFilter(TreeLogger logger, List<String> regexes) throws UnableToCompleteException {
    super(logger, regexes);
  }

  @Override
  protected boolean acceptByDefault() {
    return true;
  }

  @Override
  protected boolean entriesArePositiveByDefault() {
    return false;
  }

}
