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
package test.admin.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandConverter;
import lightsearch.server.cmd.admin.AdminCommandConverterInit;
import lightsearch.server.cmd.admin.AdminCommandCreator;
import lightsearch.server.cmd.admin.AdminCommandCreatorInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.AdminDAOInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.identifier.*;
import lightsearch.server.initialization.AdministratorsMapInit;
import lightsearch.server.initialization.ClientBlacklistInit;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.DatabaseSettings;
import lightsearch.server.initialization.DatabaseSettingsInit;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.initialization.ServerPort;
import lightsearch.server.initialization.ServerPortInit;
import lightsearch.server.initialization.ServerSettings;
import lightsearch.server.initialization.ServerSettingsInit;
import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LogDirectoryInit;
import lightsearch.server.log.LoggerFile;
import lightsearch.server.log.LoggerFileInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.log.LoggerServerInit;
import lightsearch.server.log.LoggerWindow;
import lightsearch.server.log.LoggerWindowInit;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadHolderInit;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.thread.ThreadManagerInit;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import lightsearch.server.timer.TimersIDEnum;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class CreateAdminProcessorTestNG {
    
    private LightSearchServerDTO initDTO() {
        CurrentServerDirectory currentDirectory;
        currentDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        Map<String,String> admins = AdministratorsMapInit.administratorsMap(currentDirectory).administratorsMap();
        Map<String,String> clients = new HashMap<>();
        List<String> blacklist = ClientBlacklistInit.clientBlacklist(currentDirectory).blacklist();
        
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentDirectory);
        String dbName = dbSettings.name();
        String dbIP = dbSettings.ip();
        int dbPort = dbSettings.port();

        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentDirectory);
        int rebootServer = serverSettings.rebootServerValue();
        int timeoutClient = serverSettings.timeoutClientValue();
        
        ServerPort serverPort = ServerPortInit.serverPort(currentDirectory);
        int sPort = serverPort.port();
        
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                dbIP, 
                dbName, 
                dbPort);
        
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(rebootServer, timeoutClient);
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(
                admins,
                clients,
                blacklist,
                databaseDTO,
                sPort,
                settingsDTO,
                currentDirectory.currentDirectory());
        
        serverDTO.clients().put("123456789123456", "user");
        serverDTO.clients().put("789544131546489", "user1");
        serverDTO.clients().put("555364213589963", "user2");
        
        return serverDTO;
    }
    
    private LoggerServer initLoggerServer() {
        LogDirectory logDir = LogDirectoryInit.logDirectory("logs");
        LoggerFile logFile = LoggerFileInit.loggerFile(logDir);
        LoggerWindow logWindow = LoggerWindowInit.loggerWindow();
        LoggerServer logServer = LoggerServerInit.loggerServer(logFile, logWindow);
        return logServer;
    }
    
    private ThreadManager initThreadManager() {
        HashMap<String,LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(holder);
        return threadManager;
    }
    
    private DatabaseRecordIdentifier initIdentifier(LightSearchServerDTO serverDTO) {
        DatabaseRecordIdentifierReader identifierReader = DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
        return identifier;
    }
    
    private LightSearchListenerDTO initListenerDTO(LightSearchServerDTO serverDTO) {
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = initThreadManager();
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        DatabaseRecordIdentifier databaseRecordIdentifier = initIdentifier(serverDTO);
        DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter = DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        
        LightSearchListenerDTO listenerDTO = LightSearchListenerDTOInit.lightSearchListenerDTO(
                checker, currentDateTime, threadManager, databaseRecordIdentifier,
                databaseRecordIdentifierWriter, timerRebootId);
        
        return listenerDTO;
    }
    
    private Map<String, Function<AdminCommand, CommandResult>> init(LightSearchServerDTO serverDTO) {
        LoggerServer logger = initLoggerServer();
        LightSearchListenerDTO listenerDTO = initListenerDTO(serverDTO);
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        AdminCommandCreator admCmdCreator = AdminCommandCreatorInit.adminCommandCreator(serverDTO, 
                listenerDTO, logger, adminDAO);
        
        return admCmdCreator.createCommandHolder();
    }
    
    private AdminCommand initAdminCommand() {
        try {
            String message = "{"
                    + "\"name\":\"admin\","
                    + "\"command\":\"createAdmin\","
                    + "\"adminName\":\"newAdmin\","
                    + "\"password\":\"5c29a959abce4eda5f0e7a4e7ea53dce4fa0f0abbe8eaa63717e2fed5f193d31\"" //newPassword - sha256
                    + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd;
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertNotNull(admCmd.command(), "Admin command is null!");
            assertNotNull(admCmd.adminName(), "AdminName is null!");
            assertNotNull(admCmd.password(), "Admin password is null!");
            assertFalse(admCmd.name().equals(""), "Admin name is null!");
            return admCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    @Test
    public void apply() {
        testBegin("CreateAdminProcessor", "apply()");
        
        LightSearchServerDTO serverDTO = initDTO();
        System.out.println("serverDTO.admins BEFORE: ");
        serverDTO.admins().keySet().forEach((name) -> {
            System.out.println("\t" + name + " " + serverDTO.admins().get(name));
        });
   
        Map<String, Function<AdminCommand, CommandResult>> admCmdHolder = init(serverDTO);
        AdminCommand admCmd = initAdminCommand();
        assertNotNull(admCmd, "Admin Command is null!");
        
        String command = admCmd.command();
        assertNotNull(command, "Command is null!");
        assertFalse(command.equals(""), "Command is null!");
        
        Function<AdminCommand, CommandResult> processor = admCmdHolder.get(command);
        assertNotNull(processor, "processor is null!");
        CommandResult cmdRes = processor.apply(admCmd);
        
        System.out.println("Results:");
        System.out.println("serverDTO.admins: ");
        serverDTO.admins().keySet().forEach((name) -> {
            System.out.println("\t" + name + " " + serverDTO.admins().get(name));
        });
            
        System.out.println("CommandResult.Type: " + cmdRes.type());
        System.out.println("CommandResult.Message: " + cmdRes.message());
        System.out.println("CommandResult.LogMessage: " + cmdRes.logMessage());
    
        testEnd("CreateAdminProcessor", "apply()");
    }
}
