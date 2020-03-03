/*
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.admin.panel.util;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MapRemoverTestNG {

    private Map<String, String> clients;

    @BeforeClass
    public void setUpMethod() {
        clients = new HashMap<>();
        clients.put("1", "11");
        clients.put("2", "22");
        clients.put("3", "33");
        clients.put("4", "44");
        clients.put("5", "55");
        clients.put("6", "66");
    }

    @Test
    @Parameters({"key", "value"})
    public void removeFromMap(String key, String value) {
        testBegin("MapRemover", "removeFromMap()");

        System.out.println("Map clients. Before:");
        clients.forEach((key_, value_) -> System.out.println("k:" + key_ + " v:" + value_ ));

        assertNotNull(key, "Key is null!");
        
        MapRemover mapRemover = MapRemoverInit.mapRemover();
        assertNotNull(mapRemover, "MapRemover is null!");
        
        if(mapRemover.removeFromMap(clients, key))
            System.out.println("Remove from map clients with key = " + key);
        else
            System.out.println("Cannot remove from map clients with key = " + key);
    
        System.out.println("Map clients. After:");
        clients.forEach((key_, value_) -> System.out.println("k:" + key_ + " v:" + value_ ));
        
        assertNotNull(value, "Value is null!");
        
        if(mapRemover.removeFromMap(clients, value))
            System.out.println("Remove from map clients with value = " + value);
        else
            System.out.println("Cannot remove from map clients with value = " + value);
    
        System.out.println("Map clients. After:");
        clients.forEach((key_, value_) -> System.out.println("k:" + key_ + " v:" + value_ ));
        
        testEnd("MapRemover", "removeFromMap()");
    }
}
