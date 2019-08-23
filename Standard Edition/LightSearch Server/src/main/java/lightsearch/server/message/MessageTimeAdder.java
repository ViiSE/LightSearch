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
package lightsearch.server.message;

/**
 * Проделывает операции с временем.
 * <p>
 * Используется в отладке LightSearch Server для замеров скорости работы блоков программы. При помощи этого интерфейса
 * можно суммировать время и высчитывать среднее значение всех замеров.
 * @author ViiSE
 * @see lightsearch.server.message.MessageRecipientDebugImpl
 * @see lightsearch.server.cmd.client.processor.debug
 * @since 1.0
 */
public interface MessageTimeAdder {
    void add(long time);
    long averageTime();
    void clear();
}
