/*
 *  Copyright 2019 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package lightsearch.updater.util;

/**
 * Предоставляет текущее время.
 * <p>
 * Данный интерфейс используется для вывода даты и времени в логе и для создания имени файла лога.
 * @author ViiSE
 * @since 1.0.0
 */
public interface CurrentDateTime {
    /**
     * @return Текущая дата в формате {@code dd-MM-yyyy}.
     */
    String dateLog();

    /**
     * @return Текущая дата и время в формате {@code dd.MM.yyyy HH:mm:ss}.
     */
    String dateTimeWithDot();
}