/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.cmd.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.exception.CommandResultException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("commandResultCreatorClientJSON")
@Scope("prototype")
public class CommandResultCreatorClientJSONImpl implements CommandResultCreator {

    private final String rawJSONResult;

    public CommandResultCreatorClientJSONImpl(String rawJSONResult) {
        this.rawJSONResult = rawJSONResult;
    }

    @Override
    public CommandResult createCommandResult() throws CommandResultException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(rawJSONResult, CommandResult.class);
        } catch (IOException ex) {
            throw new CommandResultException(ex.getMessage());
        }
    }
}
