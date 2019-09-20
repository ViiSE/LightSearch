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
package lightsearch.server.thread;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ThreadHolderTestNG {
    
    @Test
    @Parameters("id")
    public void add(String id) {
        testBegin("ThreadHolder", "add()");
        
        assertNotNull(id, "Thread id is null!");
        assertNotEquals(id, "", "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        holder.add(id, new LightSearchThread());
        System.out.println("Add: holder: " + holder);
        System.out.println(holder.getThreads());
        
        testEnd("ThreadHolder", "add()");
    }

    @Test
    @Parameters("id")
    public void del(String id) {
        testBegin("ThreadHolder", "del()");
        
        assertNotNull(id, "Thread id is null!");
        assertNotEquals(id, "", "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        holder.add(id, new LightSearchThread());
        System.out.println("Del: holder before: " + holder);
        System.out.println(holder.getThreads());
        holder.del(id);
        System.out.println("Del: holder after: " + holder);
        System.out.println(holder.getThreads());
        
        testEnd("ThreadHolder", "del()");
    }

    @Test
    @Parameters("id")
    public void getThread(String id) {
        testBegin("ThreadHolder", "getThread()");

        assertNotNull(id, "Thread id is null!");
        assertNotEquals(id, "", "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        holder.add(id, new LightSearchThread());
        System.out.println("getThread: " + holder.getThread(id));
        
        testEnd("ThreadHolder", "getThread()");
    }

    @Test
    @Parameters("id")
    public void delAll(String id) {
        testBegin("ThreadHolder", "delAll()");

        String id1 = id;
        String id2 = id + "2";
        String id3 = id + "3";
        String id4 = id + "4";
        String id5 = id + "5";
        assertNotNull(id1, "Thread id is null!");
        assertNotEquals(id1, "", "Thread id is null!");
        assertNotNull(id2, "Thread id is null!");
        assertNotEquals(id2, "", "Thread id is null!");
        assertNotNull(id3, "Thread id is null!");
        assertNotEquals(id3, "", "Thread id is null!");
        assertNotNull(id4, "Thread id is null!");
        assertNotEquals(id4, "", "Thread id is null!");
        assertNotNull(id5, "Thread id is null!");
        assertNotEquals(id5, "", "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        holder.add(id1, new LightSearchThread());
        holder.add(id2, new LightSearchThread());
        holder.add(id3, new LightSearchThread());
        holder.add(id4, new LightSearchThread());
        holder.add(id5, new LightSearchThread());
        System.out.println("Del ALL: holder before: " + holder);
        System.out.println(holder.getThreads());
        holder.delAll();
        System.out.println("Del ALL: holder after: " + holder);
        System.out.println(holder.getThreads());
        
        testEnd("ThreadHolder", "delAll()");
    }

    @Test
    @Parameters("id")
    public void getThreads(String id) {
        testBegin("ThreadHolder", "getThreads()");

        String id1 = id;
        String id2 = id + "2";
        String id3 = id + "3";
        String id4 = id + "4";
        String id5 = id + "5";
        assertNotNull(id1, "Thread id is null!");
        assertNotEquals(id1, "", "Thread id is null!");
        assertNotNull(id2, "Thread id is null!");
        assertNotEquals(id2, "", "Thread id is null!");
        assertNotNull(id3, "Thread id is null!");
        assertNotEquals(id3, "", "Thread id is null!");
        assertNotNull(id4, "Thread id is null!");
        assertNotEquals(id4, "", "Thread id is null!");
        assertNotNull(id5, "Thread id is null!");
        assertNotEquals(id5, "", "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        holder.add(id1, new LightSearchThread());
        holder.add(id2, new LightSearchThread());
        holder.add(id3, new LightSearchThread());
        holder.add(id4, new LightSearchThread());
        holder.add(id5, new LightSearchThread());
        System.out.println("getThreads: " + holder.getThreads());
        
        testEnd("ThreadHolder", "getThreads()");
    }

    @Test
    @Parameters({"id"})
    public void getId(String id) {
        testBegin("ThreadHolder", "getId()");

        assertNotNull(id, "Thread id is null!");
        assertNotEquals(id, "", "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        LightSearchThread thread = new LightSearchThread();
        holder.add(id, thread);
        System.out.println("getId: " + holder.getId(thread));
        
        testEnd("ThreadHolder", "getId()");
    }
}
