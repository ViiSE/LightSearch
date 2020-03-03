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
import test.TestUtils;

import java.util.HashMap;

import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ThreadManagerTestNG {
    
    private ThreadManager manager;
    private int threadAmount;

    @Test
    @Parameters({"threadAmount", "openSecondTest"})
    public void threadManager(int threadAmount, boolean openSecondTest) {
        testBegin("ThreadManager", "");

        this.threadAmount = threadAmount;

        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        
        manager = ThreadManagerInit.threadManager(holder);
        
        LightSearchThread thread = new LightSearchThread(new ThreadTest("ThreadTest"));
        
        manager.holder().add("ThreadTest", thread);
        manager.holder().getThread("ThreadTest").start();

        if(manager.interrupt("ThreadTest"))
            System.out.println("Interrupt for 1 thread succeed");

        assertTrue(manager.holder().getThreads().isEmpty(), "holder not null!");

        if(openSecondTest) {
            for (int i = 0; i < threadAmount; i++) {
                manager.holder().add("ThreadTest" + i, new LightSearchThread(new ThreadTest("ThreadTest" + i)));
                manager.holder().getThread("ThreadTest" + i).start();
            }

            manager.holder().add("Test", new LightSearchThread(new TestStart()));
            manager.holder().getThread("Test").start();

            while(manager.holder().getThread("Test") != null)  {
                // wait
            }
        }
        
        testEnd("ThreadManager", "");
    }
    
    private class TestStart implements Runnable {
        
        @Override
        public void run() {
            while(true) {
                if(manager.interruptAll("Test"))
                    System.out.println("Interrupt for " + threadAmount + " threads succeed");
                System.out.println("exit test");
                break;
            }
        }
    }
    
    private class ThreadTest implements Runnable {

        private final String id;

        ThreadTest(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            while(manager.holder().getThread(id).isWorked()) {
                TestUtils.sleep(1000);
                String hello = "hello";
                String world = "world";
                String helloWorld = hello + world;
                hello = null;
                world = null;
            }
            manager.holder().getThread(id).setIsDone(true);
        }
    }
}
