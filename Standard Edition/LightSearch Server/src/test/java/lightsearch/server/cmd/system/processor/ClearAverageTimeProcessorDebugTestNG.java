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
package lightsearch.server.cmd.system.processor;

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.cmd.system.SystemCommandConverter;
import lightsearch.server.cmd.system.SystemCommandConverterInit;
import lightsearch.server.cmd.system.SystemCommandCreator;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.exception.CommandConverterException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.util.Map;
import java.util.function.Function;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ClearAverageTimeProcessorDebugTestNG {
    
    private SystemCommand initSystemCommand(String message) {
        try {
            SystemCommandConverter sysCmdConverter = SystemCommandConverterInit.systemCommandConverter();
            SystemCommand sysCmd;
            sysCmd = sysCmdConverter.convertToSystemCommand(message);
            assertNotNull(sysCmd, "System Command is null!");
            assertNotNull(sysCmd.command(), "System command is null!");
            
            return sysCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    @Test
    @Parameters({"clearAvgTimeMessage"})
    public void apply(String message) {
        testBegin("ClearAverageTimeProcessorDebug", "apply()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        
        Map<String, Function<SystemCommand, CommandResult>> clientCmdHolder =
                DataProviderCreator.createDataProvider(SystemCommandCreator.class, serverDTO)
                .createCommandHolder();
        SystemCommand sysCmd = initSystemCommand(message);
        assertNotNull(sysCmd, "Client Command is null!");
        
        String command = sysCmd.command();
        assertNotNull(command, "Command is null!");
        assertNotEquals(command, "", "Command is null!");
        
        Function<SystemCommand, CommandResult> processor = clientCmdHolder.get(command);
        assertNotNull(processor, "processor is null!");
        CommandResult cmdRes = processor.apply(sysCmd);
        
        System.out.println("Results:");
        System.out.println("CommandResult.Type: " + cmdRes.type());
        System.out.println("CommandResult.Message: " + cmdRes.message());
        System.out.println("CommandResult.LogMessage: " + cmdRes.logMessage());
    
        testEnd("ClearAverageTimeProcessorDebug", "apply()");
    }
}
