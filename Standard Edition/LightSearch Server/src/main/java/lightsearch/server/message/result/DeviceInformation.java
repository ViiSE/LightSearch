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
package lightsearch.server.message.result;

/**
 * Достает информацию о подключенном клиенте.
 * <p>
 * На данный момент в LightSearch Server существует два типа клиентов: администраторы и клиенты. Клиент имеет
 * идентификатор {@code client}, а администратор - {@code admin}.
 * <p>
 * Идентификатор отправляется клиентом LightSearch Server при подключении к нему. После этого LightSearch Server
 * определяет тип данного клиента и создает для него соответствующий обработчик.
 * <p>
 * Название данного интерфейса сложилось исторически. Так как в начале проекта планировалось только подключение либо
 * администраторов, работающих по ПК, либо клиентов работающих под Android, то интерфейс и был назван
 * {@code DeviceInformation}. Сейчас же концепция LightSearch Server такова, что можно писать каких-угодно клиентов под
 * любые устройства.
 * @author ViiSE
 * @see lightsearch.server.identifier.ConnectionIdentifier
 * @since 1.0
 */
public interface DeviceInformation {
    String identifier();
}
