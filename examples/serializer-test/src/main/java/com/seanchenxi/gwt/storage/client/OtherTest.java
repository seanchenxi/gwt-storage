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

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Created by: Xi
 */
public class OtherTest {

  public static void removeValue(StorageExt storage) throws StorageQuotaExceededException, SerializationException {
    final int initialSize = storage.size();
    final StorageKey<Integer> key = StorageKeyFactory.intKey("removeValue1");
    final Integer value1 = Integer.MAX_VALUE;
    final Integer value2 = Integer.MIN_VALUE;

    storage.put(key, value1);
    storage.put(StorageKeyFactory.intKey("removeValue2"), value2);
    StorageTestUtil.assertTrue("removeValue - containsKey", storage.containsKey(key));
    StorageTestUtil.assertTrue("removeValue - containsKey", storage.containsKey(StorageKeyFactory.intKey("removeValue2")));

    storage.remove(key);
    storage.remove(StorageKeyFactory.intKey("removeValue2"));
    StorageTestUtil.assertEquals("removeValue - storage size", initialSize, storage.size());
    StorageTestUtil.assertTrue("removeValue - doesn't containsKey", !storage.containsKey(key));
    StorageTestUtil.assertTrue("removeValue - doesn't containsKey", !storage.containsKey(StorageKeyFactory.intKey("removeValue2")));
  }

  public static HandlerRegistration listenerTest(final StorageExt storage, StorageChangeEvent.Level level){
    storage.setEventLevel(level);
    StorageTestUtil.trace("Storage Event Level set to " + level, false);
    return storage.addStorageChangeHandler(new StorageChangeEvent.Handler() {
      @Override
      public void onStorageChange(StorageChangeEvent event) {
        StorageTestUtil.event("onStorageChange - Type=" + event.getChangeType() + ", " +
                              "Key=" + event.getKey() + ", " +
                              "Data=" + event.getData() + ", " +
                              "OldData=" + event.getOldData() + ", " +
                              "Value=" + String.valueOf(event.getValue()) + ", " +
                              "OldValue=" + String.valueOf(event.getOldValue()), false);
        if(event.getKey() != null){
          String value = storage.getString(event.getKey().name());
          String expected = event.getData();
          if ((expected == null && value == null) || (expected != null && expected.equals(value)))
            StorageTestUtil.event("onStorageChange - assertEquals succeed.", false);
          else
            StorageTestUtil.event("onStorageChange -  assertEquals error: expected=" + event.getData() + ", but given=" + value, true);
        }
        StorageTestUtil.event("==", false);
      }
    });
  }
}
