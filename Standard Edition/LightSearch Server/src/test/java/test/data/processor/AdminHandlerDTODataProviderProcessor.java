package test.data.processor;

import lightsearch.server.data.*;
import lightsearch.server.identifier.*;
import lightsearch.server.thread.LightSearchThreadID;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import test.data.DataProviderCreator;

public class AdminHandlerDTODataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        String logDirectory = (String) args[0];

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        AdminParametersHolder adminParamHolder =
                DataProviderCreator.createDataProvider(AdminParametersHolder.class, serverDTO, logDirectory);

        DatabaseRecordIdentifier identifier = DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
        String id = LightSearchThreadID.createID(IdentifierEnum.ADMIN.stringValue(), identifier.next());

        ThreadParametersHolder threadParametersHolder = ThreadParametersHolderInit.threadParametersHolder(id);

        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);

        return AdminHandlerDTOInit.adminHandlerDTO(
                adminParamHolder, threadParametersHolder, currentDateTime, threadManager, adminDAO);
    }
}
