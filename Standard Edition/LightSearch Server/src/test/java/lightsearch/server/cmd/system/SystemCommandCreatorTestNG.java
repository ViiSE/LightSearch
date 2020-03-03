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
package lightsearch.server.cmd.system;

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.util.Map;
import java.util.function.Function;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SystemCommandCreatorTestNG {
    
    @Test
    public void createCommandHolder() {
        testBegin("SystemCommandCreator", "createCommandHolder()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        
        SystemCommandCreator cmdCreator = SystemCommandCreatorInit.systemCommandCreator(serverDTO, listenerDTO);
        
        Map<String, Function<SystemCommand, CommandResult>> cmdHolder = cmdCreator.createCommandHolder();
        assertNotNull(cmdHolder, "Command Holder is null!");
        
        System.out.println("Command holder: ");
        cmdHolder.keySet().forEach((command) ->
                System.out.println("\tCommand name: " + command + "; Processor: " + cmdHolder.get(command)));
        
        testEnd("SystemCommandCreator", "createCommandHolder()");
    }
}
