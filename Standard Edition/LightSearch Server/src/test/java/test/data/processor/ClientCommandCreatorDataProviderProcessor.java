package test.data.processor;

import lightsearch.server.cmd.client.ClientCommandCreatorInit;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import test.data.DataProviderCreator;

public class ClientCommandCreatorDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];
        ClientDAO clientDAO = (ClientDAO) args[1];

        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);

        return ClientCommandCreatorInit.clientCommandCreator(serverDTO, listenerDTO, clientDAO);
    }
}
