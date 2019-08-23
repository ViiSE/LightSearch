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

import java.util.function.Function;
import lightsearch.admin.panel.cmd.message.MessageCommandEnum;
import lightsearch.admin.panel.cmd.message.MessageCommandResultEnum;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminCommandDAOInit;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerDatabase;

/**
 *
 * @author ViiSE
 */
public class ChangeDatabaseProcessor extends AbstractProcessorAdmin {

    private final String COMMAND = MessageCommandEnum.CHANGE_DATABASE.stringValue();
    private final String TRUE    = MessageCommandResultEnum.TRUE.stringValue();
    
    private final ScannerDatabase scanner;
    
    public ChangeDatabaseProcessor(AdminDTO adminDTO, ScannerDatabase scanner) {
        super(adminDTO);
        this.scanner = scanner;
    }

    @Override
    public String apply(AdminPanelDTO admPanelDTO) {
        while(true) {
            try {
                super.adminDTO().printer().print("Input database name(full path): ");
                String dbName = scanner.scanDBName();

                super.adminDTO().printer().print("Input database ip: ");
                String dbIp = scanner.scanIP();

                super.adminDTO().printer().print("Input database port: ");
                String dbPort = scanner.scanPort();
                
                AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
                admCmdDAO.setName(super.adminDTO().adminDAO().name());
                admCmdDAO.setDbName(dbName);
                admCmdDAO.setIp(dbIp);
                admCmdDAO.setPort(dbPort);
                
                Function<AdminCommandDAO, CommandResult> processor = 
                        super.adminDTO().messageCommandHolder().get(COMMAND);
                if(processor != null){
                    CommandResult cmdRes = processor.apply(admCmdDAO);
                    if(cmdRes.name().equals(super.adminDTO().adminDAO().name())) {
                        if(cmdRes.isDone().equals(TRUE)) {
                            String resMsg = cmdRes.message();
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
            }
            catch (ScannerException ex) {
                super.adminDTO().printer().println(ex.getMessage());
            }
        }
    }
}