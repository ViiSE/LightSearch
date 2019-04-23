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
package lightsearch.admin.panel.session;

import java.util.function.Function;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.data.AdminPanelSessionDTO;
import lightsearch.admin.panel.exception.ScannerException;

/**
 *
 * @author ViiSE
 */
public class AdminPanelSessionDefaultImpl implements AdminPanelSession {
    
    private final AdminPanelSessionDTO sessionDTO;

    public AdminPanelSessionDefaultImpl(AdminPanelSessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
    }
    
    @Override
    public void startSession() {
        while(true) {
            try {
                sessionDTO.adminMenu().menu();
                String command = sessionDTO.scannerCommand().scanCommand();
                Function<AdminPanelDTO, String> processor = 
                        sessionDTO.commandHolder().get(command);
                if(processor != null) {
                    String result = processor.apply(sessionDTO.adminPanelDTO());
                    sessionDTO.printer().println(result);
                }
            }
            catch(ScannerException ex) {
                sessionDTO.printer().println(ex.getMessage());
            }
        }
    }
}
