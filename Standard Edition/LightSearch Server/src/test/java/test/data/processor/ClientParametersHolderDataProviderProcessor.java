package test.data.processor;

import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.client.ClientCommandCreatorInit;
import lightsearch.server.data.*;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import test.data.DataProviderCreator;

import java.net.Socket;

import static test.message.TestMessage.catchMessage;

public class ClientParametersHolderDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];

        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        ClientCommandCreator clientCommandCreator =
                ClientCommandCreatorInit.clientCommandCreator(serverDTO, listenerDTO, clientDAO);

        Socket adminSocket = new Socket();
        DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
        try {
            dataStreamCreator.createDataStream();
        } catch(DataStreamCreatorException ex) {
            catchMessage(ex);
        }

        DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);

        return ClientParametersHolderInit.clientParametersHolder(
                adminSocket, dataStream, clientCommandCreator.createCommandHolder());
    }
}
