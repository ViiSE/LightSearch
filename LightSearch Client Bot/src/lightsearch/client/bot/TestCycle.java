/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

import lightsearch.client.bot.exception.TestCycleOutOfBoundException;
import lightsearch.client.bot.processor.Processor;

/**
 *
 * @author ViiSE
 */
public interface TestCycle {
    Processor next() throws TestCycleOutOfBoundException;
}
