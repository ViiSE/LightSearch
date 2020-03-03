package test.data.processor;

import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.catchMessage;

public class DatabaseConnectionDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        try {
            String username = (String) args[0];
            String password = (String) args[1];

            LightSearchServerDatabaseDTO databaseDTO =
                    DataProviderCreator.createDataProvider(LightSearchServerDatabaseDTO.class);

            DatabaseConnectionCreator connectionCreator =
                    DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO, username, password);
            assertNotNull(connectionCreator, "DatabaseConnectionCreator is null!");

            return connectionCreator.createFirebirdConnection();
        } catch (DatabaseConnectionCreatorException ex) {
            catchMessage(ex);
            return null;
        }
    }
}
