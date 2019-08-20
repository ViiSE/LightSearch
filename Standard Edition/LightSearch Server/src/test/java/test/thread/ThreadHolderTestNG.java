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

import java.util.HashMap;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadHolderInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ThreadHolderTestNG {
    
    @Test
    public void add() {
        testBegin("ThreadHolder", "add()");
        
        String id = "admin1";
        assertNotNull(id, "Thread id is null!");
        assertFalse(id.equals(""), "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        holder.add(id, new LightSearchThread());
        System.out.println("Add: holder: " + holder);
        System.out.println(holder.getThreads());
        
        testEnd("ThreadHolder", "add()");
    }

    @Test
    public void del() {
        testBegin("ThreadHolder", "del()");
        
        String id = "admin1";
        assertNotNull(id, "Thread id is null!");
        assertFalse(id.equals(""), "Thread id is null!");
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
    public void getThread() {
        testBegin("ThreadHolder", "getThread()");
        
        String id = "admin1";
        assertNotNull(id, "Thread id is null!");
        assertFalse(id.equals(""), "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        holder.add(id, new LightSearchThread());
        System.out.println("getThread: " + holder.getThread(id));
        
        testEnd("ThreadHolder", "getThread()");
    }

    @Test
    public void delAll() {
        testBegin("ThreadHolder", "delAll()");
        
        String id1 = "admin1";
        String id2 = "admin2";
        String id3 = "admin3";
        String id4 = "admin4";
        String id5 = "admin5";
        assertNotNull(id1, "Thread id is null!");
        assertFalse(id1.equals(""), "Thread id is null!");
        assertNotNull(id2, "Thread id is null!");
        assertFalse(id2.equals(""), "Thread id is null!");
        assertNotNull(id3, "Thread id is null!");
        assertFalse(id3.equals(""), "Thread id is null!");
        assertNotNull(id4, "Thread id is null!");
        assertFalse(id4.equals(""), "Thread id is null!");
        assertNotNull(id5, "Thread id is null!");
        assertFalse(id5.equals(""), "Thread id is null!");
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
    public void getThreads() {
        testBegin("ThreadHolder", "getThreads()");
        
        String id1 = "admin1";
        String id2 = "admin2";
        String id3 = "admin3";
        String id4 = "admin4";
        String id5 = "admin5";
        assertNotNull(id1, "Thread id is null!");
        assertFalse(id1.equals(""), "Thread id is null!");
        assertNotNull(id2, "Thread id is null!");
        assertFalse(id2.equals(""), "Thread id is null!");
        assertNotNull(id3, "Thread id is null!");
        assertFalse(id3.equals(""), "Thread id is null!");
        assertNotNull(id4, "Thread id is null!");
        assertFalse(id4.equals(""), "Thread id is null!");
        assertNotNull(id5, "Thread id is null!");
        assertFalse(id5.equals(""), "Thread id is null!");
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
    public void getId() {
        testBegin("ThreadHolder", "getId()");
        
        String id = "admin1";
        assertNotNull(id, "Thread id is null!");
        assertFalse(id.equals(""), "Thread id is null!");
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        LightSearchThread thread = new LightSearchThread();
        holder.add(id, thread);
        System.out.println("getId: " + holder.getId(thread));
        
        testEnd("ThreadHolder", "getId()");
    }
}
