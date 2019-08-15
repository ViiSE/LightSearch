/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.LightSearchThreadInit;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ViiSE
 */
@Service("garbageCollectorTimerExecutorDefault")
@Scope("prototype")
public class GarbageCollectorTimerExecutorDefaultImpl implements GarbageCollectorTimerExecutor {

    private final SuperGarbageCollectorTimer timer;
    
    public GarbageCollectorTimerExecutorDefaultImpl(SuperGarbageCollectorTimer timer) {
        this.timer = timer;
    }

    @Override
    public void startGarbageCollectorTimerExecutor() {
        LightSearchThread thread = LightSearchThreadInit.lightSearchThread(timer);
        thread.setDaemon(true);
        thread.start();
        timer.threadManager().holder().add(timer.id().stringValue(), thread);
    }
    
}
