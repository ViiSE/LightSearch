/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package test.client.processor.debug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.client.ClientCommandCreatorDebugDefaultImpl;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.ClientDAOInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.exception.CommandConverterException;
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
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadHolderInit;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.thread.ThreadManagerInit;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.timer.TimersIDEnum;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AuthenticationProcessorClientDebugTestNG {
    
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
    
    private DatabaseRecordIdentifier initIdentifier(LightSearchServerDTO serverDTO) {
        DatabaseRecordIdentifierReader identifierReader = DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
        return identifier;
    }
    
    private ThreadManager initThreadManager() {
        HashMap<String,LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(holder);
        return threadManager;
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
    
    private Map<String, Function<ClientCommand, CommandResult>> init(LightSearchServerDTO serverDTO,
            ClientDAO clientDAO) {
        LightSearchListenerDTO listenerDTO = initListenerDTO(serverDTO);
        ClientCommandCreator clientCmdCreator = new ClientCommandCreatorDebugDefaultImpl(serverDTO, 
                listenerDTO, clientDAO);
        
        return clientCmdCreator.createCommandHolder();
    }
    
    private ClientCommand initClientCommand() {
        try {
            String message = "{"
                            + "\"command\":\"connect\","
                            + "\"IMEI\":\"123456789123456\","
                            + "\"ip\":\"127.0.0.1\","
                            + "\"os\":\"Android 8.1 Oreo\","
                            + "\"model\":\"Nexus 5\","
                            + "\"username\":\"androidUser\","
                            + "\"password\":\"superSecretPass!12\""
                        + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.ip(), "Client IP is null!");
            assertNotNull(clientCmd.os(), "Client OS is null!");
            assertNotNull(clientCmd.model(), "Client model is null!");
            assertNotNull(clientCmd.username(), "Client username is null!");
            assertNotNull(clientCmd.password(), "Client password is null!");
            assertFalse(clientCmd.IMEI().equals(""), "Client IMEI is null!");
            assertFalse(clientCmd.ip().equals(""), "Client IP is null!");
            assertFalse(clientCmd.os().equals(""), "Client OS is null!");
            assertFalse(clientCmd.model().equals(""), "Client model is null!");
            assertFalse(clientCmd.username().equals(""), "Client username is null!");
            assertFalse(clientCmd.password().equals(""), "Client password is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    @Test
    public void apply() {
        testBegin("AuthenticationProcessor: Client", "apply()");
        
        LightSearchServerDTO serverDTO = initDTO();
        System.out.println("serverDTO.clients BEFORE: ");
        serverDTO.clients().keySet().forEach((clientName) -> {
            System.out.println("\t" + clientName + " " + serverDTO.clients().get(clientName));
        });
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        System.out.println("clientDAO BEFORE: ");
        System.out.println("\t clientDAO.isFirst: " + clientDAO.isFirst());
        System.out.println("\t clientDAO.IMEI: " + clientDAO.IMEI());
        
        Map<String, Function<ClientCommand, CommandResult>> clientCmdHolder = init(serverDTO, clientDAO);
        ClientCommand clientCmd = initClientCommand();
        assertNotNull(clientCmd, "Client Command is null!");
        
        String command = clientCmd.command();
        assertNotNull(command, "Command is null!");
        assertFalse(command.equals(""), "Command is null!");
        
        Function<ClientCommand, CommandResult> processor = clientCmdHolder.get(command);
        assertNotNull(processor, "processor is null!");
        CommandResult cmdRes = processor.apply(clientCmd);
        
        System.out.println("Results:");
        System.out.println("serverDTO.clients: ");
        serverDTO.clients().keySet().forEach((clientName) -> {
            System.out.println("\t" + clientName + " " + serverDTO.clients().get(clientName));
        });
        
        System.out.println("clientDAO: ");
        System.out.println("\t clientDAO.isFirst: " + clientDAO.isFirst());
        System.out.println("\t clientDAO.IMEI: " + clientDAO.IMEI());
        
        System.out.println("CommandResult.Type: " + cmdRes.type());
        System.out.println("CommandResult.Message: " + cmdRes.message());
        System.out.println("CommandResult.LogMessage: " + cmdRes.logMessage());
    
        testEnd("AuthenticationProcessor: Client", "apply()");
    }
}