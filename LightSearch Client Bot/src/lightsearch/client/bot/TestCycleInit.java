/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

import java.util.List;
import lightsearch.client.bot.processor.Processor;

/**
 *
 * @author ViiSE
 */
public class TestCycleInit {
    
    private static TestCycle testCycle(List<Processor> processors) {
        return new TestCycleDefaulImpl(processors);
    }
}
