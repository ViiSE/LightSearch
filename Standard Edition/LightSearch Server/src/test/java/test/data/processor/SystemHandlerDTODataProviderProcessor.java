package test.data.processor;

import lightsearch.server.data.*;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.IdentifierEnum;
import lightsearch.server.thread.LightSearchThreadID;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import test.data.DataProviderCreator;

public class SystemHandlerDTODataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... ignore) {
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);

        SystemParametersHolder systemParamHolder = DataProviderCreator.createDataProvider(SystemParametersHolder.class,
                serverDTO);

        DatabaseRecordIdentifier identifier =
                DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
        String id = LightSearchThreadID.createID(IdentifierEnum.CLIENT.stringValue(), identifier.next());

        ThreadParametersHolder threadParametersHolder = ThreadParametersHolderInit.threadParametersHolder(id);
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);

        return SystemHandlerDTOInit.systemHandlerDTO(
                systemParamHolder, threadParametersHolder, threadManager, currentDateTime);
    }
}
