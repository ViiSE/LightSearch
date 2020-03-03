package lightsearch.admin.panel.cmd.admin;

import lightsearch.admin.panel.cmd.admin.processor.test.*;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.util.MapRemover;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AdminCommandCreatorTestImpl implements AdminCommandCreator {

    private final String AUTHENTICATION = AdminCommandEnum.AUTHENTICATION.stringValue();
    private final String RESTART        = AdminCommandEnum.RESTART.stringValue();
    private final String TOUT_SERVER    = AdminCommandEnum.TIMEOUT_SERVER.stringValue();
    private final String TOUT_CLIENT    = AdminCommandEnum.TIMEOUT_CLIENT.stringValue();
    private final String CL_LIST        = AdminCommandEnum.CLIENT_LIST.stringValue();
    private final String KICK           = AdminCommandEnum.KICK.stringValue();
    private final String BL_LIST        = AdminCommandEnum.BLACKLIST.stringValue();
    private final String ADD_BLACKLIST  = AdminCommandEnum.ADD_BLACKLIST.stringValue();
    private final String DEL_BLACKLIST  = AdminCommandEnum.DEL_BLACKLIST.stringValue();
    private final String CREATE_ADMIN   = AdminCommandEnum.CREATE_ADMIN.stringValue();
    private final String CH_DB          = AdminCommandEnum.CHANGE_DATABASE.stringValue();
    private final String EXIT           = AdminCommandEnum.EXIT.stringValue();

    private final AdminDTO adminDTO;
    private final MapRemover mapRemover;

    public AdminCommandCreatorTestImpl(AdminDTO adminDTO, MapRemover mapRemover) {
        this.adminDTO = adminDTO;
        this.mapRemover = mapRemover;
    }

    @Override
    public Map<String, Function<AdminPanelDTO, String>> createCommandHolder() {
        Map<String, Function<AdminPanelDTO, String>> cmdHolder = new HashMap<>();
        cmdHolder.put(AUTHENTICATION, new AuthenticationProcessorTest(adminDTO));
        cmdHolder.put(RESTART, new RestartProcessorTest(adminDTO));
        cmdHolder.put(TOUT_SERVER, new TimeoutServerProcessortTest(adminDTO));
        cmdHolder.put(TOUT_CLIENT, new TimeoutClientProcessorTest(adminDTO));
        cmdHolder.put(CL_LIST, new ClientListRequestProcessorTest(adminDTO));
        cmdHolder.put(KICK, new ClientKickProcessorTest(adminDTO, mapRemover));
        cmdHolder.put(BL_LIST, new BlacklistRequestProcessorTest(adminDTO));
        cmdHolder.put(ADD_BLACKLIST, new AddBlacklistProcessorTest(adminDTO));
        cmdHolder.put(DEL_BLACKLIST, new DelBlacklistProcessorTest(adminDTO, mapRemover));
        cmdHolder.put(CREATE_ADMIN, new CreateAdminProcessorTest(adminDTO));
        cmdHolder.put(CH_DB, new ChangeDatabaseProcessorTest(adminDTO));
        cmdHolder.put(EXIT, new ExitAdminPanelProcessorTest(adminDTO));

        return cmdHolder;
    }
}
