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
package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadHolderInit;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.thread.ThreadManagerInit;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ThreadManagerTestNG {
    
    ThreadManager manager;
    int threadAmount = 20;

    @Test(groups = {"Thread", "Server"})
    public void threadManager() {
        testBegin("ThreadManager", "");
        
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        
        manager = ThreadManagerInit.threadManager(holder);
        
        LightSearchThread thread = new LightSearchThread(new ThreadTest());
        
        manager.holder().add("ThreadTest", thread);
        manager.holder().getThread("ThreadTest").start();
        
        
//        if(manager.interrupt("ThreadTest"))
//            System.out.println("Interrupt for 1 thread succeed");
//        
//        assertTrue(manager.holder().getThreads().isEmpty(), "holder not null!");
//        
//        for(int i = 0; i < threadAmount; i++) {
//            manager.holder().add("ThreadTest" + i, new Thread(new ThreadTest()));
//            manager.holder().getThread("ThreadTest" + i).start();
//        }
//        
//        manager.holder().add("Test", new Thread(new TestStart()));
//        manager.holder().getThread("Test").start();
        
        testEnd("ThreadManager", "");
    }
    
    public class TestStart implements Runnable {
        
        @Override
        public void run() {
            while(true) {
                if(manager.interruptAll("Test"))
                    System.out.println("Interrupt for " + threadAmount + " threads succeed");
                System.out.println("exit program");
                System.exit(0);
            }
        }
    }
    
    public class ThreadTest implements Runnable {
        
        @Override
        public void run() {
            while(true) {   
                try { Thread.sleep(1000); }
                catch(InterruptedException ignore) {}
                
                String hello = "hello";
                String world = "world";
                String helloWorld = hello + world;
                
                if(manager.interrupt("ThreadTest")) {
                    System.out.println("Interrupt for 1 thread succeed");
                    break;
                }
                else
                    System.out.println("FAIL");
                
                
                long a = 10000000;
                long b = 90;
                for(long i = 0; i < a; i++)
                    b++;
            }
        }
    }
}
