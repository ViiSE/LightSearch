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

import lightsearch.server.identifier.HandlerIdentifier;

/**
 * Генерирует идентификатор для потока.
 * <p>
 * Необходим для однозначного определения потока. Это необходимо, например, для корректного завершения потока.
 * <p>
 * Идентификатор состоит из двух частей: буквенная часть ( {@code admin, client, и т.д.} ) и номерная часть
 * ( {@code 1, 2, 3, ...} ).
 * Идентификаторы потоков, в которых работают таймеры, состоят только из буквенной части.
 * @author ViiSE
 * @see HandlerIdentifier
 * @since 1.0
 */
public class LightSearchThreadID {
    
    public static String createID(String identifier, long identifierValue) {
        return identifier + identifierValue;
    }
}
