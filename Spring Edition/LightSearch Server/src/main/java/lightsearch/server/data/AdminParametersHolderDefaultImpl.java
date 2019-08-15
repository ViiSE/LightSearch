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
package lightsearch.server.data;

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.stream.DataStream;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Component("adminParametersHolderDefault")
@Scope("prototype")
public class AdminParametersHolderDefaultImpl implements AdminParametersHolder {

    private final Socket adminSocket;
    private final DataStream dataStream;
    private final Map<String, Function<AdminCommand, CommandResult>> commandHolder;
    
    public AdminParametersHolderDefaultImpl(Socket adminSocket, DataStream dataStream, 
            Map<String, Function<AdminCommand, CommandResult>> commandHolder) {
        this.adminSocket = adminSocket;
        this.dataStream = dataStream;
        this.commandHolder = commandHolder;
    }

    @Override
    public Socket adminSocket() {
        return adminSocket;
    }

    @Override
    public DataStream dataStream() {
        return dataStream;
    }

    @Override
    public Map<String, Function<AdminCommand, CommandResult>> commandHolder() {
        return commandHolder;
    }
    
}
