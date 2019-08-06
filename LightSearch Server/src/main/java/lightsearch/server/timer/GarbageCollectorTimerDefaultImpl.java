/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ViiSE
 */
public class GarbageCollectorTimerDefaultImpl implements GarbageCollectorTimer {

    @Override
    public void start() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> { System.gc(); }, 1, 1000, TimeUnit.MILLISECONDS);
    }
    
}
