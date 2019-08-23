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
 * Реализация интерфейса {@link lightsearch.server.message.MessageTimeAdder} по умолчанию.
 * <p>
 * Так как при отладке сервера время замера скорости блока программы начинается сразу после работы LightSearch Server,
 * то первое значение замера будет много больше, чем реальное время обработки блока программы. Поэтому первый замер
 * опускается.
 * <p>
 * После проведенного замера времени выполнения блока программы необходимо вызвать метод {@link #clear()} для того,
 * чтобы статическая переменная {@link #isFirst} снова имело значение {@code true}.
 * @author ViiSE
 * @see lightsearch.server.message.MessageRecipientDebugImpl
 * @see lightsearch.server.cmd.client.processor.debug
 * @since 1.0
 */
public class MessageTimeAdderDefaultImpl implements MessageTimeAdder {

    private static boolean isFirst = true;
    private static long count = 0;
    private static long sum = 0;
    
    @Override
    synchronized public void add(long time) {
        if(time >= 0) {
            if(isFirst)
                isFirst = false;
            else {
                count++;
                sum += time;
            }
        }
    }

    @Override
    public long averageTime() {
        if(count == 0) return 0;
        return (sum / count);
    }

    @Override
    synchronized public void clear() {
        count = 0;
        sum = 0;
        isFirst = true;
    }
}
