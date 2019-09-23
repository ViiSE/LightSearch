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
package lightsearch.admin.panel.cmd.admin.processor.test;

import lightsearch.admin.panel.cmd.admin.processor.AbstractProcessorAdmin;
import lightsearch.admin.panel.cmd.message.MessageCommandEnum;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminCommandDAOInit;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerRestart;

import java.io.IOException;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */

public class RestartProcessorTest extends AbstractProcessorAdmin {

    private final String COMMAND = MessageCommandEnum.RESTART.stringValue();
    
    public RestartProcessorTest(AdminDTO adminDTO) {
        super(adminDTO);
    }

    @Override
    public String apply(AdminPanelDTO admPanelDTO) {
        while(true) {
            super.adminDTO().printer().print("Are you sure? [y|yes/n|no]: ");
            String answer = "n";
            if(answer.matches("^y$|^yes$")) {
                AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
                admCmdDAO.setName(super.adminDTO().adminDAO().name());

                Function<AdminCommandDAO, CommandResult> processor = super.adminDTO().messageCommandHolder().get(COMMAND);

                if(processor != null){
                    CommandResult cmdRes = processor.apply(admCmdDAO);
                    super.adminDTO().printer().println("Server Restarted");
                    super.adminDTO().printer().println(
                            "Goodbye, " + super.adminDTO().adminDAO().name() + "!");
                    try {
                        super.adminDTO().adminSocket().close();
                    } catch(IOException ignore) { }
                    finally {
                        System.exit(0);
                    }
                }
            }
            else return answer;
        }
    }
}
