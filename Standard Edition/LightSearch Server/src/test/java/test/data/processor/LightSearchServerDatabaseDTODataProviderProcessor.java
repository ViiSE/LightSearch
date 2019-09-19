package test.data.processor;

import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.initialization.*;

public class LightSearchServerDatabaseDTODataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... ignore) {
        CurrentServerDirectory currentDirectory = CurrentServerDirectoryInit.currentDirectoryDebug(OsDetectorInit.osDetector());
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentDirectory);
        String dbName = dbSettings.name();
        String dbIP = dbSettings.ip();
        int dbPort = dbSettings.port();

        return LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                dbIP,
                dbName,
                dbPort);
    }
}
