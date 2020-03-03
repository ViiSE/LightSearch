package test.data.processor;

import lightsearch.server.data.*;
import lightsearch.server.initialization.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightSearchServerDTODataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        CurrentServerDirectory currentDirectory = CurrentServerDirectoryInit.currentDirectoryDebug(OsDetectorInit.osDetector());
        Map<String,String> admins = AdministratorsMapInit.administratorsMap(currentDirectory).administratorsMap();
        Map<String,String> clients = new HashMap<>();
        List<String> blacklist = ClientBlacklistInit.clientBlacklist(currentDirectory).blacklist();

        LightSearchServerDatabaseDTO databaseDTO;
        if(args.length == 0) {
            DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentDirectory);
            String dbName = dbSettings.name();
            String dbIP = dbSettings.ip();
            int dbPort = dbSettings.port();

            databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                    dbIP,
                    dbName,
                    dbPort);
        } else
            databaseDTO = (LightSearchServerDatabaseDTO) args[0];

        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentDirectory);
        int rebootServer = serverSettings.rebootServerValue();
        int timeoutClient = serverSettings.timeoutClientValue();

        ServerPort serverPort = ServerPortInit.serverPort(currentDirectory);
        int sPort = serverPort.port();

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
}
