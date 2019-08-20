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
package lightsearch.server.log;

import lightsearch.server.time.CurrentDateTime;

/**
 * Выводит лог в окно, в котором запущен LightSearch Server.
 * Используется для создания экземпляра реализации интерфейса {@link lightsearch.server.log.LoggerWindow} по умолчанию.
 * @since 1.0
 * @author ViiSE
 * @see lightsearch.server.log.LoggerWindowDefaultImpl
 */
public interface LoggerWindow {
    /**
     * Лог собирается следующим образом: {@code [currentDateTime] type: message}
     * @param type Тип сообщения.
     * @param currentDateTime Текущая дата.
     * @param message Сообщение лога.
     */
    void printLog(String type, CurrentDateTime currentDateTime, String message);
}
