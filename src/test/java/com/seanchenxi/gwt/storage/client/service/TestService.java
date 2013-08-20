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

package com.seanchenxi.gwt.storage.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.seanchenxi.gwt.storage.client.RpcTestMapKey;
import com.seanchenxi.gwt.storage.client.RpcTestMapValue;
import com.seanchenxi.gwt.storage.client.RpcTestValue;

/**
 * Created by: Xi
 */
public interface TestService extends RemoteService {

  RpcTestValue getRpcTestValue();

  List<RpcTestValue> getRpcTestValueList();

  HashMap<RpcTestMapKey, RpcTestMapValue> getRpcTestValueStringMap();

}
