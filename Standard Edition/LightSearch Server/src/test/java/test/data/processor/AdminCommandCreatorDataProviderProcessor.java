package test.data.processor;

import lightsearch.server.cmd.admin.AdminCommandCreatorInit;
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.AdminDAOInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LoggerServer;
import test.data.DataProviderCreator;

public class AdminCommandCreatorDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];
        String logDirectory = (String) args[1];

        LoggerServer logger = DataProviderCreator.createDataProvider(LoggerServer.class, logDirectory);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        return AdminCommandCreatorInit.adminCommandCreator(serverDTO, listenerDTO, logger, adminDAO);
    }
}
