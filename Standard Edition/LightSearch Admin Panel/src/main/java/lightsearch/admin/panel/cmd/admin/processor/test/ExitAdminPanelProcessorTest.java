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
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminPanelDTO;

import java.io.IOException;

/**
 *
 * @author ViiSE
 */
public class ExitAdminPanelProcessorTest extends AbstractProcessorAdmin {
    
    public ExitAdminPanelProcessorTest(AdminDTO adminDTO) {
        super(adminDTO);
    }

    @Override
    public String apply(AdminPanelDTO admPanelDTO) {
        while(true) {
            try {
                super.adminDTO().printer().print("Are you sure? [y|yes/n|no]: ");
                String answer = "n";
                if(answer.matches("^y$|^yes$")) {
                    super.adminDTO().printer().println("Goodbye!");
                    super.adminDTO().adminSocket().close();
                    System.exit(0);
                } else
                    return answer;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
