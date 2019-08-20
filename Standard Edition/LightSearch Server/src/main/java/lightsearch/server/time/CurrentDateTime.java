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
package lightsearch.server.time;

/**
 * Предоставляет текущее время.
 * <p>
 * Данный интерфейс используется для вывода даты и времени в логе, для создания имени файла лога, для работы таймеров, и
 * для записи текущего времени в таблицы {@code LS_REQUEST} и {@code LS_RESPONSE} базы данных. Для каждого из
 * вышеперечисленного функционального требования необходим свой формат времени.
 * @author ViiSE
 * @see lightsearch.server.cmd.admin.processor.RestartProcessor
 * @see lightsearch.server.cmd.changer.ServerStateChanger
 * @see lightsearch.server.cmd.client.processor.AuthenticationProcessor
 * @see lightsearch.server.cmd.client.processor.CancelSoftCheckProcessor
 * @see lightsearch.server.cmd.client.processor.CloseSoftCheckProcessor
 * @see lightsearch.server.cmd.client.processor.ConfirmSoftCheckProductsProcessor
 * @see lightsearch.server.cmd.client.processor.OpenSoftCheckProcessor
 * @see lightsearch.server.cmd.client.processor.SearchProcessor
 * @see lightsearch.server.handler.Handler
 * @see lightsearch.server.cmd.client.processor.AuthenticationProcessor
 * @see lightsearch.server.timer.SuperTimer
 * @since 1.0
 */
public interface CurrentDateTime {
    /**
     * @return Текущая дата и время в формате {@code ISO8601}: {@code yyyy-MM-dd HH:mm:ss}.
     */
    String dateTimeInStandardFormat();

    /**
     * @return Текущая дата и время в формате {@code dd.MM.yyyy HH:mm:ss}.
     */
    String dateTimeWithDot();

    /**
     * @return Текущая дата в формате {@code dd-MM-yyyy}.
     */
    String dateLog();
}
