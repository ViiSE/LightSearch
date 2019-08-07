/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.admin.panel.cmd.admin;

import lightsearch.admin.panel.cmd.admin.processor.ExitAdminPanelProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lightsearch.admin.panel.cmd.admin.processor.AddBlacklistProcessor;
import lightsearch.admin.panel.cmd.admin.processor.AuthenticationProcessor;
import lightsearch.admin.panel.cmd.admin.processor.BlacklistRequestProcessor;
import lightsearch.admin.panel.cmd.admin.processor.ChangeDatabaseProcessor;
import lightsearch.admin.panel.cmd.admin.processor.ClientKickProcessor;
import lightsearch.admin.panel.cmd.admin.processor.ClientListRequestProcessor;
import lightsearch.admin.panel.cmd.admin.processor.CreateAdminProcessor;
import lightsearch.admin.panel.cmd.admin.processor.DelBlacklistProcessor;
import lightsearch.admin.panel.cmd.admin.processor.RestartProcessor;
import lightsearch.admin.panel.cmd.admin.processor.TimeoutClientProcessor;
import lightsearch.admin.panel.cmd.admin.processor.TimeoutServerProcessor;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.util.MapRemover;

/**
 *
 * @author ViiSE
 */
public class AdminCommandCreatorDefaultImpl implements AdminCommandCreator {

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
    
    public AdminCommandCreatorDefaultImpl(AdminDTO adminDTO, MapRemover mapRemover) {
        this.adminDTO = adminDTO;
        this.mapRemover = mapRemover;
    }
    
    @Override
    public Map<String, Function<AdminPanelDTO, String>> createCommandHolder() {
        
        ScannerCommandHolderCreator scCmdHolderCreator =
                ScannerCommandHolderCreatorInit.scannerCommandHolderCreator();
        ScannerCommandHolder scCmdHolder = scCmdHolderCreator.createScannerCommandHolder();
        
        Map<String, Function<AdminPanelDTO, String>> cmdHolder = new HashMap<>();
        cmdHolder.put(AUTHENTICATION, new AuthenticationProcessor(adminDTO, scCmdHolder.scannerRegistration()));
        cmdHolder.put(RESTART, new RestartProcessor(adminDTO, scCmdHolder.scannerRestart()));
        cmdHolder.put(TOUT_SERVER, new TimeoutServerProcessor(adminDTO, scCmdHolder.scannerTimeout()));
        cmdHolder.put(TOUT_CLIENT, new TimeoutClientProcessor(adminDTO, scCmdHolder.scannerTimeout()));
        cmdHolder.put(CL_LIST, new ClientListRequestProcessor(adminDTO));
        cmdHolder.put(KICK, new ClientKickProcessor(adminDTO, scCmdHolder.scannerClientValue(), mapRemover));
        cmdHolder.put(BL_LIST, new BlacklistRequestProcessor(adminDTO));
        cmdHolder.put(ADD_BLACKLIST, new AddBlacklistProcessor(adminDTO, scCmdHolder.scannerClientValue()));
        cmdHolder.put(DEL_BLACKLIST, new DelBlacklistProcessor(adminDTO, scCmdHolder.scannerClientValue(), mapRemover));
        cmdHolder.put(CREATE_ADMIN, new CreateAdminProcessor(adminDTO, scCmdHolder.scannerRegistration()));
        cmdHolder.put(CH_DB, new ChangeDatabaseProcessor(adminDTO, scCmdHolder.scannerDatabase()));
        cmdHolder.put(EXIT, new ExitAdminPanelProcessor(adminDTO, scCmdHolder.scannerRestart()));
        
        return cmdHolder;
    }
    
}
