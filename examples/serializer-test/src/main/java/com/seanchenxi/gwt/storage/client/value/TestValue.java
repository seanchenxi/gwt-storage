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
package com.seanchenxi.gwt.storage.client.value;

import java.io.Serializable;

/**
 * Created by: Xi
 */
public class TestValue implements Serializable {

  private String value;

  public TestValue(){}

  public TestValue(String value){
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TestValue)) return false;

    TestValue testValue = (TestValue) o;

    return value == null ? (testValue.value == null) : value.equals(testValue.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
