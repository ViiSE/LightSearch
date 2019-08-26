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
 * Реализация интерфейса {@link lightsearch.server.timer.GarbageCollectorTimerCreator} по умолчанию.
 * <p>
 * <b>Этот класс экспериментальный и не следует его использовать в релизе. Применяйте его для тестов и экспериментов.</b>
 * Делает системный вызов сборщика мусора {@link System#gc()} каждую секунду через секунду после запуска.
 * @author ViiSE
 * @since 2.0.0
 */
public class GarbageCollectorTimerCreatorDefaultImpl implements GarbageCollectorTimerCreator {

    private final LoggerServer loggerServer;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final TimersIDEnum id;

    public GarbageCollectorTimerCreatorDefaultImpl(LoggerServer loggerServer, 
            CurrentDateTime currentDateTime, ThreadManager threadManager, 
            TimersIDEnum id) {
        this.loggerServer = loggerServer;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.id = id;
    }

    @Override
    public SuperGarbageCollectorTimer getTimer() {
        return new GarbageCollectorAbstractDefaultExt(
                loggerServer, currentDateTime, threadManager, id);
    }
    
}
