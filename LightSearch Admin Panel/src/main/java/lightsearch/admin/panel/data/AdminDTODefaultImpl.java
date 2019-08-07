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

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.print.AdminPanelPrinter;

/**
 *
 * @author ViiSE
 */
public class AdminDTODefaultImpl implements AdminDTO {

    private final Socket socket;
    private final AdminDAO adminDAO;
    private final AdminPanelPrinter printer;
    private final Map<String, Function<AdminCommandDAO, CommandResult>> commandMessageHolder;

    public AdminDTODefaultImpl(Socket socket, AdminDAO adminDAO, AdminPanelPrinter printer,
            Map<String, Function<AdminCommandDAO, CommandResult>> commandMessageHolder) {
        this.socket = socket;
        this.adminDAO = adminDAO;
        this.printer = printer;
        this.commandMessageHolder = commandMessageHolder;
    }

    @Override
    public Socket adminSocket() {
        return socket;
    }
    
    @Override
    public AdminDAO adminDAO() {
        return adminDAO;
    }

    @Override
    public AdminPanelPrinter printer() {
        return printer;
    }

    @Override
    public Map<String, Function<AdminCommandDAO, CommandResult>> messageCommandHolder() {
        return commandMessageHolder;
    }
}
