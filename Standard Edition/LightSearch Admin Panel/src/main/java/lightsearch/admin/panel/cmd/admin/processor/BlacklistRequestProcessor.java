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
package lightsearch.admin.panel.cmd.admin.processor;

import lightsearch.admin.panel.cmd.admin.result.BlacklistResult;
import lightsearch.admin.panel.cmd.admin.result.BlacklistResultInit;
import lightsearch.admin.panel.cmd.message.MessageCommandEnum;
import lightsearch.admin.panel.cmd.message.MessageCommandResultEnum;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminCommandDAOInit;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminPanelDTO;

import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
public class BlacklistRequestProcessor extends AbstractProcessorAdmin {
    
    private final String COMMAND = MessageCommandEnum.BLACKLIST.stringValue();
    private final String TRUE    = MessageCommandResultEnum.TRUE.stringValue();
    
    public BlacklistRequestProcessor(AdminDTO adminDTO) {
        super(adminDTO);
    }

    @Override
    public String apply(AdminPanelDTO admPanelDTO) {
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        admCmdDAO.setName(super.adminDTO().adminDAO().name());
        
        Function<AdminCommandDAO, CommandResult> processor = 
                super.adminDTO().messageCommandHolder().get(COMMAND);
        
        if(processor != null){
            CommandResult cmdRes = processor.apply(admCmdDAO);
            if(cmdRes.name().equals(super.adminDTO().adminDAO().name())) {
                if(cmdRes.isDone().equals(TRUE)) {
                    Object data = cmdRes.data();
                    BlacklistResult blRes =
                            BlacklistResultInit.blacklistResult(data, admPanelDTO.blacklist());
                    String resMsg = blRes.result();
                    return resMsg;
                }
                else {
                    String errMsg = cmdRes.message();
                    return errMsg;
                }
            }
            else
                throw new RuntimeException(cmdRes.message());
        }
        
        throw new RuntimeException("BlacklistRequestProcessor. Unknown");
    }
}
