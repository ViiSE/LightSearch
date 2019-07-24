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
package lightsearch.client.bot;

import java.util.List;
import lightsearch.client.bot.exception.TestCycleOutOfBoundException;
import lightsearch.client.bot.processor.Processor;

/**
 *
 * @author ViiSE
 */
public class TestCycleDefaulImpl implements TestCycle {

    private final List<Processor> processors;
    private int index = 0;
    
    public TestCycleDefaulImpl(List<Processor> processors) {
        this.processors = processors;
    }

    @Override
    public Processor next() throws TestCycleOutOfBoundException {
        try { return processors.get(index++); }
        catch(IndexOutOfBoundsException ignore) {
            index = 0;
            throw new TestCycleOutOfBoundException("Cycle done.");
        }
    }
    
}
