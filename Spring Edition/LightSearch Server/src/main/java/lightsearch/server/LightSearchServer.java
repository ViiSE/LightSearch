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
package lightsearch.server;

import lightsearch.server.about.AppGreetings;
import lightsearch.server.about.EndStartupMessage;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.TimerRebootValueChecker;
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.data.*;
import lightsearch.server.initialization.*;
import lightsearch.server.iterator.*;
import lightsearch.server.listener.LightSearchServerListener;
import lightsearch.server.log.*;
import lightsearch.server.thread.*;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
@SpringBootApplication
public class LightSearchServer {

    private static final String GREETINGS                       = "appGreetingsDefault";
    private static final String OS_DETECTOR                     = "osDetectorDefault";
    private static final String CURRENT_SERVER_DIRECTORY        = "currentServerDirectoryFromFileDefault";
    private static final String SERVER_PORT                     = "serverPortFromFileDefault";
    private static final String SERVER_SETTINGS                 = "serverSettingsFromFileDefault";
    private static final String ADMINISTRATORS_MAP              = "administratorsMapFromFileDefault";
    private static final String CLIENT_BLACKLIST                = "clientBlacklistFromFileDefault";
    private static final String DATABASE_SETTINGS               = "databaseSettingsFromFileDefault";
    private static final String LIGHTSEARCH_SERVER_SETTINGS_DAO = "lightSearchServerSettingsDAODefault";
    private static final String LIGHTSEARCH_SERVER_DATABASE_DTO = "lightSearchServerDatabaseDTODefault";
    private static final String LIGHTSEARCH_SERVER_DTO          = "lightSearchServerDTODefault";
    private static final String LOG_DIRECTORY                   = "logDirectoryDefault";
    private static final String LOGGER_FILE                     = "loggerFileDefault";
    private static final String LOGGER_WINDOW                   = "loggerWindowDefault";
    private static final String LOGGER_SERVER                   = "loggerServerDefault";
    private static final String CURRENT_DATE_TIME               = "currentDateTimeDefault";
    private static final String THREAD_HOLDER                   = "threadHolderDefault";
    private static final String THREAD_MANAGER                  = "threadManagerDefault";
    private static final String ITERATOR_DATABASE_RECORD_READER = "iteratorDatabaseRecordReaderDefault";
    private static final String ITERATOR_DATABASE_RECORD_WRITER = "iteratorDatabaseRecordWriterDefault";
    private static final String ITERATOR_DATABASE_RECORD        = "iteratorDatabaseRecordDefault";
    private static final String LIGHTSEARCH_CHECKER             = "lightSearchCheckerDefault";
    private static final String LIGHTSEARCH_LISTENER_DTO        = "lightSearchListenerDTODefault";
    private static final String END_STARTUP_MESSAGE             = "endStartUpMessageDefault";
    private static final String SERVER_STATE_CHANGER            = "serverStateChangerDefault";
    private static final String TIMER_REBOOT_VALUE_CHECKER      = "timerRebootValueCheckerDefault";
    private static final String LIGHTSEARCH_LISTENER            = "lightSearchServerListenerSocketDefault";
    private static final String ITERATOR_DATABASE_RECORD_WRITER_TIMER_CREATOR_DTO = "iteratorDatabaseRecordWriterTimerCreatorDTODefault";

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(LightSearchServer.class, args);
        AppGreetings greetings = ctx.getBean(GREETINGS, AppGreetings.class);

        System.out.println(greetings.greetings());

        OsDetector osDetector = ctx.getBean(OS_DETECTOR, OsDetector.class);
        CurrentServerDirectory currentServerDirectory = (CurrentServerDirectory) ctx.getBean(CURRENT_SERVER_DIRECTORY, osDetector);
        String currentDirectory = currentServerDirectory.currentDirectory();
        
        ServerPort serverPort = (ServerPort) ctx.getBean(SERVER_PORT, currentServerDirectory);
        int port = serverPort.port();

        ServerSettings serverSettings = (ServerSettings)
                ctx.getBean(SERVER_SETTINGS, currentServerDirectory);
        int serverReboot = serverSettings.rebootServerValue();
        int clientTimeout = serverSettings.timeoutClientValue();
        
        AdministratorsMap administratorsMap = (AdministratorsMap) ctx.getBean(ADMINISTRATORS_MAP, currentDirectory);
        Map<String, String> admins = administratorsMap.administratorsMap();

        ClientBlacklist clientBlacklist = (ClientBlacklist) ctx.getBean(CLIENT_BLACKLIST, currentServerDirectory);
        List<String> blacklist = clientBlacklist.blacklist();
        
        DatabaseSettings dbSettings = (DatabaseSettings) ctx.getBean(DATABASE_SETTINGS, currentServerDirectory);
        String dbName = dbSettings.name();
        int dbPort = dbSettings.port();
        String dbIp = dbSettings.ip();
        
        LightSearchServerSettingsDAO settingsDAO = (LightSearchServerSettingsDAO) ctx.getBean(
                LIGHTSEARCH_SERVER_SETTINGS_DAO, serverReboot, clientTimeout);
        
        LightSearchServerDatabaseDTO databaseDTO = (LightSearchServerDatabaseDTO) ctx.getBean(
                LIGHTSEARCH_SERVER_DATABASE_DTO, dbIp, dbName, dbPort);
        
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) ctx.getBean(
                LIGHTSEARCH_SERVER_DTO, admins, new HashMap<>(), blacklist, databaseDTO, port, settingsDAO, currentDirectory);

        LogDirectory logDirectory = ctx.getBean(LOG_DIRECTORY, LogDirectory.class);
        LoggerFile loggerFile = (LoggerFile) ctx.getBean(LOGGER_FILE, logDirectory);
        LoggerWindow loggerWindow = ctx.getBean(LOGGER_WINDOW, LoggerWindow.class);
        
        LoggerServer loggerServer = (LoggerServer) ctx.getBean(LOGGER_SERVER, loggerFile, loggerWindow);
        
        CurrentDateTime currentDateTime = ctx.getBean(CURRENT_DATE_TIME, CurrentDateTime.class);
        
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder threadHolder = (ThreadHolder) ctx.getBean(THREAD_HOLDER, threads);
        ThreadManager threadManager = (ThreadManager) ctx.getBean(THREAD_MANAGER, threadHolder);
        
        IteratorDatabaseRecordReader iteratorReader = (IteratorDatabaseRecordReader) ctx.getBean(
                ITERATOR_DATABASE_RECORD_READER, serverDTO);
        IteratorDatabaseRecordWriter iteratorWriter = (IteratorDatabaseRecordWriter) ctx.getBean(
                ITERATOR_DATABASE_RECORD_WRITER, serverDTO);
        
        IteratorDatabaseRecord iterator = (IteratorDatabaseRecord) ctx.getBean(
                ITERATOR_DATABASE_RECORD, iteratorReader.read());
        
        LightSearchChecker checker = ctx.getBean(LIGHTSEARCH_CHECKER, LightSearchChecker.class);
        
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        
        LightSearchListenerDTO listenerDTO = (LightSearchListenerDTO) ctx.getBean(LIGHTSEARCH_LISTENER_DTO,
                checker, currentDateTime, threadManager, iterator, iteratorWriter, timerRebootId);

        EndStartupMessage endStartupMessage = ctx.getBean(END_STARTUP_MESSAGE, EndStartupMessage.class);

        System.out.println(endStartupMessage.message());
        Thread.sleep(100);
        
        ServerStateChanger stateChanger = (ServerStateChanger) ctx.getBean(SERVER_STATE_CHANGER, serverDTO,
                loggerServer, currentDateTime, threadManager);
        
        TimerRebootValueChecker timerChecker = ctx.getBean(TIMER_REBOOT_VALUE_CHECKER, TimerRebootValueChecker.class);
        if(timerChecker.check(serverDTO.settingsDAO().serverRebootValue()))
            stateChanger.executeRebootTimer(timerRebootId);
        
        TimersIDEnum timerIteratorId = TimersIDEnum.ITERATOR_WRITER_TIMER_ID;
        long minutesToWrite = 30;
        IteratorDatabaseRecordWriterTimerCreatorDTO itDbRecWriterTimerCrDTO = (IteratorDatabaseRecordWriterTimerCreatorDTO)
                ctx.getBean(ITERATOR_DATABASE_RECORD_WRITER_TIMER_CREATOR_DTO, iterator, iteratorWriter, minutesToWrite,
                        timerIteratorId);
        stateChanger.executeIteratorDatabaseRecordWriterTimer(itDbRecWriterTimerCrDTO);

        LightSearchServerListener serverListener = (LightSearchServerListener) ctx.getBean(LIGHTSEARCH_LISTENER, serverDTO,
                listenerDTO, loggerServer);
        serverListener.startServer();
    }
}