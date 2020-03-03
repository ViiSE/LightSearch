package test.data.processor;

import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.identifier.DatabaseRecordIdentifierInit;
import lightsearch.server.identifier.DatabaseRecordIdentifierReader;
import lightsearch.server.identifier.DatabaseRecordIdentifierReaderInit;

public class DatabaseRecordIdentifierDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        LightSearchServerDTO serverDTO = (LightSearchServerDTO) args[0];
        DatabaseRecordIdentifierReader identifierReader =
                DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        return DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
    }
}
