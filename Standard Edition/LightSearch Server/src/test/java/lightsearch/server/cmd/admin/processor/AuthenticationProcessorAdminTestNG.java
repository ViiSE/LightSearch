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
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.AdminDAOInit;
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
public class AuthenticationProcessorAdminTestNG {

    private AdminCommand initAdminCommand(String message) {
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertNotNull(admCmd.command(), "Admin command is null!");
            assertNotNull(admCmd.password(), "Admin password is null!");
            assertNotEquals(admCmd.name(), "", "Admin name is null!");
            return admCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }

    @Test
    @Parameters({"authenticationAdminMessage", "logDirectoryName"})
    public void apply(String message, String logDirectoryName) {
        testBegin("AuthenticationProcessor: Admin", "apply()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        System.out.println("serverDTO.admins BEFORE: ");
        serverDTO.admins().keySet().forEach((adminName) ->
                System.out.println("\t" + adminName + " " + serverDTO.admins().get(adminName)));
        
        System.out.println("serverDTO.blacklist BEFORE: ");
        if(serverDTO.blacklist().isEmpty())
            System.out.println("null");
        else
            for(int i = 0; i < serverDTO.blacklist().size(); i++)
                System.out.println("\t" + serverDTO.blacklist().get(i));
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        System.out.println("adminDAO BEFORE: ");
        System.out.println("\t adminDAO.tryNumber: " + adminDAO.tryNumber());
        System.out.println("\t adminDAO.maxTryNumber: " + adminDAO.maxTryNumber());
        
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
        System.out.println("serverDTO.admins: ");
        serverDTO.admins().keySet().forEach((adminName) ->
                System.out.println("\t" + adminName + " " + serverDTO.admins().get(adminName)));
        System.out.println("serverDTO.blacklist: ");
        if(serverDTO.blacklist().isEmpty())
            System.out.println("null");
        else
            for(int i = 0; i < serverDTO.blacklist().size(); i++)
                System.out.println("\t" + serverDTO.blacklist().get(i));
        
        System.out.println("adminDAO: ");
        System.out.println("\t adminDAO.tryNumber: " + adminDAO.tryNumber());
        System.out.println("\t adminDAO.maxTryNumber: " + adminDAO.maxTryNumber());
        
        System.out.println("CommandResult.Type: " + cmdRes.type());
        System.out.println("CommandResult.Message: " + cmdRes.message());
        System.out.println("CommandResult.LogMessage: " + cmdRes.logMessage());
    
        testEnd("AuthenticationProcessor: Admin", "apply()");
    }
}
