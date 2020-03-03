package test.data.processor;

import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
import lightsearch.admin.panel.data.MessageCommandDTO;
import test.data.DataProviderCreator;

public class MessageCommandCreatorDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        String ip = (String) args[0];
        int port  = (Integer) args[1];

        MessageCommandDTO messageCommandDTO = DataProviderCreator.createDataProvider(MessageCommandDTO.class, ip, port);
        return MessageCommandCreatorInit.messageCommandCreator(messageCommandDTO);
    }
}
