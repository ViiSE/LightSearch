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
package test.thread;

import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.data.ThreadParametersHolderInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ThreadParametersHolderTestNG {
    
    @Test
    public void id() {
        testBegin("ThreadParametersHolder", "id()");
        
        String id = "admin1";
        assertNotNull(id, "Thread id is null!");
        assertFalse(id.equals(""), "Thread id is null!");
        ThreadParametersHolder threadParametersHolder = ThreadParametersHolderInit.threadParametersHolder(id);
        System.out.println(threadParametersHolder.id());
        
        testEnd("ThreadParametersHolder", "id()");
    }
}