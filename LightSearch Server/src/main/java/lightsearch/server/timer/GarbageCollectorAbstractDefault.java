/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;

/**
 *
 * @author ViiSE
 */
public class GarbageCollectorAbstractDefault extends SuperGarbageCollectorTimer {

    private final String ID;
    
    public GarbageCollectorAbstractDefault(LoggerServer loggerServer, 
            CurrentDateTime currentDateTime, ThreadManager threadManager, TimersIDEnum id) {
        super(loggerServer, currentDateTime, threadManager, id);
        ID = super.id().stringValue();
    }

    @Override
    public void run() {
        while(super.threadManager().holder().getThread(ID).isWorked()) {
            while(true) {
                try { Thread.sleep(1000); } catch(InterruptedException ignored) {}
                System.gc();
            }
        }
        super.threadManager().holder().getThread(ID).setIsDone(true);
    }
    
}
