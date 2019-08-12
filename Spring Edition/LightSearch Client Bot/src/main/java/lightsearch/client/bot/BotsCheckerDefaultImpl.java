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
package lightsearch.client.bot;

import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.ConnectionDTO;
import lightsearch.client.bot.exception.SocketException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.producer.*;
import lightsearch.client.bot.socket.SocketCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Service("botsCheckerDefault")
@Scope("prototype")
public class BotsCheckerDefaultImpl implements BotsChecker {

    private final ConnectionDTO connDTO;

    private final String PROCESSOR_CONNECTION_SYSTEM = "lightsearch.client.bot.processor.ProcessorConnectionSystemDefaultImpl";
    private final String PROCESSOR_CLEAR_AVG_TIME    = "lightsearch.client.bot.processor.ProcessorClearAverageTimeDefaultImpl";

    @Autowired private SocketCreatorProducer socketCreatorProducer;
    @Autowired private MessageSenderProducer msgSenderProducer;
    @Autowired private MessageRecipientProducer msgRecipientProducer;
    @Autowired private BotDAOProducer botDAOProducer;
    @Autowired private ProcessorProducer processorProducer;

    @Autowired
    private ApplicationContext ctx;

    public BotsCheckerDefaultImpl(ConnectionDTO connDTO) {
        this.connDTO = connDTO;
    }
    
    @SuppressWarnings("SleepWhileInLoop")
    @Override
    public void start() {
        boolean isWorked = true;
        while(isWorked) {
            try { Thread.sleep(100); } catch(InterruptedException ignored) {}
            if(BotsDoneSwitcher.isDone()) {
                SocketCreator socketCreator = socketCreatorProducer.getSocketCreatorDefaultInstance(connDTO);
                try(Socket socket = socketCreator.createSocket()) {
                    System.out.println("System test.bot:");
                    BotDAO botDAO = botDAOProducer.getBotDAODefaultInstance();
                    botDAO.setBotName("System");
                    
                    MessageSender msgSender = msgSenderProducer.getMessageSenderDefaultInstance(new DataOutputStream(socket.getOutputStream()));
                    MessageRecipient msgRecipient = msgRecipientProducer.getMessageRecipientDebugInstance(new DataInputStream(socket.getInputStream()));
                    Processor procConn = processorProducer.getProcessorDefaultInstance(PROCESSOR_CONNECTION_SYSTEM);
                    Processor procClear = processorProducer.getProcessorDefaultInstance(PROCESSOR_CLEAR_AVG_TIME);
                    
                    procConn.apply(botDAO, msgSender, msgRecipient, 0);
                    procClear.apply(botDAO, msgSender, msgRecipient, 0);
                    
                    isWorked = false;
                }
                catch(IOException | SocketException ignored) { }
            }
        }
    }
    
}
