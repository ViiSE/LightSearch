package lightsearch.admin.panel.data.creator;

import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.ConnectionDTOInit;

public class ConnectionDTOCreatorTestImpl implements ConnectionDTOCreator {

    private final String ip;
    private final int port;

    public ConnectionDTOCreatorTestImpl(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public ConnectionDTO createConnectionDTO() {
        return ConnectionDTOInit.connectionDTO(ip, port);
    }
}
