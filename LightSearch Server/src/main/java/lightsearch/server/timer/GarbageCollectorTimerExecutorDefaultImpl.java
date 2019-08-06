/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

import lightsearch.server.thread.LightSearchThread;

/**
 *
 * @author ViiSE
 */
public class GarbageCollectorTimerExecutorDefaultImpl implements GarbageCollectorTimerExecutor {

    private final SuperGarbageCollectorTimer timer;
    
    public GarbageCollectorTimerExecutorDefaultImpl(SuperGarbageCollectorTimer timer) {
        this.timer = timer;
    }

    @Override
    public void startGarbageCollectorTimerExecutor() {
        LightSearchThread thread = new LightSearchThread(timer);
        thread.setDaemon(true);
        thread.start();
        timer.threadManager().holder().add(timer.id().stringValue(), thread);
    }
    
}
