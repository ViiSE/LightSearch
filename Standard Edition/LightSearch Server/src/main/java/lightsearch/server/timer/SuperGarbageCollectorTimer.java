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
import lightsearch.server.time.DateTimeComparatorInit;

/**
 * Абстрактный класс таймера сборщика мусора.
 * <p>
 * <b>Этот класс экспериментальный, и не следует его использовать. Применяйте его для тестов и экспериментов.</b>
 * <p>
 * Этот таймер вызывает через определенное количество времени сборщик мусора.
 * @author ViiSE
 * @see java.lang.System
 * @see java.lang.Runtime
 * @since 2.0
 */
public abstract class SuperGarbageCollectorTimer extends SuperTimer {
    
    private final DateTimeComparator dateTimeComparator = DateTimeComparatorInit.dateTimeComparator(null);
    
    public SuperGarbageCollectorTimer(LoggerServer loggerServer, 
            CurrentDateTime currentDateTime, ThreadManager threadManager, TimersIDEnum id) {
        super(loggerServer, currentDateTime, threadManager, id);
    }
    
    public DateTimeComparator dateTimeComparator() {
        return dateTimeComparator;
    }
}
