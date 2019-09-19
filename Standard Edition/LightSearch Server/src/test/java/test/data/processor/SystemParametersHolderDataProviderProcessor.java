package test.data.processor;

import lightsearch.server.cmd.system.SystemCommandCreator;
import lightsearch.server.cmd.system.SystemCommandCreatorInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.SystemParametersHolderInit;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import test.data.DataProviderCreator;

import java.net.Socket;

import static test.message.TestMessage.catchMessage;

public class SystemParametersHolderDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];

        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);

        SystemCommandCreator systemCommandCreator = SystemCommandCreatorInit.systemCommandCreator(serverDTO, listenerDTO);

        Socket adminSocket = new Socket();
        DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
        try {
            dataStreamCreator.createDataStream();
        } catch(DataStreamCreatorException ex) {
            catchMessage(ex);
        }

        DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
        return SystemParametersHolderInit.systemParametersHolder(
                adminSocket, dataStream, systemCommandCreator.createCommandHolder());
    }
}
