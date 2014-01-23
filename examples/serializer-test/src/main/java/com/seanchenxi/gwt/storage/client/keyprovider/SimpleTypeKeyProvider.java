package com.seanchenxi.gwt.storage.client.keyprovider;


import com.seanchenxi.gwt.storage.client.GenericTestValue;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyProvider;
import com.seanchenxi.gwt.storage.client.TestValue;

public interface SimpleTypeKeyProvider extends StorageKeyProvider {

  @Key(prefix = "prefix", suffix = "suffix")
  StorageKey<Boolean> boolKey();

  @Key(prefix = "prefix", value = "byteKey00", suffix = "suffix")
  StorageKey<Byte> byteKey();

  @Key(value = "byteKey00", suffix = "suffix")
  StorageKey<Character> charKey();

  @Key(prefix = "prefix", value = "byteKey00")
  StorageKey<Double> doubleKey();

  @Key(prefix = "prefix")
  StorageKey<Float> floatKey();

  @Key(suffix = "suffix")
  StorageKey<Integer> intKey();

  StorageKey<Long> longKey();

  StorageKey<Short> shortKey();

  StorageKey<String> stringKey();


  StorageKey<Boolean> boolKey(String keyName);

  StorageKey<Byte> byteKey(String keyName);

  StorageKey<Character> charKey(String keyName);

  StorageKey<Double> doubleKey(String keyName);

  StorageKey<Float> floatKey(String keyName);

  @Key(value="shortKey1", suffix = "suffix")
  StorageKey<Integer> intKey(String keyName);

  @Key(prefix = "prefix", value="shortKey1")
  StorageKey<Long> longKey(String keyName);

  @Key(prefix = "prefix", value="shortKey1", suffix = "suffix")
  StorageKey<Short> shortKey(String keyName);

  @Key(prefix = "prefix", suffix = "suffix")
  StorageKey<String> stringKey(String keyName);

  @Key(suffix = "suffix")
  StorageKey<TestValue> testValueKey(String keyName);

  @Key(prefix = "prefix")
  StorageKey<GenericTestValue<TestValue>> genericTestValueKey(String keyName);


}
