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

package com.seanchenxi.gwt.storage.server;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.gwt.storage.client.service.TestService;
import com.seanchenxi.gwt.storage.client.value.GenericTestValue;
import com.seanchenxi.gwt.storage.client.value.TestValue;
import com.seanchenxi.gwt.storage.shared.RpcTestMapKey;
import com.seanchenxi.gwt.storage.shared.RpcTestMapValue;
import com.seanchenxi.gwt.storage.shared.RpcTestValue;
import com.seanchenxi.gwt.storage.shared.StorageUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.*;

/**
 * Created by: Xi
 */
public class TestServiceImpl extends RemoteServiceServlet implements TestService {

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    StorageUtils.PolicyLoader.load(config.getServletContext(), "storage_test");
  }

  @Override
  public RpcTestValue getRpcTestValue(){
    return new RpcTestValue(12345L);
  }

  @Override
  public List<RpcTestValue> getRpcTestValueList(){
    LinkedList<RpcTestValue> rpcTestValues = new LinkedList<RpcTestValue>();
    rpcTestValues.add(new RpcTestValue(234567L));
    rpcTestValues.add(new RpcTestValue(456789L));
    return rpcTestValues;
  }

  @Override
  public Map<RpcTestMapKey, RpcTestMapValue> getRpcTestValueStringMap(){
    HashMap<RpcTestMapKey, RpcTestMapValue> rpcTestMapKeyRpcTestMapValueHashMap = new HashMap<RpcTestMapKey, RpcTestMapValue>();
    rpcTestMapKeyRpcTestMapValueHashMap.put(new RpcTestMapKey(RpcTestMapKey.MapKey.KEY_1), new RpcTestMapValue(new RpcTestMapValue.MapValue("KEY_1_VALUE")));
    rpcTestMapKeyRpcTestMapValueHashMap.put(new RpcTestMapKey(RpcTestMapKey.MapKey.KEY_2), new RpcTestMapValue(new RpcTestMapValue.MapValue("KEY_2_VALUE")));
    return rpcTestMapKeyRpcTestMapValueHashMap;
  }

  @Override
  public TestValue testDeserialization(TestValue value, String serialized, TestValue stored) throws SerializationException {
    Object[] serverSide = test(value, serialized);
    return (TestValue) serverSide[1];
  }

  @Override
  public String testSerialization(TestValue value, String serialized, TestValue stored) throws SerializationException {
    Object[] serverSide = test(value, serialized);
    return (String) serverSide[0];
  }

  @Override
  public GenericTestValue<TestValue> testGenericDeserialization(GenericTestValue<TestValue> value1, String serialized) throws SerializationException {
    Object[] serverSide = test(value1, serialized);
    return (GenericTestValue<TestValue>) serverSide[1];
  }

  @Override
  public String testGenericSerialization(GenericTestValue<TestValue> value1, String serialized) throws SerializationException {
    Object[] serverSide = test(value1, serialized);
    return (String) serverSide[0];
  }

  private <T> Object[] test(T value, String serialized) throws SerializationException {
    try {
      Object serverDeserialized = StorageUtils.deserialize(Object.class, serialized);
      if(!Objects.equals(value, serverDeserialized)){
        System.err.println("expected=" + value + ", got=" + serverDeserialized);
        throw new SerializationException("expected=" + value + ", got=" + serverDeserialized);
      }
      String serverSerialized = StorageUtils.serialize(value);
      if(!Objects.equals(serialized, serverSerialized)){
        System.err.println("expected=" + serialized + ", got=" + serverSerialized);
        throw new SerializationException("expected=" + serialized + ", got=" + serverSerialized);
      }
      return new Object[]{serverSerialized, serverDeserialized};
    } catch (SerializationException e) {
      throw e;
    } catch (Exception e) {
      throw new SerializationException(e);
    }
  }

}
