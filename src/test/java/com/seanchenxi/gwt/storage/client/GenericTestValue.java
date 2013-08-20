package com.seanchenxi.gwt.storage.client;

import java.io.Serializable;

/**
 * Created by: Xi
 * Date Time: 20/08/13 23:56
 */
public class GenericTestValue<T extends TestValue> implements Serializable{

  private T testValue;

  public GenericTestValue(){

  }

  public GenericTestValue(T testValue){
    this.testValue = testValue;
  }

  public T getTestValue(){
    return testValue;
  }

  public void setTestValue(T testValue){
    this.testValue = testValue;
  }

  @Override
  public boolean equals(Object o){
    if(this == o) return true;
    if(!(o instanceof GenericTestValue)) return false;

    GenericTestValue that = (GenericTestValue)o;

    if(!testValue.equals(that.testValue)) return false;

    return true;
  }

  @Override
  public int hashCode(){
    return testValue.hashCode();
  }
}
