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
package lightsearch.admin.panel.data;

import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.print.AdminPanelPrinter;

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
public class AdminDTOInit {
    
    public static AdminDTO adminDTO(Socket adminSocket, AdminDAO adminDAO, AdminPanelPrinter printer, 
            Map<String, Function<AdminCommandDAO, CommandResult>> commandMessageHolder) {
        return new AdminDTODefaultImpl(adminSocket, adminDAO, printer, commandMessageHolder);
    } 
}
