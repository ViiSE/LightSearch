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

package ru.viise.lightsearch.cmd.manager;

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.data.ClientHandlerCreatorDTO;
import ru.viise.lightsearch.data.ClientHandlerDTO;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.handler.ClientHandler;
import ru.viise.lightsearch.handler.ClientHandlerCreator;
import ru.viise.lightsearch.handler.ClientHandlerCreatorInit;

public class CommandManagerDefaultImpl implements CommandManager {

    private final ClientHandlerCreatorDTO creatorDTO;

    private ClientHandlerRunner clientHandlerRunner;
    private String clientHandlerRunnerMessage;
    private CommandResult commandResult;

    public CommandManagerDefaultImpl(ClientHandlerCreatorDTO creatorDTO) {
        this.creatorDTO = creatorDTO;
    }

    @Override
    public CommandResult doCommand(CommandTypeEnum type, CommandDTO commandDTO) {
        clientHandlerRunner.commandDTO = commandDTO;
        clientHandlerRunner.type = type;
        try { Thread.sleep(500); } catch(InterruptedException ignore) {}
        while(true) {
            if(clientHandlerRunner.done)
                break;
        }
        clientHandlerRunner.done = false;
        return commandResult;
    }

    @Override
    public void closeConnection() {
        clientHandlerRunner.exit = true;
        try { Thread.sleep(500); } catch(InterruptedException ignore) {}
        clientHandlerRunner = null;
    }

    @Override
    public String createClientHandler() {
        clientHandlerRunner = new ClientHandlerRunner();
        Thread thread = new Thread(clientHandlerRunner);
        thread.start();
        while(true) {
            if(clientHandlerRunner.done)
                break;
        }
        return clientHandlerRunnerMessage;
    }

    private class ClientHandlerRunner implements Runnable {

        private CommandTypeEnum type = null;
        private CommandDTO commandDTO;
        private ClientHandler clientHandler;

        private boolean done = false;
        private boolean exit = false;

        @Override
        public void run() {
            if(initClientHandler()) {
                done = false;
                while(!exit) {
                    try { Thread.sleep(500); } catch (InterruptedException ignore) {}
                    if(type != null) {
                        commandResult = clientHandler.doCommand(type, commandDTO);
                        done = true;
                        try { Thread.sleep(100);} catch(InterruptedException ignore) {}
                        type = null;
                        commandDTO = null;
                    }
                }
                clientHandler.closeConnection();
            }
        }

        private boolean initClientHandler() {
            ClientHandlerCreator clHandlerCr = ClientHandlerCreatorInit.clientHandlerCreator(creatorDTO);
            ClientHandlerDTO clHandlerDTO = clHandlerCr.createClientHandler();
            if(clHandlerDTO.clientHandler() != null) {
                clientHandler = clHandlerDTO.clientHandler();
                done = true;
                try { Thread.sleep(100); } catch(InterruptedException ignore) {}
                return true;
            }
            else {
                clientHandlerRunnerMessage = clHandlerDTO.message();
                done = true;
                try { Thread.sleep(100); } catch(InterruptedException ignore) {}
                return false;
            }
        }
    }
}
