/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.server.thread;

/**
 * Класс для работы с потоками в LightSearch Server.
 * <p>
 * В данном классе, в отличие от {@link java.lang.Thread}, можно управлять завершением потока более безопасно. Для этого
 * реализованы два поля: {@code isWorked} и {@code isDone}. isWorked показывает, работает поток или нет.
 * isDone показывает, завершил ли свою работу поток или нет.
 * <p>
 * В LightSearch Server многие потоки могут выполняться неопределенное количество времени в цикле. По этой причине
 * простой вызов метода {@code interrupt()} класса {@link java.lang.Thread} не гарантирует правильное завершение работы.
 * По этой причине и был написан данный класс.
 * <p>
 * Для реализации правильной работы необходимо в экземпляре класса, который реализует интерфейс
 * {@link java.lang.Runnable}, делать проверку на поле {@code isDone} и {@code isWorked}. Вместо цикла обработки
 * {@code while(true)} рекомендуется писать {@code while(lightSearchThreadInstance.isWorked())}. После изменения
 * значения поля {@code isWorked} на значение {@code false} (то есть когда произойдет выход из цикла обработки),
 * рекомендуется изменить значение {@code isDone} на {@code true}. Это можно сделать при помощи метода {@code setIsDone}.
 * <p>
 * Пусть имеется класс {@code Target}, который реализует интерфейс {@link java.lang.Runnable}. Тогда примерное
 * использование с классом {@code LightSearchThread} будет выглядеть так:
 * <p>
 * <pre> <code>
 *
 * public class Example {
 *
 *     public static void main(String[] args) {
 *         Target target = new Target();
 *         LightSearchThread thread = new LightSearchThread(Target);
 *         target.setThread(thread);
 *         System.out.println("1. thread.isWorked: " + thread.isWorked());
 *         thread.start();
 *         thread.setIsWorked(true);
 *         while(!thread.isDone())
 *         System.out.println("4. thread.isDone: " + thread.isDone());
 *         System.out.println("OK");
 *     }
 *
 *     private class Target implements Runnable {
 *         private LightSearchThread threadTarget; // так лучше не делать, это просто для примера
 *
 *         public void setThread(LightSearchThread threadTarget) {
 *             this.threadTarget = threadTarget;
 *         }
 *
 *         {@literal @} Override
 *         public void run() {
 *             System.out.println("2. thread.isWorked: " + targetThread.isWorked());
 *             while(threadTarget.isWorked()) {
 *                 //do something
 *             }
 *             System.out.println("3. thread.isDone: " + targetThread.isDone());
 *             threadTarget.setIsDone(true);
 *         }
 *     }
 * }
 * </code> </pre>
 * <p>
 * Применение смотрите в исходном коде обработчиков команд клиентов:
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Standard%20Edition/LightSearch%20Server/src/main/java/lightsearch/server/handler/admin/AdminHandlerDefaultImpl.java">здесь</a>,
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Standard%20Edition/LightSearch%20Server/src/main/java/lightsearch/server/handler/client/ClientHandlerDefaultImpl.java">здесь</a>,
 * и
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Standard%20Edition/LightSearch%20Server/src/main/java/lightsearch/server/handler/system/SystemHandlerDefaultImpl.java">здесь</a>,
 * Поле {@code isWorked} должно менятся извне другим, управляющим потоком. Для более удобного управления потоками в
 * данном пакете реализованы необходимые интерфейсы.
 * @author ViiSE
 * @since 1.0
 */
public class LightSearchThread extends Thread {
    
    private boolean isWorked = true;
    private boolean isDone = false;
    
    public LightSearchThread(Runnable target) {
        super(target);
    }
    
    public LightSearchThread() {
        super();
    }
    
    public boolean isWorked() {
        return isWorked;
    }
    
    public void setIsWorked(boolean isWorked) {
        this.isWorked = isWorked;
    }
    
    public boolean isDone() {
        return isDone;
    }
    
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
} 