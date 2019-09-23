package test.data.processor;

import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.data.AdminDTO;

import java.util.HashMap;
import java.util.Map;

public interface DataProviderHolder {
    Map<Class, DataProviderProcessor> holder = new HashMap<Class, DataProviderProcessor>() {{
        put(AdminCommandCreator.class, new AdminCommandCreatorDataProviderProcessor());
        put(AdminDTO.class, new AdminDTODataProviderProcessor());
        put(MessageCommandCreator.class, new MessageCommandCreatorDataProviderProcessor());
    }};
}
