/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.settings;

import lightsearch.client.bot.TestCycle;

/**
 *
 * @author ViiSE
 */
public class BotSettingsDefaultImpl implements BotSettings {

    private final long delay;
    private final int amountCycle;
    private TestCycle testCycle;
    
    public BotSettingsDefaultImpl(long delay, int amountCycle) {
        this.delay = delay;
        this.amountCycle = amountCycle;
    }

    @Override
    public long delayBeforeSendingMessage() {
        return delay;
    }

    @Override
    public int amountCycle() {
        return amountCycle;
    }

    @Override
    public void setTestCycle(TestCycle testCycle) {
        this.testCycle = testCycle;
    }

    @Override
    public TestCycle testCycle() {
        return testCycle;
    }
    
}
