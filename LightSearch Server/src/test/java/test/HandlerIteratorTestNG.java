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

import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.iterator.HandlerIteratorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class HandlerIteratorTestNG {
    
    @Test
    public void next() {
        testBegin("HandlerIterator", "next()");
        
        HandlerIterator handlerIterator = HandlerIteratorInit.handlerIterator();
        String id = "admin";
        System.out.println(id);
        String newId1 =  id + handlerIterator.next();
        System.out.println(newId1);
        String newId2 =  id + handlerIterator.next();
        System.out.println(newId2);
        String newId3 =  id + handlerIterator.next();
        System.out.println(newId3);
        
        HandlerIterator newHandlerIterator = HandlerIteratorInit.handlerIterator();
        
        String newNewId = id + newHandlerIterator.next();
        System.out.println(newNewId);
        
        assertFalse(newNewId.equals(newId1), "Number of id is equals!");
        assertFalse(newNewId.equals(newId2), "Number of id is equals!");
        assertFalse(newNewId.equals(newId3), "Number of id is equals!");
        
        testEnd("HandlerIterator", "next()");
    }
}
