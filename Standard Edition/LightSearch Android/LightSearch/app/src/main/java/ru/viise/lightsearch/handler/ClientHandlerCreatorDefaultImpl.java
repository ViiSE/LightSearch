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

import java.net.Socket;

import ru.viise.lightsearch.check.ConnectionProcessorResultChecker;
import ru.viise.lightsearch.check.ConnectionProcessorResultCheckerInit;
import ru.viise.lightsearch.cmd.holder.ClientCommandCreator;
import ru.viise.lightsearch.cmd.holder.ClientCommandCreatorInit;
import ru.viise.lightsearch.cmd.holder.ClientCommandHolder;
import ru.viise.lightsearch.connect.processor.ConnectionProcessor;
import ru.viise.lightsearch.connect.processor.ConnectionProcessorInit;
import ru.viise.lightsearch.connect.processor.result.ConnectionProcessorResult;
import ru.viise.lightsearch.data.ClientHandlerCreatorDTO;
import ru.viise.lightsearch.data.ClientHandlerDTO;
import ru.viise.lightsearch.data.ClientHandlerDTOInit;
import ru.viise.lightsearch.data.ConnectionDTO;
import ru.viise.lightsearch.data.stream.DataStreamCreator;
import ru.viise.lightsearch.data.stream.DataStreamCreatorInit;
import ru.viise.lightsearch.exception.DataStreamCreatorException;
import ru.viise.lightsearch.exception.SocketException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageRecipientInit;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.MessageSenderInit;
import ru.viise.lightsearch.socket.SocketCreator;
import ru.viise.lightsearch.socket.SocketCreatorInit;

public class ClientHandlerCreatorDefaultImpl implements ClientHandlerCreator {

    private final String IMEI;
    private final ConnectionDTO connectionDTO;

    public ClientHandlerCreatorDefaultImpl(ClientHandlerCreatorDTO creatorDTO) {
        IMEI = creatorDTO.IMEI();
        connectionDTO = creatorDTO.connectionDTO();
    }

    @Override
    public ClientHandlerDTO createClientHandler() {
        try {
            SocketCreator socketCreator = SocketCreatorInit.socketCreator(connectionDTO);
            Socket clientSocket = socketCreator.createSocket();

            DataStreamCreator dsCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
            dsCreator.createDataStream();

            MessageSender msgSender = MessageSenderInit.messageSender(dsCreator.dataOutputStream());
            MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(dsCreator.dataInputStream());

            ConnectionProcessor connPrc = ConnectionProcessorInit.connectionProcessor(msgSender, msgRecipient);
            ConnectionProcessorResult connPrcRes = connPrc.apply();

            ConnectionProcessorResultChecker connPrcResChecker =
                    ConnectionProcessorResultCheckerInit.connectionProcessorResultChecker();
            if(connPrcResChecker.check(connPrcRes)) {
                ClientCommandCreator cmdClCr = ClientCommandCreatorInit.clientCommandCreator(
                        IMEI, msgSender, msgRecipient);
                ClientCommandHolder cmdClHolder = cmdClCr.createClientCommandHolder();
                ClientHandler clientHandler = ClientHandlerInit.clientHandler(clientSocket, cmdClHolder);

                return ClientHandlerDTOInit.clientHandlerDTO(clientHandler, null);

            } else {
                String message = "Сервер не дал разрешение на подключение. " +
                        "Обратитесь к администратору для устранения проблемы.";

                return ClientHandlerDTOInit.clientHandlerDTO(null, message);
            }
        } catch(SocketException ex) {
            String message = "Не удалось подключиться к серверу: превышен лимит ожидания. " +
                    "Проверьте подключение к сети и повторите попытку.";
            return ClientHandlerDTOInit.clientHandlerDTO(null, message);
        } catch(DataStreamCreatorException ex) {
            String message = "Возникла ошибка, сообщение: " + ex.getMessage();
            return ClientHandlerDTOInit.clientHandlerDTO(null, message);
        }
    }
}
