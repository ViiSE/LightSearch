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
package lightsearch.server.cmd.admin;

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.admin.ProcessorAdminProducer;
import lightsearch.server.producer.changer.ServerStateChangerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Service("adminCommandCreatorDefault")
@Scope("prototype")
public class AdminCommandCreatorDefaultImpl implements AdminCommandCreator {

    private final String CONNECT       = AdminCommandEnum.CONNECT.stringValue();
    private final String RESTART       = AdminCommandEnum.RESTART.stringValue();
    private final String TOUT_SERVER   = AdminCommandEnum.TIMEOUT_SERVER.stringValue();
    private final String TOUT_CLIENT   = AdminCommandEnum.TIMEOUT_CLIENT.stringValue();
    private final String CL_LIST       = AdminCommandEnum.CLIENT_LIST.stringValue();
    private final String KICK          = AdminCommandEnum.KICK.stringValue();
    private final String BL_LIST       = AdminCommandEnum.BLACKLIST.stringValue();
    private final String ADD_BLACKLIST = AdminCommandEnum.ADD_BLACKLIST.stringValue();
    private final String DEL_BLACKLIST = AdminCommandEnum.DEL_BLACKLIST.stringValue();
    private final String CREATE_ADMIN  = AdminCommandEnum.CREATE_ADMIN.stringValue();
    private final String CH_DB         = AdminCommandEnum.CHANGE_DATABASE.stringValue();
    
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer logger;
    private final AdminDAO adminDAO;

    @Autowired private ProcessorAdminProducer procAdmProducer;
    @Autowired private ServerStateChangerProducer servStateChProducer;

    @Autowired
    public AdminCommandCreatorDefaultImpl(LightSearchServerDTO serverDTO, 
            LightSearchListenerDTO listenerDTO, LoggerServer logger, AdminDAO adminDAO) {
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.logger = logger;
        this.adminDAO = adminDAO;
    }

    @Override
    public Map<String, Function<AdminCommand, CommandResult>> createCommandHolder() {
        Map<String, Function<AdminCommand, CommandResult>> result = new HashMap<>();

        result.put(CONNECT, procAdmProducer.getAuthenticationProcessorInstance(serverDTO, listenerDTO.checker(), adminDAO));
        result.put(RESTART, procAdmProducer.getRestartProcessorInstance(serverDTO, listenerDTO.checker(), logger,
                listenerDTO.currentDateTime(), listenerDTO.threadManager(), listenerDTO.timerRebootId()));
        result.put(TOUT_SERVER, procAdmProducer.getTimeoutServerProcessor(serverDTO, listenerDTO.checker(),
                servStateChProducer.getServerStateChangerDefaultInstance(serverDTO, logger, listenerDTO.currentDateTime(),
                        listenerDTO.threadManager()), listenerDTO.timerRebootId()));
        result.put(TOUT_CLIENT, procAdmProducer.getTimeoutClientProcessorInstance(serverDTO, listenerDTO.checker()));
        result.put(CL_LIST, procAdmProducer.getClientListRequestProcessorInstance(serverDTO, listenerDTO.checker()));
        result.put(KICK, procAdmProducer.getClientKickProcessorInstance(serverDTO, listenerDTO.checker()));
        result.put(BL_LIST, procAdmProducer.getBlacklistRequestProcessorInstance(serverDTO, listenerDTO.checker()));
        result.put(ADD_BLACKLIST, procAdmProducer.getAddBlacklistProcessorInstance(serverDTO, listenerDTO.checker()));
        result.put(DEL_BLACKLIST, procAdmProducer.getDelBlacklistProcessorInstance(serverDTO, listenerDTO.checker()));
        result.put(CREATE_ADMIN, procAdmProducer.getCreateAdminProcessorInstance(serverDTO, listenerDTO.checker()));
        result.put(CH_DB, procAdmProducer.getChangeDatabaseProcessorInstance(serverDTO, listenerDTO.checker()));
        
        return result;
    }
    
}
