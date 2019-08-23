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
package lightsearch.server.data;

/**
 * DAO администратора LightSearch Server.
 * @author ViiSE
 * @see lightsearch.server.cmd.admin.AdminCommand
 * @see lightsearch.server.cmd.admin.processor.AuthenticationProcessor
 * @see lightsearch.server.handler.admin
 * @since 2.0
 */
public interface AdminDAO {
    String name();
    /**
     * Проверяет, в первый ли раз администратор делает попытку авотризации или нет.
     * @return Результат проверки.
     */
    boolean isFirst();
    /**
     * @return Количество сделанных попыток авторизации в LightSearch Server.
     */
    short tryNumber();
    /**
     * @return Количество попыток авторизации в LightSearch Server, которые может сделать администратор.
     */
    short maxTryNumber();
    void setName(String name);
    /**
     * Устанавливает, в первый ли раз администратор делает попытку авторизации или нет.
     * @param isFirst указывает, в первый ли раз администратор делает попытку авторизации или нет.
     */
    void setIsFirst(boolean isFirst);
    /**
     * Инкрементирует количество сделанных попыток авторизации в LightSearch Server.
     */
    void incrementTryNumber();
}
