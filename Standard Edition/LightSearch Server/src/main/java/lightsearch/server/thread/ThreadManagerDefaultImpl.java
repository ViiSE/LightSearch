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
 * Реализация интерфейса {@link lightsearch.server.thread.ThreadManagerDefaultImpl} по умолчанию.
 * <p>
 * Вызов метода {@code interrupt()} этого класса сделан с учетом возможностей
 * {@link lightsearch.server.thread.LightSearchThread}.
 * <p>
 * Применяется повсеместно.
 * @author ViiSE
 * @since 1.0
 */
public class ThreadManagerDefaultImpl implements ThreadManager {

    private final ThreadHolder holder;
    
    ThreadManagerDefaultImpl(ThreadHolder holder) {
        this.holder = holder;
    }

    @Override
    public boolean interrupt(String id) {
        LightSearchThread thread = holder.getThread(id);
        if(thread != null) {
            thread.interrupt();
            while(!thread.isInterrupted())
                thread.interrupt();
            while(!thread.isDone())
                thread.setIsWorked(false);
            holder.del(id);
        }
        return true;
    }

    @Override
    public boolean interruptAll(String timerId) {
        holder.getThreads().forEach((thread) -> {
            if(holder.getThread(timerId) != null && 
                    holder.getThread(timerId) != thread) {
                thread.interrupt();
                while(!thread.isInterrupted()) {
                    thread.interrupt();
                }
                if(thread.isWorked())
                    thread.setIsWorked(false);
            }
        });
        holder.delAll();
        return true;
    }

    @Override
    public ThreadHolder holder() {
        return holder;
    }
}
