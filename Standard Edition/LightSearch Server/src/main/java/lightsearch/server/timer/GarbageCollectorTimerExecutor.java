/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.server.timer;

/**
 * Запускает таймер сборщика мусора.
 * <p>
 * <b>Этот интерфейс экспериментальный и не следует его использовать в релизе. Применяйте его для тестов и экспериментов.</b>
 * @author ViiSE
 * @see lightsearch.server.timer.GarbageCollectorTimer
 * @since 2.0.0
 */
public interface GarbageCollectorTimerExecutor {
    void startGarbageCollectorTimerExecutor();
}
