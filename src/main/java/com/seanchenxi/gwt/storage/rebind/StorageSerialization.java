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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by: Xi
 */
@XmlRootElement(name = "StorageSerialization")
@XmlAccessorType(XmlAccessType.FIELD)
public class StorageSerialization {

  @XmlElement(required = true, name = "class")
  private List<String> classes;

  @XmlElement(required = false, name = "autoArrayType", defaultValue = "true")
  private boolean autoArrayType = true;

  @XmlElement(required = false, name = "includePrimitiveTypes", defaultValue = "true")
  private boolean includePrimitiveTypes = true;

  @XmlTransient
  private String path;

  public List<String> getClasses() {
    return classes;
  }

  public String getPath(){
    return path;
  }

  public boolean isAutoArrayType(){
    return this.autoArrayType;
  }

  public boolean isIncludePrimitiveTypes(){
    return includePrimitiveTypes;
  }

  public void setClasses(List<String> classes){
    this.classes = classes;
  }

  public void setAutoArrayType(boolean autoArrayType){
    this.autoArrayType = autoArrayType;
  }

  public void setIncludePrimitiveTypes(boolean includePrimitiveTypes){
    this.includePrimitiveTypes = includePrimitiveTypes;
  }

  public void setPath(String path){
    this.path = path;
  }

}
