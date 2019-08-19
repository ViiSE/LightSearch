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
 * Интерфейс, предоставляющий текущую директорию исполняемого jar-файла LightSearch Server.
 * <p>
 * Текущей директорией является полное имя директории, в которой расположен исполняемый jar-файл, без имени этого jar-а.
 * <p>
 * Используется в таких реализациях интерфейсов, как:
 * <p>
 * {@link lightsearch.server.initialization.AdministratorsMap} :
 * {@link lightsearch.server.initialization.AdministratorsMapFromFileDefaultImpl};
 * <p>
 * {@link lightsearch.server.initialization.ClientBlacklist} :
 * {@link lightsearch.server.initialization.ClientBlacklistFromFileDefaultImpl};
 * <p>
 * {@link lightsearch.server.initialization.DatabaseSettings} :
 * {@link lightsearch.server.initialization.DatabaseSettingsFromFileDefaultImpl};
 * <p>
 * {@link lightsearch.server.initialization.ServerPort} :
 * {@link lightsearch.server.initialization.ServerPortFromFileDefaultImpl};
 * <p>
 * {@link lightsearch.server.initialization.ServerSettings} :
 * {@link lightsearch.server.initialization.ServerSettingsFromFileDefaultImpl};
 * <p>
 * {@link lightsearch.server.log.LogDirectory} :
 * {@link lightsearch.server.log.LogDirectoryDefaultImpl};
 * <p>
 * @since 1.0
 * @author ViiSE
 */
public interface CurrentServerDirectory {
    String currentDirectory();
}
