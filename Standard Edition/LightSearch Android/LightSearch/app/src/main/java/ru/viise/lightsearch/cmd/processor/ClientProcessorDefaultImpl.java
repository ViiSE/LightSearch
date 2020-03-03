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

package ru.viise.lightsearch.cmd.processor;

import java.util.function.Function;

import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.data.CommandDTO;

public class ClientProcessorDefaultImpl implements ClientProcessor {

    private final Function<CommandDTO, CommandResult> processor;

    public ClientProcessorDefaultImpl(Function<CommandDTO, CommandResult> processor) {
        this.processor = processor;
    }

    @Override
    public CommandResult apply(CommandDTO cmdDTO) {
        return processor.apply(cmdDTO);
    }
}
