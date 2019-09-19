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
package lightsearch.server.cmd.admin.processor;

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandConverter;
import lightsearch.server.cmd.admin.AdminCommandConverterInit;
import lightsearch.server.cmd.admin.AdminCommandCreator;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
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
public class ChangeDatabaseProcessorTestNG {
    
    private AdminCommand initAdminCommand(String message) {
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertNotNull(admCmd.command(), "Admin command is null!");
            assertNotNull(admCmd.adminName(), "Admin name is null!");
            assertNotNull(admCmd.dbName(), "Database name is null!");
            assertNotNull(admCmd.ip(), "Database ip is null!");
            assertNotNull(admCmd.port(), "Database port is null!");
            assertNotEquals(admCmd.name(), "", "Admin name is null!");
            return admCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    @Test
    @Parameters({"changeDatabaseMessage", "logDirectoryName"})
    public void apply(String message, String logDirectoryName) {
        testBegin("ChangeDatabaseProcessor", "apply()");
        
        LightSearchServerDatabaseDTO databaseDTO = DataProviderCreator.createDataProvider(LightSearchServerDatabaseDTO.class);
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class, databaseDTO);
        System.out.println("databaseDTO BEFORE: ");
        System.out.println("\t dbName: " + databaseDTO.dbName());
        System.out.println("\t dbIP: " + databaseDTO.dbIP());
        System.out.println("\t dbPort: " + databaseDTO.dbPort());
        
        Map<String, Function<AdminCommand, CommandResult>> admCmdHolder = DataProviderCreator
                .createDataProvider(AdminCommandCreator.class, serverDTO, logDirectoryName)
                .createCommandHolder();
        AdminCommand admCmd = initAdminCommand(message);
        assertNotNull(admCmd, "Admin Command is null!");
        
        String command = admCmd.command();
        assertNotNull(command, "Command is null!");
        assertNotEquals(command, "", "Command is null!");
        
        Function<AdminCommand, CommandResult> processor = admCmdHolder.get(command);
        assertNotNull(processor, "processor is null!");
        CommandResult cmdRes = processor.apply(admCmd);
        
        System.out.println("Results:");
        System.out.println("databaseDTO: ");
        System.out.println("\t dbName: " + databaseDTO.dbName());
        System.out.println("\t dbIP: " + databaseDTO.dbIP());
        System.out.println("\t dbPort: " + databaseDTO.dbPort());
            
        System.out.println("CommandResult.Type: " + cmdRes.type());
        System.out.println("CommandResult.Message: " + cmdRes.message());
        System.out.println("CommandResult.LogMessage: " + cmdRes.logMessage());
    
        
        testEnd("ChangeDatabaseProcessor", "apply()");
    }
}
