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

package ru.viise.lightsearch.handler;

import java.io.IOException;
import java.net.Socket;

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.holder.ClientCommandHolder;
import ru.viise.lightsearch.cmd.processor.ClientProcessor;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.data.CommandDTO;

public class ClientHandlerDefaultImpl implements ClientHandler {

    private final Socket clientSocket;
    private final ClientCommandHolder cmdClHolder;

    public ClientHandlerDefaultImpl(Socket clientSocket, ClientCommandHolder cmdClHolder) {
        this.clientSocket = clientSocket;
        this.cmdClHolder = cmdClHolder;
    }

    @Override
    public CommandResult doCommand(CommandTypeEnum type, CommandDTO commandDTO) {
        ClientProcessor processor = cmdClHolder.get(type);
        if(processor != null) {
            return processor.apply(commandDTO);
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
