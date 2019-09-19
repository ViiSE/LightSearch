package test.data.processor;

import lightsearch.server.cmd.admin.AdminCommandCreator;
import lightsearch.server.data.AdminParametersHolderInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import test.data.DataProviderCreator;

import java.net.Socket;

import static test.message.TestMessage.catchMessage;

public class AdminParametersHolderDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];
        String logDirectory = (String) args[1];

        AdminCommandCreator adminCommandCreator =
                DataProviderCreator.createDataProvider(AdminCommandCreator.class, serverDTO, logDirectory);

        Socket adminSocket = new Socket();
        DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
        try {
            dataStreamCreator.createDataStream();
        } catch(DataStreamCreatorException ex) {
            catchMessage(ex);
        }

        DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);

        return AdminParametersHolderInit.adminParametersHolder(
                adminSocket, dataStream, adminCommandCreator.createCommandHolder());
    }
}
