package test.data.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriterInit;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.timer.TimersIDEnum;
import test.data.DataProviderCreator;

public class LightSearchListenerDTODataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];

        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        DatabaseRecordIdentifier databaseRecordIdentifier =
                DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
        DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter =
                DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);

        return LightSearchListenerDTOInit.lightSearchListenerDTO(
                checker, currentDateTime, threadManager, databaseRecordIdentifier,
                databaseRecordIdentifierWriter, timerRebootId);
    }
}
