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
package lightsearch.server.initialization;

/**
 * Предоставляет текущую директорию исполняемого jar-файла LightSearch Server.
 * <p>
 * Текущей директорией является полное имя директории, в которой расположен исполняемый jar-файл, без имени этого jar-а.
 * <p>
 * Используется в таких реализациях интерфейсов, как:
 * <p>
 * {@link lightsearch.server.initialization.AdministratorsMap};
 * <p>
 * {@link lightsearch.server.initialization.ClientBlacklist};
 * <p>
 * {@link lightsearch.server.initialization.DatabaseSettings};
 * <p>
 * {@link lightsearch.server.initialization.ServerPort};
 * <p>
 * {@link lightsearch.server.initialization.ServerSettings};
 * <p>
 * {@link lightsearch.server.log.LogDirectory}.
 * @author ViiSE
 * @see lightsearch.server.initialization.AdministratorsMapFromFileDefaultImpl
 * @see lightsearch.server.initialization.ClientBlacklistFromFileDefaultImpl
 * @see lightsearch.server.initialization.DatabaseSettingsFromFileDefaultImpl
 * @see lightsearch.server.initialization.ServerPortFromFileDefaultImpl
 * @see lightsearch.server.initialization.ServerSettingsFromFileDefaultImpl
 * @see lightsearch.server.log.LogDirectoryDefaultImpl
 * @since 1.0.0
 */
public interface CurrentServerDirectory {
    String currentDirectory();
}
