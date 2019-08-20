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

import java.util.Collection;
import java.util.Map;

/**
 * Реализация интерфейса {@link lightsearch.server.thread.ThreadHolder} по умолчанию.
 * <p>
 * Потоки хранятся в {@link java.util.Map}. Ключом является идентификатор потока, а значением сам поток.
 * @author ViiSE
 * @since 1.0
 */
public class ThreadHolderDefaultImpl implements ThreadHolder {

    private final Map<String, LightSearchThread> threads;
    
    public ThreadHolderDefaultImpl(Map<String, LightSearchThread> holder) {
        this.threads = holder;
    }

    @Override
    public void add(String id, LightSearchThread thread) {
        threads.put(id, thread);
    }
    
    @Override
    public void del(String id) {
        threads.remove(id);
    }

    @Override
    public LightSearchThread getThread(String id) {
        return threads.get(id);
    }

    @Override
    public void delAll() {
        threads.clear();
    }

    @Override
    public Collection<LightSearchThread> getThreads() {
        return threads.values();
    }

    @Override
    public String getId(LightSearchThread thread) {
        for (String element : threads.keySet()) {
            if (threads.get(element).equals(thread)) {
                return element;
            }
        }
        return null;
    }
    
}
