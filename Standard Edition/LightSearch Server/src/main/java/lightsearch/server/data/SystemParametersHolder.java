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

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.data.stream.DataStream;

/**
 * Контейнер параметров системного бота LightSearch Server.
 * @author ViiSE
 * @see lightsearch.server.handler.system
 * @since 2.0
 */
public interface SystemParametersHolder {
    Socket systemSocket();
    DataStream dataStream();
    Map<String, Function<SystemCommand, CommandResult>> commandHolder();
}
