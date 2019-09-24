package test.data.processor;

import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.connect.processor.ConnectionProcessor;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.MessageCommandDTO;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.scanner.ScannerChooserCommand;

import java.util.HashMap;
import java.util.Map;

public interface DataProviderHolder {
    Map<Class, DataProviderProcessor> holder = new HashMap<Class, DataProviderProcessor>() {{
        put(AdminCommandCreator.class, new AdminCommandCreatorDataProviderProcessor());
        put(AdminDTO.class, new AdminDTODataProviderProcessor());
        put(MessageCommandCreator.class, new MessageCommandCreatorDataProviderProcessor());
        put(ConnectionProcessor.class, new ConnectionProcessorDataProviderProcessor());
        put(DataStream.class, new DataStreamDataProviderProcessor());
        put(MessageCommandDTO.class, new MessageCommandDTODataProviderProcessor());
        put(ScannerChooserCommand.class, new ScannerChooserCommandDataProviderProcessor());
    }};
}
