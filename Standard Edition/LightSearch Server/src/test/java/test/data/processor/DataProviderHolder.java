package test.data.processor;

import lightsearch.server.cmd.admin.AdminCommandCreator;
import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.system.SystemCommandCreator;
import lightsearch.server.data.*;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;

import java.util.HashMap;
import java.util.Map;

public interface DataProviderHolder {
    Map<Class, DataProviderProcessor> holder = new HashMap<Class, DataProviderProcessor>() {{
        put(LightSearchServerDTO.class, new LightSearchServerDTODataProviderProcessor());
        put(LoggerServer.class, new LoggerServerDTODataProviderProcessor());
        put(ThreadManager.class, new ThreadManagerDataProviderProcessor());
        put(DatabaseRecordIdentifier.class, new DatabaseRecordIdentifierDataProviderProcessor());
        put(LightSearchListenerDTO.class, new LightSearchListenerDTODataProviderProcessor());
        put(AdminCommandCreator.class, new AdminCommandCreatorDataProviderProcessor());
        put(ClientCommandCreator.class, new ClientCommandCreatorDataProviderProcessor());
        put(SystemCommandCreator.class, new SystemCommandCreatorDataProviderProcessor());
        put(LightSearchServerDatabaseDTO.class, new LightSearchServerDatabaseDTODataProviderProcessor());
        put(AdminHandlerDTO.class, new AdminHandlerDTODataProviderProcessor());
        put(SystemHandlerDTO.class, new SystemHandlerDTODataProviderProcessor());
        put(ClientHandlerDTO.class, new ClientHandlerDTODataProviderProcessor());
        put(AdminParametersHolder.class, new AdminParametersHolderDataProviderProcessor());
        put(SystemParametersHolder.class, new SystemParametersHolderDataProviderProcessor());
        put(ClientParametersHolder.class, new ClientParametersHolderDataProviderProcessor());
        put(DataStreamCreator.class, new DataStreamCreatorDataProviderProcessor());
        put(DatabaseConnection.class, new DatabaseConnectionDataProviderProcessor());
    }};
}
