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

import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.gwt.storage.client.RpcTestMapKey;
import com.seanchenxi.gwt.storage.client.RpcTestMapValue;
import com.seanchenxi.gwt.storage.client.RpcTestValue;
import com.seanchenxi.gwt.storage.client.service.TestService;

/**
 * Created by: Xi
 */
public class TestServiceImpl extends RemoteServiceServlet implements TestService {

  @Override
  public RpcTestValue getRpcTestValue(){
    return null;
  }

  @Override
  public List<RpcTestValue> getRpcTestValueList(){
    return null;
  }

  @Override
  public Map<RpcTestMapKey, RpcTestMapValue> getRpcTestValueStringMap(){
    return null;
  }
}
