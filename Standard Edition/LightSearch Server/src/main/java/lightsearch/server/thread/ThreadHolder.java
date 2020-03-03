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

/**
 * Контейнер для всех активных потоков LightSearch Server. Используется в интерфейсе
 * {@link lightsearch.server.thread.ThreadManager}.
 * @author ViiSE
 * @since 1.0.0
 */
public interface ThreadHolder {
    void add(String id, LightSearchThread thread);
    void del(String id);
    void delAll();
    LightSearchThread getThread(String id);
    String getId(LightSearchThread thread);
    Collection<LightSearchThread> getThreads();
}
