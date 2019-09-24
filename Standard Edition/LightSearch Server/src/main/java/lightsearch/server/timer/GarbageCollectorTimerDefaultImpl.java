/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Реализация интерфейса {@link lightsearch.server.timer.GarbageCollectorTimer} по умолчанию.
 * <p>
 * <b>Этот класс экспериментальный и не следует его использовать в релизе. Применяйте его для тестов и экспериментов.</b>
 * <p>
 * Делает системный вызов сборщика мусора {@link System#gc()} каждую секунду через секунду после запуска.
 * @author ViiSE
 * @since 2.0.0
 */
public class GarbageCollectorTimerDefaultImpl implements GarbageCollectorTimer {

    @Override
    public void start() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(System::gc, 1, 1000, TimeUnit.MILLISECONDS);
    }
    
}
