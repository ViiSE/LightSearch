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

package ru.viise.lightsearch.data;

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;

public class CommandManagerAsyncTaskDTODefaultImpl implements CommandManagerAsyncTaskDTO {

    private final CommandManager commandManager;
    private final CommandTypeEnum type;
    private final CommandDTO commandDTO;

    public CommandManagerAsyncTaskDTODefaultImpl(CommandManager commandManager, CommandTypeEnum type,
             CommandDTO commandDTO) {
        this.commandManager = commandManager;
        this.type = type;
        this.commandDTO = commandDTO;
    }

    @Override
    public CommandManager commandManager() {
        return commandManager;
    }

    @Override
    public CommandTypeEnum type() {
        return type;
    }

    @Override
    public CommandDTO commandDTO() {
        return commandDTO;
    }
}
