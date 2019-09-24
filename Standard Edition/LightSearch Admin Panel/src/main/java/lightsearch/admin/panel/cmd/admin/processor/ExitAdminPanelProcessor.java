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

import java.io.IOException;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerRestart;

/**
 *
 * @author ViiSE
 */
public class ExitAdminPanelProcessor extends AbstractProcessorAdmin {

    private final ScannerRestart scanner;
    
    public ExitAdminPanelProcessor(AdminDTO adminDTO, ScannerRestart scanner) {
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
                    super.adminDTO().printer().println("Goodbye!");
                    super.adminDTO().adminSocket().close();
                    System.exit(0);
                } else {
                    return answer;
                }
            } catch(ScannerException ex) {
                super.adminDTO().printer().println(ex.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
