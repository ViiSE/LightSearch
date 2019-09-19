package test.data.processor;

import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.exception.DataStreamCreatorException;

import java.net.Socket;

public class DataStreamCreatorDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... ignore) {
        Socket clientSocket = new Socket();
        DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
        try {
            dataStreamCreator.createDataStream();
            return dataStreamCreator;
        } catch (DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
            return null;
        }
    }
}
