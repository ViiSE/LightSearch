/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.DateTimeComparator;

/**
 *
 * @author ViiSE
 */
public abstract class SuperGarbageCollectorTimer extends SuperTimer {
    
    public SuperGarbageCollectorTimer(LoggerServer loggerServer,
            CurrentDateTime currentDateTime, ThreadManager threadManager, TimersIDEnum id) {
        super(loggerServer, currentDateTime, threadManager, id);
    }
}
