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
public class GarbageCollectorTimerCreatorInit {
    
    public static GarbageCollectorTimerCreator garbageCollectorTimerCreator(
            LoggerServer loggerServer, CurrentDateTime currentDateTime, 
            ThreadManager threadManager, TimersIDEnum id) {
        return new GarbageCollectorTimerCreatorDefaultImpl(loggerServer, 
                currentDateTime, threadManager, id);
    }
}
