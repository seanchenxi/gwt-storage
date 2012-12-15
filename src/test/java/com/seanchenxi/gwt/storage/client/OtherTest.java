/*
 * Copyright 2012 Xi CHEN
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.seanchenxi.gwt.storage.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Created by: Xi
 */
public class OtherTest extends StorageTestUnit {

  public static void removeValue(StorageExt storage) throws StorageQuotaExceededException, SerializationException {
    final int initialSize = storage.size();
    final StorageKey<Integer> key = StorageKeyFactory.intKey("removeValue1");
    final Integer value1 = Integer.MAX_VALUE;
    final Integer value2 = Integer.MIN_VALUE;

    storage.put(key, value1);
    assertEquals("removeValue - storage size", initialSize + 1, storage.size());
    assertTrue("removeValue - containsKey", storage.containsKey(key));

    storage.put(StorageKeyFactory.intKey("removeValue2"), value2);
    assertEquals("removeValue - storage size", initialSize + 2, storage.size());
    assertTrue("removeValue - containsKey", storage.containsKey(StorageKeyFactory.intKey("removeValue2")));

    storage.remove(key);
    assertEquals("removeValue - storage size", initialSize + 1, storage.size());
    assertTrue("removeValue - doesn't containsKey", !storage.containsKey(key));

    storage.remove(StorageKeyFactory.intKey("removeValue2"));
    assertEquals("removeValue - storage size", initialSize, storage.size());
    assertTrue("removeValue - doesn't containsKey", !storage.containsKey(StorageKeyFactory.intKey("removeValue2")));
  }

  public static HandlerRegistration listenerTest(final StorageExt storage, StorageChangeEvent.Level level){
    storage.setEventLevel(level);
    trace("Storage Event Level set to " + level);
    return storage.addStorageChangeHandler(new StorageChangeEvent.Handler() {
      @Override
      public void onStorageChange(StorageChangeEvent event) {
        trace("onStorageChange - Type=" + event.getChangeType() + ", " +
              "Key=" + event.getKey() + ", " +
              "Data=" + event.getData() + ", " +
              "OldData=" + event.getOldData() + ", " +
              "Value=" + String.valueOf(event.getValue()) + ", " +
              "OldValue=" + String.valueOf(event.getOldValue()));
        if(event.getKey() != null)
          assertEquals("onStorageChange", event.getData(), storage.getString(event.getKey().name()));
      }
    });
  }
}
