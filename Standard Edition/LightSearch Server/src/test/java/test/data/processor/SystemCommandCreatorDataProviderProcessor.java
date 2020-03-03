package test.data.processor;

import lightsearch.server.cmd.system.SystemCommandCreatorInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import test.data.DataProviderCreator;

public class SystemCommandCreatorDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];

        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        return SystemCommandCreatorInit.systemCommandCreator(serverDTO, listenerDTO);
    }
}
