/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
