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

public class RestartProcessor extends AbstractProcessorAdmin {

    private final String COMMAND = MessageCommandEnum.RESTART.stringValue();
    
    private final ScannerRestart scanner;

    public RestartProcessor(AdminDTO adminDTO, ScannerRestart scanner) {
        super(adminDTO);
        this.scanner = scanner;
    }

    @Override
    public String apply(AdminPanelDTO admPanelDTO) {
        while(true) {
            try {
                super.adminDTO().printer().print("Are you sure? [y|yes/n|no]: ");
                String answer = scanner.scanAnswer();
                if(answer.matches("^y$|^yes$")) {
                    AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
                    admCmdDAO.setName(super.adminDTO().adminDAO().name());

                    Function<AdminCommandDAO, CommandResult> processor =
                            super.adminDTO().messageCommandHolder().get(COMMAND);

                    if(processor != null) {
                        processor.apply(admCmdDAO);
                        super.adminDTO().printer().println("Server Restarted");
                        super.adminDTO().printer().println("Goodbye, " + super.adminDTO().adminDAO().name() + "!");
                        try {
                            super.adminDTO().adminSocket().close();
                        } catch(IOException ignore) {}
                        finally {
                            System.exit(0);
                        }
                    }
                } else
                    return answer;
            } catch(ScannerException ex) {
                super.adminDTO().printer().println(ex.getMessage());
            }
        }
    }
}
