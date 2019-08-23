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

import java.util.List;
import java.util.Map;

/**
 * DTO LightSearch Server.
 * @author ViiSE
 * @see lightsearch.server.cmd
 * @see lightsearch.server.cmd.admin
 * @see lightsearch.server.cmd.client
 * @see lightsearch.server.cmd.system
 * @see lightsearch.server.handler
 * @see lightsearch.server.handler.processor
 * @see lightsearch.server.identifier
 * @see lightsearch.server.listener
 * @since 2.0
 */
public interface LightSearchServerDTO {
    Map<String, String> admins();
    Map<String, String> clients();
    List<String> blacklist();
    LightSearchServerDatabaseDTO databaseDTO();
    int serverPort();
    String currentDirectory();
    LightSearchServerSettingsDAO settingsDAO();
}
