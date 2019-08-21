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
 * Таймер сборщика мусора.
 * <p>
 * <b>Этот класс экспериментальный и не следует его использовать в релизе. Применяйте его для тестов и экспериментов.</b>
 * <p>
 * Вы
 * @author ViiSE
 */
public class GarbageCollectorAbstractDefaultExt extends SuperGarbageCollectorTimer {

    private final String ID;
    
    public GarbageCollectorAbstractDefaultExt(LoggerServer loggerServer,
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
