/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

import lightsearch.server.thread.LightSearchThread;

/**
 * Реализация интерфейса {@link lightsearch.server.timer.GarbageCollectorTimerExecutor} по умолчанию.
 * <p>
 * <b>Этот класс экспериментальный и не следует его использовать в релизе. Применяйте его для тестов и экспериментов.</b>
 * <p>
 * Запускает таймер сборщика мусора в отдельном потоке-демоне.
 * @author ViiSE
 * @see lightsearch.server.timer.SuperGarbageCollectorTimer
 * @see lightsearch.server.thread.ThreadManager
 * @see lightsearch.server.daemon.DaemonServer
 * @since 2.0
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
